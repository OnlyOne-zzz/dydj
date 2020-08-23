package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult<CommonPage<Note>> paging(@RequestBody NoteListRequest request) {
        return CommonResult.success(noteService.paging(request));
    }

    @GetMapping("/name-search/{name}")
    @ApiOperation("根据名称搜索")
    public CommonResult<CommonPage<Note>> pagingByName(@PathVariable String name) {
        return CommonResult.success(noteService.pagingByName(name));
    }
}
