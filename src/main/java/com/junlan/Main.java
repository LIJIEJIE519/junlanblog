package com.junlan;

import com.fasterxml.jackson.annotation.JsonFormat;
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

@Data
@Accessors(chain = true)        // lombok支持链式构造
@Builder(toBuilder = true)
@AllArgsConstructor
public class Main {

    private String string;
    private boolean bool;
    private int i;

    public Main() {
    }
    public static void main(String[] args) {
        final Main hhh = new Main().setBool(true).setI(123).setString("hhh");
        System.out.println(hhh);
    }

    @Override
    public String toString() {
        return "Main{" +
                "string='" + string + '\'' +
                ", bool=" + bool +
                ", i=" + i +
                '}';
    }
}
