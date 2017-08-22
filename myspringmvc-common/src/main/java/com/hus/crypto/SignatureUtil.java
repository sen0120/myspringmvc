package com.hus.crypto;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fanyun on 17/6/14.
 * 签名工具类
 */
public class SignatureUtil {
    private final static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * map转成treemap(key排序)后转成json
     *
     * @param map
     * @return 按照一定规则对key进行排序, 然后转成json字符串
     */
    private static String mapToSortString(Map map) {
        Map treeMap = new TreeMap();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next != null) {
                treeMap.put(next, map.get(next));
            }
        }
        return JSONObject.toJSONString(treeMap);
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));

            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            if (StringUtils.isBlank(encode)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(encode));
            }
            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            logger.error("SignatureUtil sign:", e);
        }
        return null;
    }

    public static String sign(String content, String privateKey) {
        return sign(content, privateKey, null);
    }

    public static String sign(Map map, String privateKey, String encode) {
        return sign(mapToSortString(map), privateKey, encode);
    }

    public static String sign(Map map, String privateKey) {
        return sign(mapToSortString(map), privateKey);
    }


    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            if (StringUtils.isBlank(encode)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(encode));
            }
            boolean bverify = signature.verify(Base64.decode(sign));
            return bverify;

        } catch (Exception e) {
            logger.error("SignatureUtil doCheck:", e);
        }

        return false;
    }

    public static boolean doCheck(String content, String sign, String publicKey) {
        return doCheck(content, sign, publicKey, null);
    }

    /**
     * @param mapWithSignature 不包含签名参数的map
     * @param sign             签名
     * @param publicKey        公钥
     * @param encode           编码
     * @return
     */
    public static boolean doCheck(Map mapWithSignature, String sign, String publicKey, String encode) {
        return doCheck(mapToSortString(mapWithSignature), sign, publicKey, encode);
    }

    /**
     * @param mapWithSignature 不包含签名参数的map
     * @param sign             签名
     * @param publicKey        公钥
     * @return
     */
    public static boolean doCheck(Map mapWithSignature, String sign, String publicKey) {
        return doCheck(mapToSortString(mapWithSignature), sign, publicKey);
    }
}
