package com.hus.biz.elasticsearch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.*;

/**
 * elasticsearch工具类
 */
public abstract class SearchHelper<DataObject> {

    private static final Logger log = LoggerFactory.getLogger(SearchHelper.class);
    /**
     * 默认elasticsearch-cluster-centos
     */
    protected String clusterName;
    /**
     * 必填,数据库名
     */
    protected String dataBase;//数据库名
    /**
     * 必填,表名
     */
    protected String table;//表名
    /**
     * 非必填,ip1:port1;ip2:port2
     */
    protected String hosts;

    //    private AtomicBoolean initedNode = new AtomicBoolean(false);
    /**
     * 必填  持久化数据的 类名
     */
    protected Class<DataObject> dataObject;

    protected Client client;

    public boolean insertOrUpdate(DataObject idAndColumn, boolean nullSet) {
        initIndexAndType();
        checkClassType(idAndColumn);
        List<DataObject> idAndColumnValueList = new ArrayList<DataObject>();
        idAndColumnValueList.add(idAndColumn);
        return insertOrUpdate(idAndColumnValueList, nullSet);
    }

    public boolean insertOrUpdate(List<DataObject> idAndColumnValueList, boolean nullSet) {
        initIndexAndType();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        if (CollectionUtils.isNotEmpty(idAndColumnValueList)) {
            checkClassType(idAndColumnValueList.get(0));
        }
        for (DataObject data : idAndColumnValueList) {
            IndexRequestBuilder indexBuilder = client.prepareIndex(dataBase, table);
            indexBuilder.setId(getIdFromBean(data)).setSource(beanToMap(data, nullSet));
            bulkRequest.add(indexBuilder);
        }
        log.info(bulkRequest.toString());
        BulkResponse response = bulkRequest.get();
        return !response.hasFailures();
    }

    public SearchRequestBuilder getSearchBuilder() {
        return client.prepareSearch(dataBase).setTypes(table);
    }

    public SearchSelectResult select(String keywords, Integer pageNo, Integer pageSize, String... columnNames) {
        SearchRequestBuilder search = client.prepareSearch(dataBase).setTypes(table)
                .setTimeout(TimeValue.timeValueSeconds(60))
                .setFrom((pageNo - 1) * pageSize)
                .setSize(pageSize)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        if (columnNames.length > 0) {
            for (String column : columnNames) {
                search.setQuery(QueryBuilders.matchQuery(column, keywords));
            }
        } else {
            search.setQuery(QueryBuilders.queryString(keywords));
        }
        log.info(search.toString());
        SearchResponse response = search.execute().actionGet();
        return result(response, pageNo, pageSize);
    }

    public Map<String, Object> get(Long id) {
        GetRequest request = new GetRequest(dataBase, table, id.toString());
        log.info(request.toString());
        GetResponse response = client.get(request).actionGet();
        return response.getSource();
    }

    public CountAndMaxIdResult selectCountAndMaxId() {
        MaxBuilder maxBuilder = AggregationBuilders.max("maxId").field("id");

        SearchRequestBuilder search = client.prepareSearch(dataBase).setTypes(table)
                .addAggregation(maxBuilder)
                .setFrom(0)
                .setSize(0);
        log.info(search.toString());
        SearchResponse response = search.get();
        long total = response.getHits().getTotalHits();
        Max max = response.getAggregations().get("maxId");
        long maxId = (long) max.getValue();
        return new CountAndMaxIdResult(total, maxId);
    }

    public boolean exists(Long id) {
        GetRequest request = new GetRequest(dataBase, table, id.toString());
        log.info(request.toString());
        GetResponse response = client.get(request).actionGet();
        return !response.isSourceEmpty();
    }

/*
    public boolean update(DataObject bean, boolean nullUpdate) {
        checkClassType(bean);
        String id = getIdFromBean(bean);
        Map map = beanToMap(bean, nullUpdate);
        UpdateResponse response = client.prepareUpdate(dataBase, table, id.toString())
                .setDoc(map).get();
        return response.isCreated();
    }*/

    public boolean delete(Long id) {
        DeleteRequestBuilder builder = client.prepareDelete(dataBase, table, id.toString());
        log.info(builder.toString());
        DeleteResponse response = builder.get();
        return response.isFound();
    }

    /**
     * 删除table
     */
    private void dropType() {
        DeleteMappingRequest request = Requests.deleteMappingRequest(dataBase).types(table);
        log.info(request.toString());
        getClient().admin().indices().deleteMapping(request);
    }

