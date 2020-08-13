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

    private BigDecimal money;

    private Integer paytime;

    @ApiModelProperty(value = "创建时间")
    private Integer createtime;

    private Integer paid;

    private Integer status;

    private Integer type;

    private String address;

    private String content;

    private String mark;

    private Integer noteid;

    private Integer sendtime;

    private BigDecimal dmoney;

    private Integer couponid;

    private Integer model;

    private Integer refund;

    private Integer pid;

    private String title;

    private Integer cityid;

    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

    private BigDecimal finalmoney;

    private Integer spaid;

    @ApiModelProperty(value = "技师图片(冗余)")
    private String noteAvatarUrl;

    @ApiModelProperty(value = "技师名字(冗余)")
    private String noteName;

    @ApiModelProperty(value = "项目Id")
    private Integer contentId;

    @ApiModelProperty(value = "项目图(冗余)")
    private String contentThumb;

    @ApiModelProperty(value = "预约时间")
    private String subTime;

    @ApiModelProperty(value = "交通方式0:出租 1:公交/地铁")
    private Integer trafficType;

    @ApiModelProperty(value = "估算公里数")
    private BigDecimal trafficReckonMile;

    @ApiModelProperty(value = "交通费用")
    private BigDecimal trafficPrice;

    @ApiModelProperty(value = "详细地址")
    private String daddress;

    @ApiModelProperty(value = "服务项目名字")
    private String contentName;

    @ApiModelProperty(value = "更新时间")
    private Integer updatetime;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
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

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public BigDecimal getDmoney() {
        return dmoney;
    }

    public void setDmoney(BigDecimal dmoney) {
        this.dmoney = dmoney;
    }

    public Integer getCouponid() {
        return couponid;
    }

    public void setCouponid(Integer couponid) {
        this.couponid = couponid;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
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

    public BigDecimal getFinalmoney() {
        return finalmoney;
    }

    public void setFinalmoney(BigDecimal finalmoney) {
        this.finalmoney = finalmoney;
    }

    public Integer getSpaid() {
        return spaid;
    }

    public void setSpaid(Integer spaid) {
        this.spaid = spaid;
    }

    public String getNoteAvatarUrl() {
        return noteAvatarUrl;
    }

    public void setNoteAvatarUrl(String noteAvatarUrl) {
        this.noteAvatarUrl = noteAvatarUrl;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentThumb() {
        return contentThumb;
    }

    public void setContentThumb(String contentThumb) {
        this.contentThumb = contentThumb;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public Integer getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(Integer trafficType) {
        this.trafficType = trafficType;
    }

    public BigDecimal getTrafficReckonMile() {
        return trafficReckonMile;
    }

    public void setTrafficReckonMile(BigDecimal trafficReckonMile) {
        this.trafficReckonMile = trafficReckonMile;
    }

    public BigDecimal getTrafficPrice() {
        return trafficPrice;
    }

    public void setTrafficPrice(BigDecimal trafficPrice) {
        this.trafficPrice = trafficPrice;
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Integer getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
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
        sb.append(", noteAvatarUrl=").append(noteAvatarUrl);
        sb.append(", noteName=").append(noteName);
        sb.append(", contentId=").append(contentId);
        sb.append(", contentThumb=").append(contentThumb);
        sb.append(", subTime=").append(subTime);
        sb.append(", trafficType=").append(trafficType);
        sb.append(", trafficReckonMile=").append(trafficReckonMile);
        sb.append(", trafficPrice=").append(trafficPrice);
        sb.append(", daddress=").append(daddress);
        sb.append(", contentName=").append(contentName);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}