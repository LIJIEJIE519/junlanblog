package com.junlan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.junlan.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

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
