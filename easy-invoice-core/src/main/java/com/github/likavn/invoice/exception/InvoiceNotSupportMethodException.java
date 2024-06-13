package com.github.likavn.invoice.exception;

/**
 * 发票不支持的方法异常
 *
 * @author likavn
 * @date 2023/12/26
 **/
public class InvoiceNotSupportMethodException extends RuntimeException {
    public InvoiceNotSupportMethodException() {
        this("Not Support Method Exception");
    }

    public InvoiceNotSupportMethodException(String message) {
        super(message);
    }
}
