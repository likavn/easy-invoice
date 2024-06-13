package com.github.likavn.invoice.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发票配置信息
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceCfg extends InvoiceCopy implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 发票厂家名称
     */
    private String name;
    /**
     * 发票厂家编号
     */
    private String factoryCode;

    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求回调地址
     */
    private String callBackUrl;

    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 销售方企业税号
     */
    private String sellerTaxNum;

    /**
     * 销售方电话
     */
    private String sellerTel;

    /**
     * 销售方地址: 杭州文一路888号
     */
    private String sellerAddress;

    /**
     * 销售方开户行
     */
    private String sellerBank;

    /**
     * 销售方开户行账号
     */
    private String sellerBankAccount;

    /**
     * 开票人
     */
    private String drawer;

    /**
     * 复核人
     */
    private String checker;

    /**
     * 收款人
     */
    private String payee;

    /**
     * 发票税率
     */
    private BigDecimal taxRate;

    /**
     * 拓展字段properties
     */
    private String extendProperties;
}
