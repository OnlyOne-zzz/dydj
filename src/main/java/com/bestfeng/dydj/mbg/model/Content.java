package com.bestfeng.dydj.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Content implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公众号id")
    private Integer uniacid;

    private String title;

    @ApiModelProperty(value = "创建时间")
    private Integer createtime;

    private Float money;

    private Integer sort;

    private Integer pid;

    private Integer sid;

    private Integer hits;

    private Byte status;

    private String thumb;

    private String intro;

    private Byte isding;

    @ApiModelProperty(value = "文章内容")
    private String content;

    private String piclist;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Byte getIsding() {
        return isding;
    }

    public void setIsding(Byte isding) {
        this.isding = isding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPiclist() {
        return piclist;
    }

    public void setPiclist(String piclist) {
        this.piclist = piclist;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uniacid=").append(uniacid);
        sb.append(", title=").append(title);
        sb.append(", createtime=").append(createtime);
        sb.append(", money=").append(money);
        sb.append(", sort=").append(sort);
        sb.append(", pid=").append(pid);
        sb.append(", sid=").append(sid);
        sb.append(", hits=").append(hits);
        sb.append(", status=").append(status);
        sb.append(", thumb=").append(thumb);
        sb.append(", intro=").append(intro);
        sb.append(", isding=").append(isding);
        sb.append(", content=").append(content);
        sb.append(", piclist=").append(piclist);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}