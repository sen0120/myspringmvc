package com.biz.test3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

public class TestElastic {
    private static Client client;

    static{
        Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch-cluster-centos").build();
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300))
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9301))
            ;
        } catch (UnknownHostException e) {
        }
    }


    public static void createIndex() throws IOException{
        //创建索引
        //client.admin().indices().prepareCreate("kbs").execute().actionGet();
        XContentBuilder builder= XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("questionanswer4")
                        .startObject("properties")
                            .startObject("article_title")
                                .field("type", "string")
                                .field("store", "yes")
                                .field("index","analyzed")
                                .field("analyzer","ik_max_word")
                            .endObject()
                            .startObject("article_content")
                                .field("type", "string")//数据类型
                                .field("store", "no")//是否存储
                                .field("index","analyzed")//是否分词
                                .field("analyzer","ik_smart")//分词器
                            .endObject()
                            .startObject("article_url")
                                .field("type", "string")
                                .field("store", "yes")
                                .field("index","not_analyzed")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        //创建索引结构  pages对应indices对应数据库   sina对应type对应表table  document对应记录row  field对应字段column
        PutMappingRequest mapping = Requests.putMappingRequest("index2").type("questionanswer4").source(builder);
        client.admin().indices().putMapping(mapping).actionGet();

        client.close();
    }

    public static void insertIndex() {//添加索引数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("article_title", "this is title");
        map.put("article_url", "this is url");
        //2.生成一个索引。这里用Map来保存json数据，然后插入到index为“twitter”的索引里面，其document为“tweet”，id为“1”。当然，生成json数据的方法很多，朋友们可以查看相关api。
        //CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("indexName").setSettings();
        IndexResponse indexResponse = client.prepareIndex("kbs", "article").setSource(map).execute().actionGet();

    }

    public static void getIndex() {
        GetResponse response = client.prepareGet("twitter", "tweet", "1").execute().actionGet();
        Map<String, Object> rpMap = response.getSource();
        if (rpMap == null) {
            System.out.println("empty");
            return;
        }
        Iterator<Map.Entry<String, Object>> rpItor = rpMap.entrySet().iterator();
        while (rpItor.hasNext()) {
            Map.Entry<String, Object> rpEnt = rpItor.next();
            System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
        }
    }

    public static void scrollSearch() {
        //QueryBuilder query = QueryBuilders.termQuery("context", "");
        SearchRequestBuilder sbuilder = client.prepareSearch("index")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000)) //超时时间
                //.setQuery(query) // Query
                .setFrom(0)
                .setSize(2);
        //System.err.println(sbuilder);
        SearchResponse scrollResp = sbuilder.execute().actionGet();
        //System.out.println(scrollResp);
        /*while (true) {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...]
                System.err.println(hit.getSource());
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            System.err.println(scrollResp);
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }*/
        for (SearchHit hit : scrollResp.getHits().getHits()) {
            //Handle the hit...]
            System.err.println(hit.getSource());
        }
    }

    public static void deleteType(){
        client.prepareDelete().setIndex("kbs").setType("article").execute().actionGet();
    }

    public static void search(){
        SearchResponse response = client.prepareSearch()
//                .setTypes("fulltext")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery("John"))                 // Query
                //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                //.setFrom(0).setSize(2).setExplain(true)
                .execute()
                .actionGet();
        //System.err.println(response);
        SearchHit[] hits = response.getHits().getHits();
        for (int i = 0; i < hits.length; i++) {
            System.err.println(hits[i].getSource());
            System.err.println(hits[i].getId());
        }
    }

    public static void main(String[] args) throws Exception {
//        createIndex();
        search();
        Long id = new Long(10);
        String idStr = id.longValue()+"";
        System.out.println(idStr);
    }

}
