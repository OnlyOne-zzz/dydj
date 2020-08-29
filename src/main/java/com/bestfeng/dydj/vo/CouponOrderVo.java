package com.bestfeng.dydj.vo;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.mbg.model.CouponOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
        vo.setCreatetime(timeStampToStr(couponOrder.getCreatetime()));
        vo.setValidityPeriod(timeStampToStr(couponOrder.getValidityPeriod()));
        return vo;
    }


    public static String timeStampToStr(int time) {
        return timeStampToStr((long) (time) * 1000, "yyyy-MM-dd HH:mm:ss");
    }

    public static String timeStampToStr(Long time, String pattern) {
        if (time == null) {
            return null;
        }
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

}