    private void updateType() {
        XContentBuilder builder = getMapping(dataBase, dataObject.getClass());
        PutMappingRequest mapping = Requests.putMappingRequest(dataBase).type(table).source(builder);
        log.info(mapping.toString());
        PutMappingResponse response = client.admin().indices().putMapping(mapping).actionGet();
        if (!response.isAcknowledged()) {
            log.error(">>> init" + dataBase + " " + table + " error");
            throw new IllegalArgumentException(">>> init" + dataBase + " " + table + " error");
        }
    }

    protected SearchSelectResult result(SearchResponse res, Integer pageNo, Integer pageSize) {
        SearchSelectResult result = new SearchSelectResult();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        SearchHits totalHits = res.getHits();
        result.setTotal(totalHits.getTotalHits());
        List<Map<String, Object>> list = result.getList();
        SearchHit[] hits = totalHits.getHits();
        for (SearchHit hit : hits) {
            list.add(hit.getSource());
        }
        return result;
    }

    protected void init() {//初始化client
//        if (initedNode.get()) return;
        if (clusterName == null) throw new IllegalArgumentException(">>> clusterName不能为空");
        if (StringUtils.isBlank(hosts)) {
            client = NodeBuilder.nodeBuilder()
                    .client(true)//本应用不单独存储数据
                    .clusterName(clusterName).node().client();
        } else {
            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
            TransportClient transportClient = new TransportClient(settings);
            for (String host : hosts.split(",")) {
                try {//FIXME 支持ip1,ip2:port2-port2格式
                    transportClient.addTransportAddress(new InetSocketTransportAddress
                            (InetAddress.getByName(host.split(":")[0]), Integer.parseInt(host.split(":")[1])));
                } catch (Exception e) {
                    log.error(">>> hosts error:", e);
                    throw new IllegalArgumentException(">>> hosts error:", e);
                }
            }
            client = transportClient;
        }
        initIndexAndType();
//        initedNode.set(true);
    }

    protected void initIndexAndType() {
        if (dataBase == null) throw new IllegalArgumentException(">>> dataBase不能为空");
        if (table == null) throw new IllegalArgumentException(">>> tableName不能为空");
        if (dataObject == null) throw new IllegalArgumentException(">>> dataObject不能为空");
        if (!existsIndex(dataBase)) createIndex(dataBase);
        if (!existsType(dataBase, table)) createType(dataBase, table);
    }

    protected boolean existsIndex(String indexName) {
        IndicesExistsRequest request = new IndicesExistsRequest(indexName);
        return client.admin().indices().exists(request).actionGet().isExists();
    }

