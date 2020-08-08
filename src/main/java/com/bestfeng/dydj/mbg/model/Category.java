package com.bestfeng.dydj.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "所属帐号")
    private Integer weid;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类图片")
    private String thumb;

    @ApiModelProperty(value = "上级分类ID,0为第一级")
    private Integer parentid;

    private Integer isrecommand;

    @ApiModelProperty(value = "分类介绍")
    private String description;

    @ApiModelProperty(value = "排序")
    private Byte displayorder;

    @ApiModelProperty(value = "是否开启")
    private Byte enabled;

    private Integer model;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeid() {
        return weid;
    }

    public void setWeid(Integer weid) {
        this.weid = weid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getIsrecommand() {
        return isrecommand;
    }

    public void setIsrecommand(Integer isrecommand) {
        this.isrecommand = isrecommand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(Byte displayorder) {
        this.displayorder = displayorder;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", weid=").append(weid);
        sb.append(", name=").append(name);
        sb.append(", thumb=").append(thumb);
        sb.append(", parentid=").append(parentid);
        sb.append(", isrecommand=").append(isrecommand);
        sb.append(", description=").append(description);
        sb.append(", displayorder=").append(displayorder);
        sb.append(", enabled=").append(enabled);
        sb.append(", model=").append(model);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}