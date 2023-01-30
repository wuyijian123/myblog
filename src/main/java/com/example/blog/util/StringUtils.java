package com.example.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


public class StringUtils {
    //电话号码正则表达式
    private static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|" +
            "(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|" +
            "18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

    //邮箱正则表达式
    private static final String REGEX_MAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    //校验电话号码
    public static boolean validPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return Pattern.matches(REGEX_MOBILE, phone);
    }
    //校验邮箱
    public static boolean validMail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            return false;
        }
        return Pattern.matches(REGEX_MAIL, mail);
    }

    //MD5加密加盐
    public static String md5Encode(String origin, String salt){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (!isEmpty(salt)){
                origin += salt;
            }
            digest.update(origin.getBytes());
            byte[] bytes = digest.digest();
            int i;
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                i = b;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    //空串判断
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    //右填充字符串补丁
    public static String padRight(String src, int len, char patch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }
        char[] chars = new char[len];
        System.arraycopy(src.toCharArray(), 0, chars, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            chars[i] = patch;
        }
        return new String(chars);
    }

    //左填充字符串补丁
    public static String padLeft(String src, int len, char patch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] chars = new char[len];
        System.arraycopy(src.toCharArray(), 0, chars, diff, src.length());
        for (int i = 0; i < diff; i++) {
            chars[i] = patch;
        }
        return new String(chars);
    }
}
