package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.mbg.model.Address;
import com.bestfeng.dydj.service.AddressService;
import io.swagger.annotations.Api;
import org.aurochsframework.boot.commons.controller.GeneralCrudController;
import org.aurochsframework.boot.commons.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/address")
@Api(tags = "AddressController", description = "地址管理")
public class AddressController implements GeneralCrudController<Address> {


    @Autowired
    private AddressService addressService;


    @Override
    public GeneralService<Address> getService() {
        return addressService;
    }
}