    protected void createIndex(String indexName) {
        CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).execute().actionGet();
        if (!response.isAcknowledged()) {
            log.error(">>> init" + indexName + " error");
            throw new IllegalArgumentException(">>> init" + indexName + " error");
        }
    }

    protected boolean existsType(String indexName, String typeName) {
        TypesExistsRequest typeRequest = new TypesExistsRequest(new String[]{indexName}, typeName);
        TypesExistsResponse typesExistsResponse = client.admin().indices().typesExists(typeRequest).actionGet();
        return typesExistsResponse.isExists();
    }

    protected void createType(String indexName, String typeName) {
        XContentBuilder builder = getMapping(indexName, dataObject.getClass());
        PutMappingRequest mapping = Requests.putMappingRequest(indexName).type(typeName).source(builder);
        PutMappingResponse response = client.admin().indices().putMapping(mapping).actionGet();
        if (!response.isAcknowledged()) {
            log.error(">>> init" + indexName + " " + typeName + " error");
            throw new IllegalArgumentException(">>> init" + indexName + " " + typeName + " error");
        }
    }

    protected static XContentBuilder getMapping(String indexType, Class aclass) {
        Map<String, Map<String, Object>> mapAggr = new HashMap<String, Map<String, Object>>();

        Field[] fields = aclass.getDeclaredFields();
        for (Field field : fields) {
            Class<?> classz = field.getType();
            field.setAccessible(true);
            Map<String, Object> map = new HashedMap();
            SearchFieldProperties annotation = field.getAnnotation(SearchFieldProperties.class);
            if (annotation != null) {
                if (annotation.type() != null) {
                    map.put("type", annotation.type());
                }
                if (annotation.store() != null) {
                    map.put("store", annotation.store());
                }
                if (annotation.index() != null) {
                    map.put("index", annotation.index());
                }
                if (annotation.analyzer() != null) {
                    map.put("analyzer", annotation.analyzer());
                }
            }
            if (map.get("type") == null) {
                String elsType = toElsType(classz);
                if (elsType != null) {
                    map.put("type", elsType);
                } else {
                    log.warn(aclass.toString() + "." + field.getName() + "类型占时不支持");
                }
            }

            mapAggr.put(field.getName(), map);
        }

        return mapToXContentBuilder(indexType, mapAggr);
    }

    protected static String getIdFromBean(Object bean) {
        Field field = null;
        try {
            field = bean.getClass().getDeclaredField("id");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("" + bean.getClass().toString() + "中不包含id字段");
        }
        Object idObj = null;
        try {
            idObj = field.get(bean);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("" + bean.getClass().toString() + "id值获取失败");
        }
        if (idObj == null) throw new IllegalArgumentException("" + bean.getClass().toString() + "id为空");

        Long id = Long.parseLong(idObj.toString());
        if (!(id > 0 && id % 1 == 0)) {
            throw new IllegalArgumentException("" + bean.getClass().toString() + "id必须大于0的整数");
        }
        return id.toString();
    }

    protected static Map beanToMap(Object bean, boolean nullUpdate) {
        Map returnMap = new HashMap();

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(bean);
                if (value == null) {
                    if (nullUpdate) returnMap.put(field.getName(), value);
                } else {
                    returnMap.put(field.getName(), value);
                }

            } catch (IllegalAccessException e) {
                log.error(bean.toString() + "获取" + field.getName() + "字段值失败");
            }
        }
        return returnMap;
    }

    protected static synchronized XContentBuilder mapToXContentBuilder(String indexType
            , Map<String, Map<String, Object>> fields) {
        try {
            XContentBuilder mapping = XContentFactory.jsonBuilder();
            mapping.startObject().startObject(indexType).startObject("properties");
            for (Map.Entry<String, Map<String, Object>> field : fields.entrySet()) {
                String name = field.getKey();
                Map<String, Object> indexed = field.getValue();
                String type = (String) indexed.get("type");
                String store = (String) indexed.get("store");
                String analyzed = (String) indexed.get("index");
//                String analyzer = (String) indexed.get("analyzer");//指定 分词器

                if (StringUtils.isEmpty(type)) type = "string";
                mapping.startObject(name).field("type", type);
                if (StringUtils.isNotBlank(store)) mapping.field("store", store);
                if (StringUtils.isNotBlank(analyzed)) mapping.field("index", analyzed);
//                if (StringUtils.isNotEmpty(analyzer)) mapping.field("analyzer", analyzer);
                mapping.endObject();
            }
            mapping.endObject().endObject().endObject();
            return mapping;
        } catch (Exception e) {
            log.error("can not create mapping for '" + indexType + "'", e);
        }
        return null;
    }

    protected static String toElsType(Class classz) {
        if (classz.equals(String.class) || classz.equals(char.class) || classz.equals(Character.class)) {
            return "string";
        }
        if (classz.equals(Long.class) || classz.equals(long.class)) {
            return "long";
        }
        if (classz.equals(Integer.class) || classz.equals(int.class)) {
            return "integer";
        }
        if (classz.equals(Short.class) || classz.equals(short.class)) {
            return "short";
        }
        if (classz.equals(Byte.class) || classz.equals(byte.class)) {
            return "byte";
        }
        if (classz.equals(Double.class) || classz.equals(double.class)) {
            return "double";
        }
        if (classz.equals(Float.class) || classz.equals(float.class)) {
            return "float";
        }
        if (classz.equals(Date.class) || classz.equals(java.sql.Date.class) || classz.equals(Timestamp.class)) {
            return "date";//FIXME test timestamp支持
        }
        if (classz.equals(Boolean.class) || classz.equals(boolean.class)) {
            return "boolean";
        }
        return null;
    }

    protected void checkClassType(Object bean) {
        Class classz = bean.getClass();
        if (!dataObject.equals(classz)) {
            throw new IllegalArgumentException(">>> " + bean.getClass() + "无法造型成dataObject" + dataObject);
        }
    }


    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setDataObject(Class<DataObject> dataObject) {
        this.dataObject = dataObject;
    }

    public Client getClient() {
        return client;
    }

}
