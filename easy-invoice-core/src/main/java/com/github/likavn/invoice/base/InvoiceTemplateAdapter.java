/**
 * Copyright 2023-2033, likavn (likavn@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.likavn.invoice.base;

import com.github.likavn.invoice.api.InvoiceTemplate;
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
 * 抽象类 InvoiceTemplateAdapter 为发票模板适配器提供了一个通用的实现框架。
 * 它要求所有子类提供特定于发票操作的实现，例如二维码发票、普通发票、重新发票和取消发票等。
 * 这个类也管理发票配置和缓存。
 *
 * @param <T> 代表发票配置的类型，该类型必须是 InvoiceCfg 的子类型。
 * @author : zxr
 * @date : 2022/9/29
 */
public abstract class InvoiceTemplateAdapter<T extends InvoiceCfg> implements InvoiceTemplate {
    // 线程本地存储，用于存储当前线程的发票配置
    private final ThreadLocal<T> config = new ThreadLocal<>();
    // 使用 ConcurrentHashMap 来支持并发的发票配置访问
    private final Map<InvoiceCfg, T> cacheMap = new ConcurrentHashMap<>(4);
    // 缓存对象，用于缓存发票配置信息
    protected Cache cache = null;

    /**
     * 获取当前线程的发票配置。
     *
     * @return 返回当前线程的发票配置。
     */
    protected T getConfig() {
        return config.get();
    }

    /**
     * 清除当前线程的发票配置。
     */
    protected void clearConfig() {
        config.remove();
    }

    /**
     * 初始化函数，用于加载配置信息并初始化缓存对象。
     *
     * @param source 配置信息的来源，包含了发票配置的详细信息。
     * @param cache  缓存对象，用于缓存发票配置信息，可以为 null。
     *               如果不为 null，则会更新当前对象的缓存为传入的缓存对象。
     */
    @SuppressWarnings("all")
    public void load(InvoiceCfg source, Cache cache) {
        // 计算并获取对应的配置信息，如果不存在则进行创建和初始化
        T localConfig = cacheMap.computeIfAbsent(source, key -> {
            try {
                // 解析出 InvoiceCfg 的具体实现类类型，并创建其实例
                Type clazz = this.getClass().getGenericSuperclass();
                ParameterizedType pt = (ParameterizedType) clazz;
                Type[] types = pt.getActualTypeArguments();
                Class<? extends InvoiceCfg> type = (Class<? extends InvoiceCfg>) types[0];

                // 复制 source 的数据到新创建的配置实例中
                InvoiceCfg invoiceCfg = type.newInstance();
                invoiceCfg.copy(source);

                // 加载并处理扩展属性，将其设置到 invoiceCfg 中
                Properties properties = new Properties();
                String propStr = source.getExtendProperties();
                if (!Func.isEmpty(propStr)) {
                    properties.load(new ByteArrayInputStream(propStr.getBytes(StandardCharsets.UTF_8)));
                    properties.forEach((fieldName, val) -> Func.setFieldVal(invoiceCfg, fieldName.toString(), val));
                }

                // 返回初始化好的配置实例
                return (T) invoiceCfg;
            } catch (Exception e) {
                // 在初始化过程中遇到异常，则抛出发票异常
                throw new InvoiceException(e.getMessage());
            }
        });

        // 更新当前线程的发票配置
        this.config.set(localConfig);

        // 如果传入了缓存对象，则更新当前对象的缓存为传入的缓存对象
        if (null != cache) {
            this.cache = cache;
        }
    }

    /**
     * 获取工厂编码。
     * 这是一个抽象方法，需要在子类中具体实现。
     *
     * @return 返回工厂编码。
     */
    public abstract String getFactoryCode();

    /**
     * 处理二维码发票逻辑，完成后清除当前配置。
     *
     * @param data 发票数据
     * @return 返回发票结果
     */
    @Override
    public InvoiceResult qrCodeInvoice(InvoiceData data) {
        InvoiceResult result = qrCodeInvoiceImpl(data);
        clearConfig();
        return result;
    }

    /**
     * 二维码发票的具体实现。
     * 这是一个抽象方法，需要在子类中具体实现。
     *
     * @param data 发票数据
     * @return 返回发票结果
     */
    public abstract InvoiceResult qrCodeInvoiceImpl(InvoiceData data);

    /**
     * 处理普通发票逻辑，完成后清除当前配置。
     *
     * @param data  发票数据
     * @param title 发票标题
     * @return 返回发票结果
     */
    @Override
    public InvoiceResult invoice(InvoiceData data, InvoiceTitle title) {
        InvoiceResult result = invoiceImpl(data, title);
        clearConfig();
        return result;
    }

    /**
     * 普通发票的具体实现。
     * 这是一个抽象方法，需要在子类中具体实现。
     *
     * @param data  发票数据
     * @param title 发票标题
     * @return 返回发票结果
     */
    public abstract InvoiceResult invoiceImpl(InvoiceData data, InvoiceTitle title);

    /**
     * 处理重新发票逻辑，完成后清除当前配置。
     *
     * @param data  发票数据
     * @param title 发票标题
     * @return 返回发票结果
     */
    @Override
    public InvoiceResult reInvoice(InvoiceData data, InvoiceTitle title) {
        InvoiceResult result = reInvoiceImpl(data, title);
        clearConfig();
        return result;
    }

    /**
     * 重新发票的具体实现。
     * 这是一个抽象方法，需要在子类中具体实现。
     *
     * @param data  发票数据
     * @param title 发票标题
     * @return 返回发票结果
     */
    public abstract InvoiceResult reInvoiceImpl(InvoiceData data, InvoiceTitle title);

    /**
     * 处理取消发票逻辑，完成后清除当前配置。
     *
     * @param data 发票数据
     * @return 返回发票结果
     */
    @Override
    public InvoiceResult cancelInvoice(InvoiceData data) {
        InvoiceResult result = cancelInvoiceImpl(data);
        clearConfig();
        return result;
    }

    /**
     * 取消发票的具体实现。
     * 这是一个抽象方法，需要在子类中具体实现。
     *
     * @param data 发票数据
     * @return 返回发票结果
     */
    public abstract InvoiceResult cancelInvoiceImpl(InvoiceData data);

    /**
     * 处理查询发票结果逻辑，完成后清除当前配置。
     *
     * @param records 发票数据记录列表
     * @return 返回发票结果列表
     */
    @Override
    public List<InvoiceResult> queryInvoiceResult(List<InvoiceData> records) {
        List<InvoiceResult> invoiceResults = queryInvoiceResultImpl(records);
        clearConfig();
        return invoiceResults;
    }

    /**
     * 查询发票结果的具体实现。
     * 这是一个抽象方法，需要在子类中具体实现。
     *
     * @param records 发票数据记录列表
     * @return 返回发票结果列表
     */
    public abstract List<InvoiceResult> queryInvoiceResultImpl(List<InvoiceData> records);
}

