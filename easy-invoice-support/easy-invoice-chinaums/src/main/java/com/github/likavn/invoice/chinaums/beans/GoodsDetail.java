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
package com.github.likavn.invoice.chinaums.beans;

/**
 * 商品明细
 *
 * @author likavn
 * @date 2024/5/9
 **/
public class GoodsDetail {

    /**
     * 行号，从 1 开始
     * <p>
     * 必填
     */
    private Integer index;

    /**
     * 发票行性质
     * 0: 正常行
     * 1: 折扣行
     * 2: 被折扣行
     * <p>
     * 必填
     */
    private String attribute;

    /**
     * 折行对应行号
     * 有折扣时必填，为另一行的行号index，形成交叉对应
     */
    private Integer discountIndex;

    /**
     * 商品名称：String(1,64)
     * 折扣行与被折扣行一致
     * 注：商品名称可以自定义，但不要
     * 超出该商品分类范围，票面上会自
     * 动附上商品分类
     * <p>
     * 必填
     */
    private String name;

    /**
     * 商品编码:String(19)
     * 参考国税总局出具的《税收分类编
     * 码》.xls
     * 注：必须使用文件中 19 位的税收分类编码，对接系统的商品库中如果没有该编码，需要自行进行映射维护
     * <p>
     * 必填
     */
    private String sn;

    /**
     * 税率,百分数
     * <p>
     * 必填
     */
    private Integer taxRate;

    /**
     * 含税总金额
     * 备注：单位为元折扣行为负数
     * <p>
     * 必填
     */
    private Double priceIncludingTax;

    /**
     * 数量
     * 备注：
     * 1.若不传单价，则数量默认为 1；
     * 2.若传单价，则后台根据单价计算数量；
     */
    private Integer quantity;

    /**
     * 含税单价
     */
    private Integer unitPriceIncludingTax;

    /**
     * 单位：String(1,16)
     */
    private String unit;

    /**
     * 规格型号：String(1,64)
     */
    private String model;

    /**
     * 免税类型
     * 备注：
     * 空：正常非零税率
     * 0：出口退税
     * 1：免税
     * 2：不征税
     * 3：普通零税率
     */
    private String freeTaxType;

    /**
     * 是否使用优惠政策
     * 备注：
     * 0：否
     * 1：是
     */
    private String preferPolicyFlag;

    /**
     * 增值税特殊管理：String(1,64)
     */
    private String vatSpecial;
}
