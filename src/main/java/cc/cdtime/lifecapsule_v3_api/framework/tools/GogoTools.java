package cc.cdtime.lifecapsule_v3_api.framework.tools;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

public class GogoTools {
    /**
     * 生成一个UUID
     *
     * @return
     * @throws Exception
     */
    public static String UUID32() throws Exception {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        return uuidStr;
    }

    /**
     * 返回两个日期之间相差的秒数
     *
     * @param dateStart
     * @param dateEnd
     * @return
     * @throws Exception
     */
    public static Integer differentSeconds(Date dateStart, Date dateEnd) throws Exception {
        int seconds = (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000));
        return seconds;
    }

    /**
     * 对用户密码进行MD5加密
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String encoderByMd5(String password) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] newpass = Base64.encodeBase64(md5.digest(password.getBytes("utf-8")));
        String str = new String(newpass);
        return str;

    }

    /**
     * 生成一对RSA公钥和私钥
     *
     * @return
     * @throws Exception
     */
    public static Map generateRSAKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map out = new HashMap();
        out.put("publicKey", Base64.encodeBase64String(rsaPublicKey.getEncoded()));
        out.put("privateKey", Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        return out;
    }

    /**
     * 用私钥来解密
     *
     * @param src
     * @param rsaPrivateKey
     * @return
     * @throws Exception
     */
    public static String decryptRSAByPrivateKey(String src, String rsaPrivateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaPrivateKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(src.getBytes()));
        Map out = new HashMap();
        return new String(result);
    }

    /**
     * AES加密
     *
     * @param codec
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptAESKey(String codec, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        byte[] dataBytes = codec.getBytes("UTF-8");//如果有中文，记得加密前的字符集
        SecretKey keyspec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keyspec);
        byte[] encrypted = cipher.doFinal(dataBytes);
        String outCode = Base64.encodeBase64String(encrypted);
        return outCode;
    }

    /**
     * 如果Date1>Date2，返回True
     *
     * @param DATE1
     * @param DATE2
     * @return
     * @throws Exception
     */
    public static boolean compare_date(Date DATE1, Date DATE2) throws Exception {
        Date dt1 = DATE1;
        Date dt2 = DATE2;
        if (dt1.getTime() > dt2.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 生成一个随机字符串
     *
     * @param length
     * @return
     * @throws Exception
     */
    public static String generateString(int length) throws Exception {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机生成字符，只有数字
     *
     * @param length id长度
     */
    public static Long getNumber(Integer length) {
        String uid = "";
        for (Integer i = 0; i < length; i++) {
            String randChar = String.valueOf(Math.round(Math.random() * 9));
            uid = uid.concat(randChar);
        }
        Long num = Long.parseLong(uid);
        return num;
    }

    public static String convertMapToString(HashMap map) throws Exception {
        Iterator iter = map.entrySet().iterator();
        String outStr = "";
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            outStr += key.toString() + ":";
            Object val = entry.getValue();
            if (val != null) {
                outStr += val.toString() + "/";
            } else {
                outStr += "null/";
            }
        }
        if (outStr.length() > 2000) {
            outStr = outStr.substring(0, 2000);
        }
        return outStr;
    }

    /**
     * 比较两个double数据大小
     *
     * @param double1
     * @param double2
     * @return 小于-1，等于0，大于1
     * @throws Exception
     */
    public static int compareDouble(Double double1, Double double2) throws Exception {
        BigDecimal data1 = new BigDecimal(double1);
        BigDecimal data2 = new BigDecimal(double2);
        return data1.compareTo(data2);
    }

}
