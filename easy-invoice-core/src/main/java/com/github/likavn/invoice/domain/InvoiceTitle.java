package com.github.likavn.invoice.domain;

import com.github.likavn.invoice.enums.InvoiceType;
import com.github.likavn.invoice.enums.TitleType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 开票记录
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Data
@Builder
public class InvoiceTitle implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 开票类型
     */
    private TitleType titleType;

    /**
     * 开票类型
     */
    private InvoiceType type;
    /**
     * 购方名称
     */
    private String buyerName;

    /**
     * 购方税号
     */
    private String buyerTaxNum;

    /**
     * 购方银行
     */
    private String buyerBank;

    /**
     * 购方银行账户
     */
    private String buyerBankAccount;

    /**
     * 购方电话
     */
    private String buyerTel;

    /**
     * 购方地址
     */
    private String buyerAddress;

    /**
     * 通知邮箱
     */
    private String notifyEmail;

    /**
     * 通知手机
     */
    private String notifyPhone;
}
