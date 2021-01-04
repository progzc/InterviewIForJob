package org.fenixsoft.jvm;

import lombok.Getter;

/**
 * @Description 使用枚举类简化代码
 * @Author zhaochao
 * @Date 2021/1/4 14:22
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public enum CountryEnum {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");
    @Getter
    private Integer retCode;
    @Getter
    private String retMsg;

    private CountryEnum(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public static CountryEnum list(int idx) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if (idx == countryEnum.getRetCode())
                return countryEnum;
        }
        return null;
    }
}
