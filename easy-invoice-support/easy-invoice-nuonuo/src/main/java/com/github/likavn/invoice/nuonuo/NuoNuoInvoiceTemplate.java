package com.github.likavn.invoice.nuonuo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.likavn.invoice.api.InvoiceTemplate;
import com.github.likavn.invoice.domain.InvoiceCfg;
import com.github.likavn.invoice.domain.InvoiceData;
import com.github.likavn.invoice.domain.InvoiceResult;
import com.github.likavn.invoice.domain.InvoiceTitle;
import com.github.likavn.invoice.enums.InvoiceStatus;
import com.github.likavn.invoice.exception.InvoiceException;
import com.github.likavn.invoice.util.Assert;
import com.github.likavn.invoice.util.Func;
import lombok.extern.slf4j.Slf4j;
import nuonuo.open.sdk.NNOpenSDK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 诺诺发票模板
 *
 * @author : zxr
 * @date : 2022/9/29
 */
@Slf4j
public class NuoNuoInvoiceTemplate extends InvoiceTemplate<InvoiceCfg> {
    /**
     * token缓存
     */
    private static final String CACHE_TOKEN = "ling:invoice:token:nuonuo:%s";
    /**
     * 测试环境
     */
    private static final String DEV_URL = "https://sandbox.nuonuocs.cn/open/v1/services";
    /**
     * 固定地址:SDK请求地址
     */
    private static final String URL = "https://sdk.nuonuo.com/open/v1/services";

    private static final NuoNuoInvoiceTemplate INSTANCE = new NuoNuoInvoiceTemplate();

    public static NuoNuoInvoiceTemplate getInstance() {
        return INSTANCE;
    }

    /**
     * 获取根域名
     */
    private String getApi() {
        return URL;
    }

    @Override
    public String qrCodeInvoice(InvoiceData record) {
        throw new InvoiceException("暂不支持该操作");
    }

