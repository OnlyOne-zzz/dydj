package com.bestfeng.dydj.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class NoteOrder implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公众号id")
    private Integer uniacid;

    private Integer uid;

    private String name;

    private String tel;

    private String msgtime;

    private String orderid;

    @ApiModelProperty(value = "总价格")
    private BigDecimal money;

    private Integer paytime;

    @ApiModelProperty(value = "创建时间")
    private Integer createtime;

    private Integer paid;

    private Integer status;

    private String address;

    @ApiModelProperty(value = "详细地址")
    private String daddress;

    private String content;

    @ApiModelProperty(value = "goods-Id")
    private Integer pid;

    @ApiModelProperty(value = "卡券id")
    private Integer couponid;

    @ApiModelProperty(value = "技师id")
    private Integer shopid;

    @ApiModelProperty(value = "项目id")
    private Integer currentid;

    @ApiModelProperty(value = "交通方式0:出租 1:公交/地铁")
    private Integer trafficType;

    @ApiModelProperty(value = "估算公里数")
    private BigDecimal trafficReckonMile;

    @ApiModelProperty(value = "交通费用")
    private BigDecimal trafficMoney;

    private BigDecimal contentMoney;

    @ApiModelProperty(value = "服务项目名字")
    private String contentName;

    @ApiModelProperty(value = "项目图(冗余)")
    private String contentThumb;

    @ApiModelProperty(value = "技师图片(冗余)")
    private String noteAvatarUrl;

    @ApiModelProperty(value = "技师名字(冗余)")
    private String noteName;

    private Integer cityid;

    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

    @ApiModelProperty(value = "备注")
    private String remake;

    private Integer addressId;

    @ApiModelProperty(value = "更新时间")
    private Integer updatetime;

    @ApiModelProperty(value = "0现金支付1:余额支付")
    private Integer payType;

    @ApiModelProperty(value = "实际的总价格")
    private BigDecimal totalMoney;
}