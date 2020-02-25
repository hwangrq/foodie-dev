package com.yellowrq.enums;

import io.swagger.models.auth.In;

/**
 * ClassName:YesOrNo
 * Package:com.yellowrq.enums
 * Description:
 *  是否 枚举
 * @author:yellowrq
 * @date: 2020/2/25 10:43
 */
public enum  YesOrNo {
    NO(0,"否"),
    Yes(1,"是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
