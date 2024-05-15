package com.github.likavn.invoice.hangxing51;

import com.github.likavn.invoice.domain.InvoiceCfg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 51航信 发票配置信息
 *
 * @author likavn
 * @date 2023/12/26
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class Invoice51Cfg extends InvoiceCfg {
    /**
     * 命名空间
     */
    private String nameSpace;
    /**
     * 分机号
     */
    private Integer extensionNumber;

    /**
     * 机器编号
     */
    private String machineCode = "";

    /**
     * 是否默认
     */
    private Boolean isDefault;
}
