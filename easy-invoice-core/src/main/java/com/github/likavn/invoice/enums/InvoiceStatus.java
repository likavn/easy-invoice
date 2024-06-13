package com.github.likavn.invoice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开票状态：1开票中、2开票成功、3开票失败、4发票作废等
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
     * 发票已作废
     */
    CANCEL(4),

    /**
     * 发票作废中
     */
    CANCEL_ING(5),

    /**
     * 发票作废失败
     */
    CANCEL_FAIL(6),

    /**
     * 发票已红冲
     */
    RED(7),

    /**
     * 发票红冲中
     */
    RED_ING(8),

    /**
     * 发票红冲失败
     */
    RED_FAIL(9),
    ;
    private final Integer value;

    /**
     * 判断是否成功
     *
     * @param value 状态
     * @return 是否成功
     */
    public static boolean isSuccess(Integer value) {
        return InvoiceStatus.SUCCESS.value.equals(value);
    }
}