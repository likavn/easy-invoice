package com.github.likavn.invoice.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 开票记录
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Data
@Builder
public class InvoiceData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    private String id;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNum;

    /**
     * 发票备注信息
     */
    private String remark;

    /**
     * 所有订单总金额
     */
    private String orderTotalMoney;

    /**
     * 所有订单扣除总金额
     */
    private String orderDeductionTotalMoney;

    /**
     * 所有订单折扣总金额
     */
    private String orderZkeTotalMoney;

    /**
     * 开票时间
     */
    private Date invoiceDate;

    /**
     * 发票订单
     */
    private List<Order> orders;

    /**
     * 开票记录订单数据
     *
     * @author : zxr
     * @date : 2022/9/29
     */
    @Data
    @Builder
    public static class Order implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 商品编码（如不传 入，则自动匹配， 需签订免责协议）
         */
        private String goodsCode;
        /**
         * 商品名称
         */
        private String goodsName;

        /**
         * 规格型号
         */
        private String specType;

        /**
         * 单位
         */
        private String unit;

        /**
         * 数量（单价、数量必须都填写或都不填）
         */
        private String num;

        /**
         * 单价（单价、数量必须都填写或都不填）
         */
        private String price;

        /**
         * 发票费率
         */
        private String taxRate;

        /**
         * 折扣额(大于0表示该行有折扣，精确到小数点后两位)
         */
        private String zke;

        /**
         * 扣除额(若大于0表示开具差额票，仅支持一条明细；精确到小数点后两位)
         */
        private String deduction;

        /**
         * 含税金额（精确到小数点后两位，单价、数量 与 含税金额不能同时不填）
         */
        private String taxIncludedAmount;
    }
}