    @Override
    public void invoice(InvoiceData record, InvoiceTitle title) {
        List<JSONObject> orderDetails = new ArrayList<>(record.getOrders().size());
        for (InvoiceData.Order order : record.getOrders()) {
            orderDetails.add(new JSONObject()
                    .fluentPut("goodsName", order.getGoodsName())
                    .fluentPut("goodsCode", order.getGoodsCode())
                    .fluentPut("specType", order.getSpecType())
                    .fluentPut("unit", order.getUnit())
                    .fluentPut("num", order.getNum())
                    .fluentPut("price", order.getPrice())
                    // 优惠政策标志(0:不使用、1:使用)
                    .fluentPut("favouredPolicyFlag", "0")
                    // 优惠政策名称(favouredPolicyFlag为1时必填)
                    // 零税率标志(空:非零税率、1:免税、2:不征税、3:普通零税率)
                    .fluentPut("zeroRateFlag", "3")
                    // 税率（只支持 0.xx x）
                    .fluentPut("taxRate", order.getTaxRate())
                    // 折扣额(大于0表示该行有折扣，精确到小数点后两位)
                    .fluentPut("zke", order.getZke())
                    // 扣除额(若大于0表示开具差额票，仅支持一条明细；精确到小数点后两位)
                    .fluentPut("deduction", order.getDeduction())
                    // 含税金额（精确到小数点后两位，单价、数量 与 含税金额不能同时不填）
                    .fluentPut("taxIncludedAmount", order.getTaxIncludedAmount()));
        }
        InvoiceCfg config = getConfig();

        String content = new JSONObject()
                // 发票ID、流水号
                .fluentPut("orderNo", record.getId())
                // 订单总金额（含税）
                .fluentPut("orderTotal", record.getOrderTotalMoney())
                // 销售方信息
                .fluentPut("salerTaxNum", config.getSellerTaxNum())
                .fluentPut("salerAddress", config.getSellerAddr())
                .fluentPut("salerTel", config.getSellerTel())
                .fluentPut("salerAccount", config.getSellerBank())

                // 发票信息
                .fluentPut("clerk", config.getDrawer())
                .fluentPut("payee", config.getPayee())
                .fluentPut("checker", config.getChecker())

                // 单据时间
                .fluentPut("invoiceDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getInvoiceDate()))

                // 买方信息
                .fluentPut("buyerName", title.getBuyerName())
                .fluentPut("buyerTaxNum", title.getBuyerTaxNum())
                .fluentPut("buyerAccount", title.getBuyerBankAccount())
                .fluentPut("buyerTel", title.getBuyerTel())
                .fluentPut("buyerAddress", title.getBuyerAddress())
                .fluentPut("notifyEmail", title.getNotifyEmail())
                .fluentPut("notifyPhone", title.getNotifyPhone())
                .fluentPut("buyerInfoEditAble", "1")
                .fluentPut("invoiceLine", "p")

                // 订单信息
                .fluentPut("detail", orderDetails)
                .toJSONString();
        toNuoNuoApi(config.getSellerTaxNum(), "nuonuo.ElectronInvoice.saveScanTemp", content);
    }

    @Override
    public void reInvoice(InvoiceData record, InvoiceTitle title) {


    }

    @Override
    public List<InvoiceResult> queryInvoiceResult(List<InvoiceData> records) {
        InvoiceCfg config = getConfig();
        String content = new JSONObject()
                // 最多查50个订单号
                .fluentPut("serialNos", records.stream().map(InvoiceData::getId).collect(Collectors.toList()))
                .fluentPut("isOfferInvoiceDetail", 1)
                .toJSONString();
        String result = toNuoNuoApi(config.getSellerTaxNum(), "nuonuo.ElectronInvoice.queryInvoiceResult", content);

        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        Assert.isTrue(!"E0000".equals(code), "查询失败");

        result = jsonObject.getString("result");

        return JSONArray.parseArray(result, JSONObject.class)
                .stream()
                .map(js -> {
                    // 获取开票结果
                    // 发票状态：
                    // 2 :开票完成（ 最终状 态），其他状态分别为:
                    // 20:开票中;
                    // 21:开票成功签章中;
                    // 22:开票失败;
                    // 24: 开票成功签章失败;
                    // 3:发票已作废
                    // 31: 发票作废中
                    // 备注：22、24状态时，无需再查询，请确认开票失败原因以及签章失败原因； 注：请以该状态码区分发票状态
                    Integer status = js.getInteger("status");

                    InvoiceStatus invoiceStatus = InvoiceStatus.ING;
                    // 成功
                    if (Integer.valueOf(2).equals(status)) {
                        invoiceStatus = InvoiceStatus.SUCCESS;
                    }
                    // 22、24状态时，无需再查询，请确认开票失败原因以及签章失败原因；
                    else if (Integer.valueOf(22).equals(status) || Integer.valueOf(24).equals(status)) {
                        invoiceStatus = InvoiceStatus.FAIL;
                    }
                    // 2发票已作废
                    else if (Integer.valueOf(3).equals(status) || Integer.valueOf(31).equals(status)) {
                        invoiceStatus = InvoiceStatus.CANCEL;
                    }

                    return InvoiceResult.builder()
                            .id(js.getLong("serialNo"))
                            .status(invoiceStatus)
                            .failCause(js.getString("failCause"))
                            .pdfUrl(js.getString("pdfUrl"))
                            .pictureUrl(js.getString("pictureUrl"))
                            .invoiceCode(js.getString("invoiceCode"))
                            .invoiceNo(js.getString("invoiceNo"))
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    public void deliveryInvoice(InvoiceData record, InvoiceTitle title) {
        InvoiceCfg config = getConfig();
        String content = new JSONObject()
                .fluentPut("mail", title.getNotifyEmail())
                .fluentPut("phone", title.getNotifyPhone())
                .fluentPut("taxnum", title.getBuyerTaxNum())
                .fluentPut("invoiceCode", record.getInvoiceCode())
                .fluentPut("invoiceNum", record.getInvoiceNum())
                .toJSONString();
        toNuoNuoApi(config.getSellerTaxNum(), "nuonuo.ElectronInvoice.deliveryInvoice", content);
    }

    @Override
    public void cancelInvoice(InvoiceData record) {
        InvoiceCfg config = getConfig();
        String content = new JSONObject()
                .fluentPut("invoiceId", record.getId())
                .fluentPut("invoiceCode", record.getInvoiceCode())
                .fluentPut("invoiceNo", record.getInvoiceNum())
                .toJSONString();
        log.info("发起发票作废，id={}", record.getId());
        toNuoNuoApi(config.getSellerTaxNum(), "nuonuo.electronInvoice.invoiceCancellation", content);
    }

    /**
     * 获取token
     *
     * @return tk
     */
    private String getToken() {
        InvoiceCfg config = getConfig();
        if (!Func.isEmpty(config.getToken())) {
            return config.getToken();
        }
        String cacheKey = String.format(CACHE_TOKEN, config.getAppId());
        Object token = cache.get(cacheKey);
        if (null != token) {
            return token.toString();
        }

        NNOpenSDK sdk = NNOpenSDK.getIntance();
        String result = sdk.getMerchantToken(config.getAppId(), config.getAppSecret(), "");
        JSONObject js = JSONObject.parseObject(result);
        String accessToken = js.getString("access_token");

        if (null == accessToken) {
            throw new InvoiceException(String.format("获取发票token异常，code:%s", js.getString("error")));
        }

        // 设置2小时token
        cache.set(cacheKey, accessToken, 1000 * 60 * 60 * 2);
        return accessToken;
    }

    /**
     * 诺诺接口调用
     *
     * @param taxNum  授权企业税号
     * @param method  调用方法
     * @param content 内容
     */
    private String toNuoNuoApi(String taxNum, String method, String content) {
        String senId = UUID.randomUUID().toString().replace("-", "");
        InvoiceCfg config = getConfig();
        NNOpenSDK sdk = NNOpenSDK.getIntance();
        return sdk.sendPostSyncRequest(getApi(), senId, config.getAppId(), config.getAppSecret(),
                getToken(),
                taxNum,
                method,
                content);
    }
}
