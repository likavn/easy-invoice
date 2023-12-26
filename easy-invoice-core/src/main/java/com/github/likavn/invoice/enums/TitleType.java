package com.github.likavn.invoice.enums;

/**
 * 抬头类型
 *
 * @author likavn
 * @date 2023/12/26
 **/
public enum TitleType {
    //个人
    PERSONAL("个人"),
    //企业
    ENTERPRISE("企业");

    private final String value;

    TitleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TitleType getTitleType(String value) {
        for (TitleType titleType : TitleType.values()) {
            if (titleType.getValue().equals(value)) {
                return titleType;
            }
        }
        return null;
    }
}
