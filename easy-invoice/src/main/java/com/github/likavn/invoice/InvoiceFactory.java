package com.github.likavn.invoice;

import com.github.likavn.invoice.api.InvoiceTemplate;
import com.github.likavn.invoice.cache.Cache;
import com.github.likavn.invoice.cache.DefaultCache;
import com.github.likavn.invoice.domain.InvoiceCfg;
import com.github.likavn.invoice.enums.InvoiceFactoryCode;
import com.github.likavn.invoice.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发票工厂
 *
 * @author : zxr
 * @date : 2022/9/29
 */
public class InvoiceFactory {
    /**
     * 工厂代码和实现映射
     */
    private static final Map<InvoiceFactoryCode, InvoiceTemplate> TEMPLATE_MAP = new ConcurrentHashMap<>();
    private static final Cache CACHE = DefaultCache.getInstance();

    /**
     * 获取发票实现
     *
     * @param config 配置
     * @return service
     */
    @SuppressWarnings("all")
    public static InvoiceTemplate create(InvoiceCfg config) {
        InvoiceFactoryCode factoryCode = InvoiceFactoryCode.of(config.getFactoryCode());
        Assert.notNull(factoryCode, "工厂代码不存在");

        InvoiceTemplate template = TEMPLATE_MAP.computeIfAbsent(factoryCode, InvoiceFactoryCode::getTemplate);
        template.init(config, getCache());
        return template;
    }

    /**
     * 获取缓存
     *
     * @return cache
     */
    public static Cache getCache() {
        return CACHE;
    }
}
