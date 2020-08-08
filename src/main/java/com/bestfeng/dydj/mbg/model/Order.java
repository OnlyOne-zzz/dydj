package com.bestfeng.dydj.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公众号id")
    private Integer uniacid;

    private Integer uid;

    private String name;

    private String tel;

    private String msgtime;

    private String orderid;

    private Float money;

    private Integer paytime;

    @ApiModelProperty(value = "创建时间")
    private Integer createtime;

    private Byte paid;

    private Byte status;

    private Byte type;

    private String address;

    private String content;

    private String mark;

    private Integer noteid;

    private Integer sendtime;

    private Float dmoney;

    private Integer couponid;

    private Byte model;

    private Byte refund;

    private Integer pid;

    private String title;

    private Integer cityid;

    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

    private Float finalmoney;

    private Byte spaid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUniacid() {
        return uniacid;
    }

    public void setUniacid(Integer uniacid) {
        this.uniacid = uniacid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getPaytime() {
        return paytime;
    }

    public void setPaytime(Integer paytime) {
        this.paytime = paytime;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Byte getPaid() {
        return paid;
    }

    public void setPaid(Byte paid) {
        this.paid = paid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public Integer getSendtime() {
        return sendtime;
    }

    public void setSendtime(Integer sendtime) {
        this.sendtime = sendtime;
    }

    public Float getDmoney() {
        return dmoney;
    }

    public void setDmoney(Float dmoney) {
        this.dmoney = dmoney;
    }

    public Integer getCouponid() {
        return couponid;
    }

    public void setCouponid(Integer couponid) {
        this.couponid = couponid;
    }

    public Byte getModel() {
        return model;
    }

    public void setModel(Byte model) {
        this.model = model;
    }

    public Byte getRefund() {
        return refund;
    }

    public void setRefund(Byte refund) {
        this.refund = refund;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public Float getFinalmoney() {
        return finalmoney;
    }

    public void setFinalmoney(Float finalmoney) {
        this.finalmoney = finalmoney;
    }

    public Byte getSpaid() {
        return spaid;
    }

    public void setSpaid(Byte spaid) {
        this.spaid = spaid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uniacid=").append(uniacid);
        sb.append(", uid=").append(uid);
        sb.append(", name=").append(name);
        sb.append(", tel=").append(tel);
        sb.append(", msgtime=").append(msgtime);
        sb.append(", orderid=").append(orderid);
        sb.append(", money=").append(money);
        sb.append(", paytime=").append(paytime);
        sb.append(", createtime=").append(createtime);
        sb.append(", paid=").append(paid);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", address=").append(address);
        sb.append(", content=").append(content);
        sb.append(", mark=").append(mark);
        sb.append(", noteid=").append(noteid);
        sb.append(", sendtime=").append(sendtime);
        sb.append(", dmoney=").append(dmoney);
        sb.append(", couponid=").append(couponid);
        sb.append(", model=").append(model);
        sb.append(", refund=").append(refund);
        sb.append(", pid=").append(pid);
        sb.append(", title=").append(title);
        sb.append(", cityid=").append(cityid);
        sb.append(", lat=").append(lat);
        sb.append(", lng=").append(lng);
        sb.append(", finalmoney=").append(finalmoney);
        sb.append(", spaid=").append(spaid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}