package com.github.likavn.invoice.hangxing51;

import com.github.likavn.invoice.base.InvoiceTemplateAdapter;
import com.github.likavn.invoice.domain.InvoiceData;
import com.github.likavn.invoice.domain.InvoiceResult;
import com.github.likavn.invoice.domain.InvoiceTitle;
import com.github.likavn.invoice.exception.InvoiceNotSupportMethodException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 航信51发票模板
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Slf4j
public class Invoice51Template extends InvoiceTemplateAdapter<Invoice51Cfg> {

    @Override
    public String getFactoryCode() {
        return "51";
    }

    @Override
    public String qrCodeInvoiceImpl(InvoiceData data) {
        throw new InvoiceNotSupportMethodException();
    }

    @Override
    public void invoiceImpl(InvoiceData data, InvoiceTitle title) {
        throw new InvoiceNotSupportMethodException();
    }

    @Override
    public void reInvoiceImpl(InvoiceData data, InvoiceTitle title) {
        throw new InvoiceNotSupportMethodException();
    }

    @Override
    public void cancelInvoiceImpl(InvoiceData data) {
        throw new InvoiceNotSupportMethodException();
    }

    @Override
    public List<InvoiceResult> queryInvoiceResultImpl(List<InvoiceData> records) {
        throw new InvoiceNotSupportMethodException();
    }

}
