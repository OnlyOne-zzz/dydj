package com.bestfeng.dydj.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmallProDto implements Serializable {

    /*小程序、公众号的code*/
    private String code;

    /*手机号*/
    private String phone;

    private String encryptedData;
    private String iv;

    private String encryptedDataPhone;
    private String ivPhone;

    private Integer channel;
    private Integer type;
}
