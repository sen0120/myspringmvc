package com.hus.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * rsa加密/解密
 */
public class RSAUtil {
    private final static Logger logger = LoggerFactory.getLogger(RSAUtil.class);
    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String TEST_PUBLIC_KEY_PATH = "~/publicKey.keystore";
    private static final String TEST_PRIVATE_KEY_PATH = "~/privateKey.keystore";

    public static void main(String[] args) throws Exception {
        //RSAUtil.genKeyPair(filepath);


        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "ihep_公钥加密私钥解密";
        //公钥加密过程
        byte[] cipherData = RSAUtil.encrypt(RSAUtil.loadPublicKeyByStr(RSAUtil.loadSecretKeyByFile(TEST_PUBLIC_KEY_PATH)), plainText.getBytes());
        String cipher = Base64.encode(cipherData);
        //私钥解密过程
        byte[] res = RSAUtil.decrypt(RSAUtil.loadPrivateKeyByStr(RSAUtil.loadSecretKeyByFile(TEST_PRIVATE_KEY_PATH)), Base64.decode(cipher));
        String restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("--------------私钥加密公钥解密过程-------------------");
        plainText = "ihep_私钥加密公钥解密";
        //私钥加密过程
        cipherData = RSAUtil.encrypt(RSAUtil.loadPrivateKeyByStr(RSAUtil.loadSecretKeyByFile(TEST_PRIVATE_KEY_PATH)), plainText.getBytes());
        cipher = Base64.encode(cipherData);
        //公钥解密过程
        res = RSAUtil.decrypt(RSAUtil.loadPublicKeyByStr(RSAUtil.loadSecretKeyByFile(TEST_PUBLIC_KEY_PATH)), Base64.decode(cipher));
        restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("---------------私钥签名过程------------------");
        String content = "ihep_这是用于签名的原始数据";
        String signstr = SignatureUtil.sign(content, RSAUtil.loadSecretKeyByFile(TEST_PRIVATE_KEY_PATH));
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signstr);
        System.out.println();

        System.out.println("---------------公钥校验签名------------------");
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signstr);

        System.out.println("验签结果：" + SignatureUtil.doCheck(content, signstr, RSAUtil.loadSecretKeyByFile(TEST_PUBLIC_KEY_PATH)));
        System.out.println();
    }

    /**
     * 随机生成密钥对
     */
    private static void genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.error("RSAUtil genKeyPair:", e);
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(512, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        try {
            // 得到公钥字符串
            String publicKeyString = Base64.encode(publicKey.getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64.encode(privateKey.getEncoded());
            System.out.println("---------------生成公钥密钥------------------");
            System.out.println("公钥串：");
            System.out.println(publicKeyString);
            System.out.println("密钥串：");
            System.out.println(privateKeyString);
            // 将密钥对写入到文件
            FileWriter pubfw = new FileWriter(TEST_PUBLIC_KEY_PATH);
            FileWriter prifw = new FileWriter(TEST_PRIVATE_KEY_PATH);
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        } catch (Exception e) {
            logger.error("RSAUtil genKeyPair:", e);
        }
    }

    /**
     * 从文件中加载密钥
     *
     * @param pathAndkeyFileName 私钥文件路径及名称名
     * @return 是否成功
     * @throws Exception
     */
    public static String loadSecretKeyByFile(String pathAndkeyFileName) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathAndkeyFileName));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("密钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("密钥输入流为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 公钥加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            logger.error("RSAUtil encrypt:", e);
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            logger.error("RSAUtil decrypt:", e);
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey  公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            logger.error("RSAUtil decrypt:", e);
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            // 取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            /*if (i < data.length - 1) {
                stringBuilder.append(' ');
            }*/
        }
        return stringBuilder.toString();
    }

}