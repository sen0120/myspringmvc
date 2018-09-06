package com.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by fanyun on 16/7/12.
 */
public class TestMain {
    private static Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(10);
        bitSet.set(65, true);
        System.out.println(bitSet.get(65));


        /*BitSet bitSet = new BitSet(10);
        bitSet.set(0);
        bitSet.set(65);
        System.out.println(bitSet.get(0));
        bitSet.flip(0);
        bitSet.flip(65);
        System.out.println(bitSet.get(0));
        System.out.println(bitSet.size());
        System.out.println(bitSet.length());*/

//        bitset();
//        main3();
//        main4();
    }

    private static void bitset() {
        Random random = new Random();

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int randomResult = random.nextInt(20);
            list.add(randomResult);
        }
        System.out.println("产生的随机数有");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        BitSet bitSet = new BitSet(20);
        for (int i = 0; i < 10; i++) {
            bitSet.set(list.get(i));
        }

        System.out.println("0~1亿不在上述随机数中有" + bitSet.size());
        for (int i = 0; i < 20; i++) {
            if (!bitSet.get(i)) {
                System.out.println(i);
            }
        }
    }

    private static void main4() {
        BigDecimal bigDecimal = new BigDecimal("3.3432");
        BigDecimal bigDecimal1 = bigDecimal.setScale(2, RoundingMode.DOWN);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal1);

        String x = "a";
        String a = "ac";
        String b = "ac";//常量池已经存在,存在会直接引用
        System.out.println(b.toString());
        String c = "a" + "c";//编译器自动优化成:String c = "ac";(因为值的固定的,编译器可以优化)
        String d = x + "c";//编译器不会优化,会创建新对象.(因为引用的值是不确定的,一定需要先创建新的String对象,这个时候再去常量池找就没意义了)
        System.out.println(c);
    }

    private static void main3() {
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
        int ranNum = RandomUtils.nextInt(100);
        /*sb.append(ranNum);*/

        return ranNum + "";
    }

}
