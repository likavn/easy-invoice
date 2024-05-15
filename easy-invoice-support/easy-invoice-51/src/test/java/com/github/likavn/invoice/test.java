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
package com.github.likavn.invoice;

import com.github.likavn.invoice.api.InvoiceTemplate;
import com.github.likavn.invoice.domain.InvoiceCfg;
import org.junit.Test;

/**
 * @author likavn
 * @date 2024/4/8
 **/
public class test {

    @Test
    public void test() {
        System.out.println("test");
        InvoiceCfg invoiceCfg = new InvoiceCfg();
        invoiceCfg.setFactoryCode("51");
        invoiceCfg.setName("航信51发票");
        String extendProperties = "" +
                "# 命名空间\n" +
                "nameSpace=https://www.baidu.com/\n" +
                "# 分机号\n" +
                "extensionNumber=12315\n" +
                "# 机器号码\n" +
                "machineCode=98822\n" +
                "# 是否默认\n" +
                "isDefault=true\n" +
                "test=1\n";
        invoiceCfg.setExtendProperties(extendProperties);
        InvoiceTemplate invoiceTemplate = InvoiceFactory.create(invoiceCfg);
        invoiceTemplate.cancelInvoice(null);
        System.out.println("test1");
    }
}
