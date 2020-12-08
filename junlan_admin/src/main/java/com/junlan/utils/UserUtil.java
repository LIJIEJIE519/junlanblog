package com.junlan.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Author LJ
 * @Date 2020/11/4
 * msg
 */

public class UserUtil {
    /**
     *
     * @param strNum 随机字符串数
     * @return  随机字符串
     */
    public static String RandomString(int strNum) {
        return RandomStringUtils.randomAlphanumeric(strNum);
    }

    /**
     * 加密规则，16随机字符串数
     * MD5加密
     * @param pass   加密字符串--密码
     * @param salt  密钥
     * @return
     */
    public static String encrypt(String pass, String salt) {
        return DigestUtils.md5Hex("str={" + pass + "}, salt={" + salt + "}");
    }
}
