package com.github.likavn.invoice.api;


import com.github.likavn.invoice.domain.InvoiceData;
import com.github.likavn.invoice.domain.InvoiceResult;
import com.github.likavn.invoice.domain.InvoiceTitle;

import java.util.List;

/**
 * 发票接口
 *
 * @author : zxr
 * @date : 2022/9/29
 */
public interface InvoiceTemplate {
    /**
     * 二维码扫码开发票
     *
     * @param data data
     * @return 二维码地址
     */
    InvoiceResult qrCodeInvoice(InvoiceData data);

    /**
     * 开发票
     *
     * @param data  开票记录
     * @param title 发票抬头
     */
    InvoiceResult invoice(InvoiceData data, InvoiceTitle title);

    /**
     * 重开发票
     *
     * @param data  开票记录
     * @param title 发票抬头
     */
    InvoiceResult reInvoice(InvoiceData data, InvoiceTitle title);

    /**
     * 发票作废
     *
     * @param data 开票记录
     */
    InvoiceResult cancelInvoice(InvoiceData data);

    /**
     * 开发票结果查询
     *
     * @param records 开票记录
     * @return 开发票结果
     */
    List<InvoiceResult> queryInvoiceResult(List<InvoiceData> records);
}
