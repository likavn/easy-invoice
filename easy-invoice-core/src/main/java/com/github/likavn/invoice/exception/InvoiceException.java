package com.github.likavn.invoice.exception;

/**
 * 发票异常
 *
 * @author likavn
 * @date 2023/12/26
 **/
public class InvoiceException extends RuntimeException {
    public InvoiceException(String message) {
        super(message);
    }
}
