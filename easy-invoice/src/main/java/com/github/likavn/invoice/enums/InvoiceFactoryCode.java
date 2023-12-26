package com.github.likavn.invoice.enums;

import com.github.likavn.invoice.api.InvoiceTemplate;
import com.github.likavn.invoice.nuonuo.NuoNuoInvoiceTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发票编号
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Getter
@AllArgsConstructor
public enum InvoiceFactoryCode {
    /**
     * 诺诺发票
     */
    NUONUO("nuonuo", NuoNuoInvoiceTemplate.getInstance()),
    ;
    private String code;
    private InvoiceTemplate template;

    public String getValue() {
        return this.name().toLowerCase();
    }

    public static InvoiceFactoryCode of(String code) {
        for (InvoiceFactoryCode invoiceFactoryCode : InvoiceFactoryCode.values()) {
            if (invoiceFactoryCode.code.equals(code)) {
                return invoiceFactoryCode;
            }
        }
        return null;
    }
}
