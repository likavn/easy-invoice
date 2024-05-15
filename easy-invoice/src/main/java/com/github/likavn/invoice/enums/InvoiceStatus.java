package com.github.likavn.invoice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开票状态：1开票中、2开票成功、3开票失败、4发票作废
 *
 * @author : zxr
 * @date : 2022/9/28
 */
@Getter
@AllArgsConstructor
public enum InvoiceStatus {

    /**
     * 开票中
     */
    ING(1),

    /**
     * 成功
     */
    SUCCESS(2),

    /**
     * 失败
     */
    FAIL(3),

    /**
     * 发票作废
     */
    CANCEL(4),
    ;
    private final Integer value;

    /**
     * 判断是否成功
     *
     * @param value
     * @return
     */
    public static boolean isSuccess(Integer value) {
        return InvoiceStatus.SUCCESS.value.equals(value);
    }
}