package com.bestfeng.dydj.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class TestRequest extends BasicRequest {

    private String username;

    private String password;
    private Integer type;
}
