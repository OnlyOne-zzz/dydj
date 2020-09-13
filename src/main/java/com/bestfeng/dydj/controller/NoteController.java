package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.controller.request.NoteListSearchNameRequest;
import com.bestfeng.dydj.service.NoteService;
import com.bestfeng.dydj.vo.NoteVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonPage;
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
@RequestMapping("/note")
@Api(tags = "NoteController", description = "技师管理")
@SignValidated
public class NoteController{


    @Autowired
    private NoteService noteService;

    @PostMapping("/paging")
    @ApiOperation("分页排序查询")
    public CommonResult<CommonPage<NoteVo>> paging(@RequestBody NoteListRequest request) {
        return CommonResult.success(noteService.paging(request));
    }

    @PostMapping("/name-search")
    @ApiOperation("根据名称搜索")
    public CommonResult<CommonPage<NoteVo>> pagingByName(@RequestBody NoteListSearchNameRequest request) {
        return CommonResult.success(noteService.pagingByName(request.getName()));
    }
}
