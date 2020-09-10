package com.bestfeng.dydj.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Note implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公众号id")
    private Integer uniacid;

    private Integer uid;

    private String jobtitle;

    private String name;

    private Byte sex;

    private String tel;

    private String birthday;

    private String education;

    private String express;

    private String address;

    private String place;

    private String currentstatus;

    private String card;

    private String special;

    private Float money;

    private Integer areaid;

    @ApiModelProperty(value = "创建时间")
    private Integer createtime;

    private Byte status;

    private String avatarurl;

    private Integer refreshtime;

    private String thumbUrl;

    private Integer loginid;

    private Integer sort;

    private Byte isrecommand;

    private Byte isgood;

    private String username;

    private String shopname;

    private Integer cityid;

    private String lat;

    private String lng;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @ApiModelProperty(value = "服务状态")
    private Integer serviceStatus;

    private String content;

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

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(String currentstatus) {
        this.currentstatus = currentstatus;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public Integer getRefreshtime() {
        return refreshtime;
    }

    public void setRefreshtime(Integer refreshtime) {
        this.refreshtime = refreshtime;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getLoginid() {
        return loginid;
    }

    public void setLoginid(Integer loginid) {
        this.loginid = loginid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getIsrecommand() {
        return isrecommand;
    }

    public void setIsrecommand(Byte isrecommand) {
        this.isrecommand = isrecommand;
    }

    public Byte getIsgood() {
        return isgood;
    }

    public void setIsgood(Byte isgood) {
        this.isgood = isgood;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        sb.append(", jobtitle=").append(jobtitle);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", tel=").append(tel);
        sb.append(", birthday=").append(birthday);
        sb.append(", education=").append(education);
        sb.append(", express=").append(express);
        sb.append(", address=").append(address);
        sb.append(", place=").append(place);
        sb.append(", currentstatus=").append(currentstatus);
        sb.append(", card=").append(card);
        sb.append(", special=").append(special);
        sb.append(", money=").append(money);
        sb.append(", areaid=").append(areaid);
        sb.append(", createtime=").append(createtime);
        sb.append(", status=").append(status);
        sb.append(", avatarurl=").append(avatarurl);
        sb.append(", refreshtime=").append(refreshtime);
        sb.append(", thumbUrl=").append(thumbUrl);
        sb.append(", loginid=").append(loginid);
        sb.append(", sort=").append(sort);
        sb.append(", isrecommand=").append(isrecommand);
        sb.append(", isgood=").append(isgood);
        sb.append(", username=").append(username);
        sb.append(", shopname=").append(shopname);
        sb.append(", cityid=").append(cityid);
        sb.append(", lat=").append(lat);
        sb.append(", lng=").append(lng);
        sb.append(", views=").append(views);
        sb.append(", serviceStatus=").append(serviceStatus);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}