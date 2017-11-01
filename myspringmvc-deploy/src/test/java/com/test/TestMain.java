package com.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by fanyun on 16/7/12.
 */
public class TestMain {
    private static Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {


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