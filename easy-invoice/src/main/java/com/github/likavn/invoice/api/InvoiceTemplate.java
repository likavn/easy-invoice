package com.github.likavn.invoice.api;


import com.github.likavn.invoice.cache.Cache;
import com.github.likavn.invoice.domain.InvoiceCfg;
import com.github.likavn.invoice.domain.InvoiceData;
import com.github.likavn.invoice.domain.InvoiceResult;
import com.github.likavn.invoice.domain.InvoiceTitle;
import com.github.likavn.invoice.exception.InvoiceException;
import com.github.likavn.invoice.util.Func;

import java.io.ByteArrayInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发票接口
 *
 * @author : zxr
 * @date : 2022/9/29
 */
public abstract class InvoiceTemplate<T extends InvoiceCfg> {
    private final ThreadLocal<T> config = new ThreadLocal<>();
    private final Map<InvoiceCfg, T> cacheMap = new ConcurrentHashMap<>(4);
    protected Cache cache = null;

    /**
     * 初始化
     *
     * @param config 配置信息
     * @param cache  缓存对象
     */
    @SuppressWarnings("all")
    public void load(InvoiceCfg source, Cache cache) {
        T localConfig = cacheMap.computeIfAbsent(source, configKey -> {
            try {
                Type clazz = this.getClass().getGenericSuperclass();
                ParameterizedType pt = (ParameterizedType) clazz;
                Type[] types = pt.getActualTypeArguments();
                Class<? extends InvoiceCfg> type = (Class<? extends InvoiceCfg>) types[0];
                InvoiceCfg invoiceCfg = type.newInstance();
                invoiceCfg.copy(source);
                Properties properties = new Properties();
                String propStr = source.getExtendProperties();
                if (!Func.isEmpty(propStr)) {
                    properties.load(new ByteArrayInputStream(propStr.getBytes(StandardCharsets.UTF_8)));
                    properties.forEach((fieldName, val) -> Func.setFieldVal(invoiceCfg, fieldName.toString(), val));
                }
                return (T) invoiceCfg;
            } catch (Exception e) {
                throw new InvoiceException(e.getMessage());
            }
        });
        this.config.set(localConfig);
        if (null != cache) {
            this.cache = cache;
        }
    }

    /**
     * 获取配置
     *
     * @return config
     */
    protected T getConfig() {
        return config.get();
    }

    /**
     * 清除配置
     */
    public void clearConfig() {
        config.remove();
    }

    /**
     * 获取工厂编码
     *
     * @return 工厂编码
     */
    public abstract String getFactoryCode();

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
     * @param title  发票抬头
     */
    public abstract void invoice(InvoiceData record, InvoiceTitle title);

    /**
     * 重开发票
     *
     * @param record 开票记录
     * @param title  发票抬头
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
     * 余票查询
     *
     * @return 余票数量
     */
    public abstract Integer queryBillLeftCount();
}
