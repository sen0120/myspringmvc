package com.test.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.test.elasticsearch.domain.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.ScoreDoc;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class TestElastic {
    private static final Log log = LogFactory.getLog(TestElastic.class);
    private final static String INDEX_KBS = "kbs";
    private final static String TYPE_QUESTION_ANSWER = "questionanswer";
    private final static String TYPE_ARTICLE = "article";
    @Value("${elasticsearch.ip}")
    private String ip;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.cluster.name}")
    private String cluster_name;

    public Client getClient() {
        Client client = null;
        try {
            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch-cluster-centos").build();
            client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port))
            //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301))
            ;
        } catch (UnknownHostException e) {
            log.error(">>> getClient error:", e);
        }
        return client;
    }

    public void closeClient(Client client) {
        if (client != null) {
            client.close();
        }
    }

    public void createIndex() throws Exception {//添加问答内容到索引
        Client client = null;
        try {
            client = getClient();

            // 创建index和type
            IndicesExistsResponse existsResponse = client.admin().indices().exists(new IndicesExistsRequest().indices(new String[]{INDEX_KBS})).actionGet();
            if (!existsResponse.isExists()) {
                client.admin().indices().create(new CreateIndexRequest(INDEX_KBS)).actionGet();//index
            }

            //type
            TypesExistsResponse typeResponse = null;
            typeResponse = getClient().admin().indices().typesExists(new TypesExistsRequest(new String[]{INDEX_KBS}, TYPE_QUESTION_ANSWER)).actionGet();
            if (!typeResponse.isExists()) {
                createQuestionAnswerType(client);
            }
            /*typeResponse = getClient().admin().indices().typesExists(new TypesExistsRequest(new String[]{INDEX_KBS}, TYPE_ARTICLE)).actionGet();
            if(!typeResponse.isExists()){
                createArticleType(client);
            }*/

            // 把数据放入bulkRequest中
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            // 取数据源
            List<QuestionAnswer> questionList = new ArrayList<QuestionAnswer>();
            for (QuestionAnswer questionAnswer : questionList) {
                QuestionAnswerForElastic elastic = new QuestionAnswerForElastic();
                BeanUtils.copyProperties(elastic, questionAnswer);
                elastic.setCreateTime(questionAnswer.getCreateTime());
                elastic.setModifyTime(questionAnswer.getModifyTime());
                elastic.setPublishTime(questionAnswer.getPublishTime());
                String map = JSONObject.toJSONString(elastic);
                bulkRequest.add(client.prepareIndex(INDEX_KBS, TYPE_QUESTION_ANSWER, questionAnswer.getId().longValue() + "").setRefresh(true).setSource(map));
            }
           /* List<Article> articleList = articleService.list(new Article());
            for (Article article: articleList) {
                ArticleForElastic elastic = new ArticleForElastic();
                BeanUtils.copyProperties(elastic,article);
                bulkRequest.add(client.prepareIndex(INDEX_KBS,TYPE_ARTICLE).setId(article.getId().longValue()+"").setSource(BeanUtils.describe(elastic)));
            }*/
            // 执行导入
            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                Iterator<BulkItemResponse> iter = bulkResponse.iterator();
                while (iter.hasNext()) {
                    BulkItemResponse itemResponse = iter.next();
                    if (itemResponse.isFailed()) {
                        log.error(itemResponse.getFailureMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> createIndex ERROR!", e);
        } finally {
            closeClient(client);
        }
    }

    private void createQuestionAnswerType(Client client) {
        XContentBuilder builder = null;
        try {
            builder = jsonBuilder()
                    .startObject()
                    .startObject(TYPE_QUESTION_ANSWER)
                    .startObject("properties")
                    .startObject("id")
                    .field("type", "long")//string
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("catalogId")
                    .field("type", "long")//string
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("question")
                    .field("type", "string")//string long
                    .field("store", "yes")//yes no
                    .field("index", "analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("answer")
                    .field("type", "string")//string long
                    .field("store", "no")//yes no
                    .field("index", "analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("sort")
                    .field("type", "integer")//string long
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("chartUrl")
                    .field("type", "string")//string long
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("isHot")
                    .field("type", "integer")//string long
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("isPublish")
                    .field("type", "integer")//string long
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("modifyUser")
                    .field("type", "string")//string long
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("publishUser")
                    .field("type", "string")//string long
                    .field("store", "no")//yes no
                    .field("index", "not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("publishTime")
                    .field("type", "date")//string long
                    .field("format", "yyyy-MM-dd HH:mm:ss")
                    //.field("store", "no")//yes no
                    //.field("index","not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("createTime")
                    .field("type", "date")//string long
                    .field("format", "yyyy-MM-dd HH:mm:ss")
                    //.field("store", "no")//yes no
                    //.field("index","not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()

                    .startObject("modifyTime")
                    .field("type", "date")//string long
                    .field("format", "yyyy-MM-dd HH:mm:ss")
                    //.field("store", "no")//yes no
                    //.field("index","not_analyzed")//no not_analyzed analyzed
                    //.field("analyzer","ik_max_word")//ik ik_max_word ik_smart
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            //创建索引结构  pages对应indices对应数据库   sina对应type对应表table  document对应记录row  field对应字段column
            PutMappingRequest mapping = Requests.putMappingRequest(INDEX_KBS).type(TYPE_QUESTION_ANSWER).source(builder);
            PutMappingResponse response = client.admin().indices().putMapping(mapping).actionGet();
            System.out.println(1);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(">>> createQuestionAnswerType ERROR!", e);
        }
    }

    public List<QueryResultVO> doSearch(String queryStr, ScoreDoc scoreDoc, int size, int pageNo) throws Exception {
        Client client = null;
        List<QueryResultVO> resultList = new ArrayList<QueryResultVO>();
        Set<Long> questionAnswerIdSet = new HashSet<Long>();//id是否重复添加
        try {
            client = getClient();
            /*SearchResponse response = client.prepareSearch()
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.queryStringQuery("John"))
                    .setScroll(new TimeValue(60000)) //超时时间
                    .setFrom((pageNo-1)*size)
                    .setSize(size)
                    .execute()
                    .actionGet();*/
            SearchRequestBuilder srQuestion = client.prepareSearch(INDEX_KBS).setTypes(TYPE_QUESTION_ANSWER)
                    .setScroll(new TimeValue(60000)) //超时时间
                    .setFrom((pageNo - 1) * size)
                    .setSize(size)
                    .setQuery(QueryBuilders.matchQuery("question", queryStr));
            SearchRequestBuilder srAnswer = client.prepareSearch(INDEX_KBS).setTypes(TYPE_QUESTION_ANSWER)
                    .setScroll(new TimeValue(60000)) //超时时间
                    .setFrom((pageNo - 1) * size)
                    .setSize(size)
                    .setQuery(QueryBuilders.matchQuery("answer", queryStr));

            MultiSearchResponse msrQuestion = client.prepareMultiSearch().add(srQuestion).execute().actionGet();
            getQuestionAnswerResultFromResponse(resultList, questionAnswerIdSet, msrQuestion);

            MultiSearchResponse msrAnswer = client.prepareMultiSearch().add(srAnswer).execute().actionGet();
            getQuestionAnswerResultFromResponse(resultList, questionAnswerIdSet, msrAnswer);

            sortListByScore(resultList);
            /*SearchResponse response = client.prepareSearch(INDEX_KBS)
                    .setTypes(TYPE_QUESTION_ANSWER)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.queryString(queryStr))
                    .setScroll(new TimeValue(60000)) //超时时间
                    .setFrom((pageNo-1)*size)
                    .setSize(size)
                    .execute()
                    .actionGet();
            SearchHit[] hits = response.getHits().getHits();
            for (int i = 0; i < hits.length; i++) {
                Map<String, Object> source = hits[i].getSource();
                QueryResultVO vo = convertMapToVo(source);
                resultList.add(vo);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> doSearch error:", e);
        } finally {
            closeClient(client);
        }
        return resultList;
    }

    private void getQuestionAnswerResultFromResponse(List<QueryResultVO> resultList, Set<Long> questionAnswerIdSet, MultiSearchResponse msrAnswer) {
        for (MultiSearchResponse.Item item : msrAnswer.getResponses()) {
            SearchResponse response = item.getResponse();
            SearchHit[] hits = response.getHits().getHits();
            for (int i = 0; i < hits.length; i++) {
                QueryResultVO vo = convertMapToVo(hits[i].getSource(), hits[i].getScore());
                if (!questionAnswerIdSet.contains(vo.getId())) {
                    resultList.add(vo);
                }
                if (vo.getId() != null) {
                    questionAnswerIdSet.add(vo.getId());
                }
            }
        }
    }

    private void sortListByScore(List<QueryResultVO> resultList) {
        Collections.sort(resultList, new Comparator<QueryResultVO>() {
            public int compare(QueryResultVO o1, QueryResultVO o2) {
                if (o1.getScore() > o2.getScore()) {
                    return -1;
                } else if (o1.getScore() == o2.getScore()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

    }

    private QueryResultVO convertMapToVo(Map<String, Object> source, float score) {
        QueryResultVO resultVO = new QueryResultVO();
        Object id = source.get("id");
        if (id != null && !id.equals("")) {
            resultVO.setId(new Long(id.toString()));
        }

        Object question = source.get("question");
        if (question != null) {
            resultVO.setTitle(question.toString());
        }

        Object chartUrl = source.get("chartUrl");
        if (chartUrl != null) {
            resultVO.setImgPath(chartUrl.toString());
        }

        Object isPublish = source.get("isPublish");
        if (isPublish != null) {
            resultVO.setIsPublish(isPublish.toString());
        }

        Object answer = source.get("answer");
        if (answer != null) {
            resultVO.setContent(answer.toString());
        }
//		resultVO.setTitle1(title1);

        resultVO.setType(1);
        Object sort = source.get("sort");
        if (sort != null && !sort.equals("") && !sort.equals(0)) {
            resultVO.setSort(Integer.parseInt(sort.toString()));
        }

        resultVO.setScore(score);

        return resultVO;
    }

    public void addDocAticle(Article result) throws IOException {
        Client client = null;
        try {
            client = getClient();
            ArticleForElastic elastic = new ArticleForElastic();
            BeanUtils.copyProperties(elastic, result);
            elastic.setCreateTime(result.getCreateTime());
            elastic.setModifyTime(result.getModifyTime());
            elastic.setPublishTime(result.getPublishTime());
            String map = JSONObject.toJSONString(elastic);
            IndexResponse response = client.prepareIndex(INDEX_KBS, TYPE_ARTICLE).setId(result.getId().longValue() + "").setSource(map).get();
            //System.out.println(response.getId() + "====" + response.getIndex() + "====" + response.getType());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> addDocAticle error:", e);
        } finally {
            closeClient(client);
        }
    }

    public void modifyDocAticle(Article result) throws IOException {
        Client client = null;
        try {
            client = getClient();
            ArticleForElastic elastic = new ArticleForElastic();
            BeanUtils.copyProperties(elastic, result);
            elastic.setCreateTime(result.getCreateTime());
            elastic.setModifyTime(result.getModifyTime());
            elastic.setPublishTime(result.getPublishTime());
            String map = JSONObject.toJSONString(elastic);
            UpdateRequest updateRequest = new UpdateRequest(INDEX_KBS, TYPE_ARTICLE, result.getId().longValue() + "").doc(map);
            UpdateResponse response = client.update(updateRequest).get();
//            System.out.println(response.getId() + "====" + response.getIndex() + "====" + response.getType());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> modifyDocAticle error:", e);
        } finally {
            closeClient(client);
        }
    }

    public void deleteArticleIndex(Long id) throws IOException {
        Client client = null;
        try {
            client = getClient();

            client.prepareDelete(INDEX_KBS, TYPE_ARTICLE, id.longValue() + "").get();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> deleteArticleIndex error:", e);
        } finally {
            closeClient(client);
        }
    }

}
