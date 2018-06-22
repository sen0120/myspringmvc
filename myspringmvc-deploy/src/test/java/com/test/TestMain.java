package com.test;

import com.alibaba.fastjson.JSONObject;
import com.test.elasticsearch.domain.Student;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fanyun on 16/7/12.
 */
public class TestMain {
    private static Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {

        try {
            throw new RuntimeException("sdf");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            System.err.println(111);
        }
       /* Student student = new Student();
        student.settPlanTrade(true);
        student.settPlanTradeCreateDate(new Date());
        student.setMale(true);
        String sfStr = net.sf.json.JSONObject.fromObject(student).toString();
        String fastStr = JSONObject.toJSONString(student);
        System.out.println(sfStr);
        System.out.println(fastStr);

        Object sfStudent = net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(sfStr), Student.class);
        Student fastStudent = JSONObject.parseObject(fastStr, Student.class);
        System.out.println(sfStudent);
        System.out.println(fastStudent);*/

        //from space 4096K,  20% used [7fac00000, 7facd3ba8, 7fb000000)
        long a = Long.parseLong("7fac00000", 16);
        long b = Long.parseLong("7facd3ba8", 16);
        long c = Long.parseLong("7fb000000", 16);
        System.out.println((b - a) / 1024);
        System.out.println((c - a) / 1024);
    }

    public static String mapToSortString(Map map) {
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            //升序排序
            public int compare(Map.Entry<String, Object> o1,
                               Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }

        });
        return JSONObject.toJSONString(list);
    }

    public static String getSeqId() {
        /*StringBuilder sb = new StringBuilder();
        String formatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        sb.append(formatTime);*/
        int ranNum = RandomUtils.nextInt(1000);
        /*sb.append(ranNum);*/

        return ranNum + "";
    }

}
