package com.bestfeng.dydj.vo;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.mbg.model.CouponOrder;
import com.bestfeng.dydj.utils.TimeUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class CouponOrderVo implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公众号id")
    private Integer uniacid;

    private Integer pid;

    private Integer uid;

    private String name;

    private String tel;

    private String orderid;

    private String money;

    private Integer paytime;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    private Byte paid;

    private Byte status;

    private String title;

    @ApiModelProperty(value = "1=注册")
    private Integer type;

    @ApiModelProperty(value = "有效期")
    private String validityPeriod;

    private Float allmoney;

    public static CouponOrderVo of(CouponOrder couponOrder) {
        CouponOrderVo vo = JSON.parseObject(JSON.toJSONString(couponOrder), CouponOrderVo.class);
        vo.setCreatetime(TimeUtils.timeStampToStr(((long) couponOrder.getCreatetime()) * 1000));
        vo.setValidityPeriod(TimeUtils.timeStampToStr(((long) couponOrder.getValidityPeriod()) * 1000));
        return vo;
    }


}
