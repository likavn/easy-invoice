package com.github.likavn.invoice.domain;

import lombok.Builder;
import lombok.Data;

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
public class InvoiceCfg implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * token 永久token
     */
    private String token;
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
    private String sellerAddr;

    /**
     * 销售方开户行及账号
     */
    private String sellerBank;

    /**
     * 分机号
     */
    private String extensionNumber = "";

    /**
     * 机器编号
     */
    private String machineCode = "";

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

}
