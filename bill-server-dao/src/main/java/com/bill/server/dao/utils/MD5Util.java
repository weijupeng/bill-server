package com.bill.server.dao.utils;

/**
 * @author kancy
 * @version 1.0
 * @date 2019/1/28 14:21
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 * @author kancy
 * @version 1.0
 * @date 2019/1/28 11:16
 */
public class MD5Util {

    /**
     * 获取MD5加密字符串
     * @param source 明文字符串
     * @return 加密后字符串
     */
    public static String getStringMd5(String source){
        Charset utf8 = Charset.forName("UTF-8");
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(source.getBytes(utf8));
            return toHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个文件的md5值
     * @return md5 value
     */
    public static String getFileMd5(String filePath) {
        return getFileMd5(new File(filePath));
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     * @return md5 value
     */
    public static String getFileMd5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return toHexString(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeInputStream(fileInputStream);
        }
        return null;
    }

    /**
     * 验证Md5
     * @param string
     * @param md5
     * @return
     */
    public static boolean verifyStringMd5(String string , String md5){
        String newMd5 = getStringMd5(string);
        return (newMd5 == null || md5 == null) ? false : newMd5.equalsIgnoreCase(md5);
    }
    /**
     * 验证Md5
     * @param file
     * @param md5
     * @return
     */
    public static boolean verifyFileMd5(File file , String md5){
        String newMd5 = getFileMd5(file);
        return (newMd5 == null || md5 == null) ? false : newMd5.equalsIgnoreCase(md5);
    }
    /**
     * 验证Md5
     * @param filePath
     * @param md5
     * @return
     */
    public static boolean verifyFileMd5(String filePath , String md5){
        String newMd5 = getFileMd5(filePath);
        return (newMd5 == null || md5 == null) ? false : newMd5.equalsIgnoreCase(md5);
    }

    private static String toHexString(byte[] b) {
        StringBuffer builder = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(0xff & b[i]);
            if (hex.length() == 1) builder.append('0');
            builder.append(hex);
        }
        return builder.toString();
    }

    private static void closeInputStream(InputStream fileInputStream) {
        try {
            if (fileInputStream != null){fileInputStream.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}