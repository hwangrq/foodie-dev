package com.yellowrq.enums;

/**
 * ClassName:Sex
 * Package:com.yellowrq.enums
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/1/16 11:16
 */
public enum Sex {

    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final Integer type;
    public final String value;

    Sex(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
