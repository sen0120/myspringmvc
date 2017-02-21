package com.test;

import com.google.gson.JsonArray;
import org.apache.commons.lang.math.RandomUtils;

import java.io.IOException;

/**
 * Created by fanyun on 16/7/12.
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        JsonArray hintArray = new JsonArray();
        hintArray.add("111");
        hintArray.add("\"2222\"");
        System.out.println(hintArray.toString().replace("\\\"", "'"));

        System.out.println("".endsWith("\n"));
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
