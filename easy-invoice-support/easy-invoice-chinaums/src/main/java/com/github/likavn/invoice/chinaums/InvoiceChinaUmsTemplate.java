package com.github.likavn.invoice.chinaums;

import com.github.likavn.invoice.api.InvoiceTemplate;
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
public class InvoiceChinaUmsTemplate extends InvoiceTemplate<InvoiceChinaUmsCfg> {

    @Override
    public String getFactoryCode() {
        return "chinaums";
    }

    @Override
    public String qrCodeInvoice(InvoiceData record) {
        throw new InvoiceNotSupportMethodException();
    }

    @Override
    public void invoice(InvoiceData record, InvoiceTitle title) {
        InvoiceChinaUmsCfg config = getConfig();
    }

    @Override
    public void reInvoice(InvoiceData record, InvoiceTitle title) {
        System.out.println("reInvoice");
    }

    @Override
    public void cancelInvoice(InvoiceData record) {
        InvoiceChinaUmsCfg config = getConfig();

        System.out.println("cancelInvoice");
    }

    @Override
    public List<InvoiceResult> queryInvoiceResult(List<InvoiceData> records) {
        throw new InvoiceNotSupportMethodException();
    }

    @Override
    public Integer queryBillLeftCount() {
        throw new InvoiceNotSupportMethodException();
    }
}
