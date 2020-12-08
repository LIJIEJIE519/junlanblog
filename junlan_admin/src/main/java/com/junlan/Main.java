package com.junlan;

import com.junlan.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author LJ
 * @Date 2020/11/30
 * msg
 */


public class Main {
    public static void main(String[] args) {
        System.out.println(UserUtil.RandomString(16));
        System.out.println(UserUtil.encrypt("111111", "2lHYY7g48KiWEMeY"));
    }
}
