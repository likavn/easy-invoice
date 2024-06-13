package com.github.likavn.invoice.domain;

import com.github.likavn.invoice.enums.InvoiceStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 发票查询结果
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Data
@Builder
public class InvoiceResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    private Long id;

    /**
     * 开票状态：1开票中、2开票成功、3开票失败、4发票作废等
     */
    private InvoiceStatus status;

    /**
     * 失败原因
     */
    private String failCause;

    /**
     * pdfUrl
     */
    private String pdfUrl;

    /**
     * pictureUrl
     */
    private String pictureUrl;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 时间
     */
    private LocalDateTime time;
}
