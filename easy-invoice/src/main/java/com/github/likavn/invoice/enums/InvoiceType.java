package com.github.likavn.invoice.enums;

/**
 * 发票类型
 *
 * @author likavn
 * @date 2023/12/26
 **/
public enum InvoiceType {
    // 增值税普通发票
    NORMAL_INVOICE("normal_invoice", "增值税普通发票"),
    // 增值税专用发票
    SPECIAL_INVOICE("special_invoice", "增值税专用发票"),
    ;
    private final String code;
    private final String name;

    InvoiceType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
