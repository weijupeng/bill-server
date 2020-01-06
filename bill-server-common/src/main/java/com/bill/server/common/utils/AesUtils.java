package com.bill.server.common.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>
 * Description: Aes加解密
 * </p>
 * @create: 2019-10-11 17:14
 * @author: kancy
 */
public class AesUtils {

    public static final String key = "qazwsxedcrfvtgb0";

    /**
     * AES的加密函数
     * @param str 传入需要加密的字符
     * @param key 传入一个16位长度的密钥。否则报错
     * @return 执行成功返回加密结果，否则报错
     * @throws Exception 抛出一个加密异常
     */
    public static String aesEncrypt(String str, String key) throws Exception {
        if (Objects.isNull(str) || Objects.isNull(key)) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * AES的解密函数
     * @param str 传入需要解密的字符
     * @param key 传入一个16位长度的密钥。否则报错
     * @return 执行成功返回加密结果，否则报错
     * @throws Exception 抛出一个解密异常
     */
    public static String aesDecrypt(String str, String key) throws Exception {
        if (Objects.isNull(str) || Objects.isNull(key)) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] bytes = Base64Utils.decodeFromString(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
