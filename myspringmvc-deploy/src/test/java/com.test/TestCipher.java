package com.test;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by fanyun on 17/6/13.
 */
public class TestCipher {
    //对称性加密 AES DES
    public static void main(String[] args) throws Exception {
        //algorithm
        //crypto
        //secure
//        javax.security
//        javax.crypto

        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = "32423423sdf3".getBytes();
        byte[] md5Bytes = md5.digest(bytes);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        System.out.println(hexValue.toString());


        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        byte[] bytes1 = "32423423sdf3".getBytes();
        byte[] digest = md.digest(bytes1);
        BigInteger bigInteger = new BigInteger(1, digest);

        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        System.out.println(bigInteger.toString(2));
        System.out.println(bigInteger.toString(8));
        System.out.println(bigInteger.toString(10));
        System.out.println(bigInteger.toString(16));
        System.out.println(bigInteger.toString(32));
    }


}
