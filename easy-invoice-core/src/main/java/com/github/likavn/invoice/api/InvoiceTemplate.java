package com.github.likavn.invoice.api;


import com.github.likavn.invoice.cache.Cache;
import com.github.likavn.invoice.domain.InvoiceCfg;
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
public abstract class InvoiceTemplate {
    private final ThreadLocal<InvoiceCfg> config = new ThreadLocal<>();
    protected Cache cache = null;

    /**
     * 初始化
     *
     * @param config config
     * @param cache  cache
     */
    public void init(InvoiceCfg config, Cache cache) {
        this.config.set(config);
        if (null != cache) {
            this.cache = cache;
        }
    }

    /**
     * 获取配置
     *
     * @return config
     */
    protected InvoiceCfg getConfig() {
        return config.get();
    }

    /**
     * 清除配置
     */
    public void clearConfig() {
        config.remove();
    }

    /**
     * 二维码扫码开发票
     *
     * @param record record
     * @return 二维码地址
     */
    public abstract String qrCodeInvoice(InvoiceData record);

    /**
     * 开发票
     *
     * @param record 开票记录
     */
    public abstract void invoice(InvoiceData record, InvoiceTitle title);

    /**
     * 重开发票
     *
     * @param record 开票记录
     */
    public abstract void reInvoice(InvoiceData record, InvoiceTitle title);

    /**
     * 发票作废
     *
     * @param record 开票记录
     */
    public abstract void cancelInvoice(InvoiceData record);

    /**
     * 开发票结果查询
     *
     * @param records 开票记录
     * @return 开发票结果
     */
    public abstract List<InvoiceResult> queryInvoiceResult(List<InvoiceData> records);

    /**
     * 发票投递
     *
     * @param record 开票记录
     */
    public abstract void deliveryInvoice(InvoiceData record, InvoiceTitle title);
}
