package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.TravelRequest;
import com.bestfeng.dydj.enums.TravelTypeEnums;
import com.bestfeng.dydj.manager.travel.TravelServiceSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/travel")
@Api(tags = "TravelController", description = "出行管理")
@SignValidated
public class TravelController {

    @Autowired
    private TravelServiceSupport travelServiceSupport;

    @PostMapping("/fare")
    @ApiOperation("出行价格")
    public CommonResult<Long> travelFate(@RequestBody TravelRequest request) {
        return CommonResult.success(travelServiceSupport.travelFareTo(TravelTypeEnums.ofValue(request.getType()), request.getDistance()));
    }
}
