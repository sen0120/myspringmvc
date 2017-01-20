package com.hus.biz.test.search;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * @author huangfox
 * @date 2014年2月10日 下午3:27:43
 *
 */
public class ESClient {

    private Client client;

    public void init() {
        //client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }

    public void close() {
        client.close();
    }

    /**
     * index
     */
    public void createIndex() {
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setId(new Long(i));
            user.setName("huang fox " + i);
            user.setAge(i % 100);
            client.prepareIndex("users", "user").setSource(generateJson(user)).execute().actionGet();
        }
    }

    /**
     * 转换成json对象
     *
     * @param user
     * @return
     */
    private String generateJson(User user) {
        String json = "";
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
            contentBuilder.field("id", user.getId() + "");
            contentBuilder.field("name", user.getName());
            contentBuilder.field("age", user.getAge() + "");
            json = contentBuilder.endObject().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void main(String[] args) {
        ESClient client = new ESClient();
        client.init();
        client.createIndex();
        client.close();
    }


    /*public void search() {
        SearchResponse response = client.prepareSearch("users")
                .setTypes("user")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("name", "fox")) // Query
                .setFilter(FilterBuilders.rangeFilter("age").from(20).to(30)) // Filter
                .setFrom(0).setSize(60).setExplain(true).execute().actionGet();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsString());
        }
    }*/

}