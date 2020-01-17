package com.yellowrq.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:MD5Util
 * Package:com.yellowrq.utils
 * Description:
 *  对字符串MD5加密
 * @author:yellowrq
 * @date: 2020/1/16 10:16
 */
public class MD5Util {

    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }

    public static void main(String[] args) {
        try {
            String md5 = getMD5Str("yellowrq");
            System.out.println(md5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
