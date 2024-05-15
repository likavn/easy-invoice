package com.github.likavn.invoice;

import com.github.likavn.invoice.api.InvoiceTemplate;
import com.github.likavn.invoice.cache.Cache;
import com.github.likavn.invoice.cache.DefaultCache;
import com.github.likavn.invoice.domain.InvoiceCfg;
import com.github.likavn.invoice.util.Assert;

import java.util.Map;
import java.util.ServiceLoader;
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
    @SuppressWarnings("all")
    private static final Map<String, InvoiceTemplate> TEMPLATE_MAP = new ConcurrentHashMap<>();

    /**
     * 缓存
     */
    private static final Cache CACHE = DefaultCache.getInstance();

    // 初始化
    static {
        // 发票服务加载
        @SuppressWarnings("all")
        ServiceLoader<InvoiceTemplate> serviceLoader = ServiceLoader.load(InvoiceTemplate.class);
        serviceLoader.forEach(template -> TEMPLATE_MAP.put(template.getFactoryCode(), (InvoiceTemplate<? extends InvoiceCfg>) template));
    }

    /**
     * 获取本地缓存
     *
     * @return cache
     */
    public static Cache getLocalCache() {
        return CACHE;
    }

    /**
     * 获取发票实现
     *
     * @param config 配置
     * @return 发票服务
     */
    @SuppressWarnings("all")
    public static InvoiceTemplate create(InvoiceCfg config) {
        return create(config, null);
    }

    /**
     * 获取发票实现
     *
     * @param config 配置
     * @param cache  缓存
     * @return 发票服务
     */
    @SuppressWarnings("all")
    public static InvoiceTemplate create(InvoiceCfg config, Cache cache) {
        InvoiceTemplate template = TEMPLATE_MAP.get(config.getFactoryCode());
        Assert.notNull(template, "工厂代码对应服务不存在，code" + config.getFactoryCode());

        template.load(config, null != cache ? cache : getLocalCache());
        return template;
    }
}
