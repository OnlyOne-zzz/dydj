package com.bestfeng.dydj.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;

    private Integer uniacid;

    private Integer uid;

    private String name;

    private String tel;

    private String address;

    private String daddress;

    private Byte ison;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress;
    }

    public Byte getIson() {
        return ison;
    }

    public void setIson(Byte ison) {
        this.ison = ison;
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
        sb.append(", address=").append(address);
        sb.append(", daddress=").append(daddress);
        sb.append(", ison=").append(ison);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}