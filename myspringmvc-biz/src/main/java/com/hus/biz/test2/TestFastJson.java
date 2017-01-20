package com.hus.biz.test2;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class TestFastJson {

    public static void main(String[] args) {

        User entity = new User();
        UserAggr aggr = new UserAggr();
        System.out.println(JSONObject.toJSONString(aggr));
    }
}

class UserAggr {
    List<User> list;
    @JSONField(serialzeFeatures = {
            SerializerFeature.WriteMapNullValue,SerializerFeature.PrettyFormat})
    User user;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

class User {
    @JSONField(serialzeFeatures = {
            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero})
    private Long id;
    private Number a;
    @JSONField(serialize = false)
    private String username;
    @JSONField(serialzeFeatures = {
            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty})
    private String password;
    @JSONField(serialzeFeatures = {
            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty})
    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Number getA() {
        return a;
    }

    public void setA(Number a) {
        this.a = a;
    }

    /**
     * 动态授权令牌
     */
    private String token;
    /**
     * 过期时间
     */
    private String expired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}

