package com.mdh.gmall.admin.ums.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdh.gmall.to.CommonResult;
import com.mdh.gmall.ums.entity.MemberLevel;
import com.mdh.gmall.ums.service.MemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员等级相关的操作
 *
 * @author madonghao
 * @create 2019-12-26 11:07
 **/
@CrossOrigin
@RestController
@Api(tags = "MemberLevelController", description = "会员等级")
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Reference
    MemberLevelService memberLevelService;

    @ApiOperation("获取会员等级列表")
    @GetMapping("/list")
    public Object list(@RequestParam(value = "defaultStatus",required = false) String defaultStatus){

        // 注意:在Controller直接使用Service原生的方法是,不能有QueryWrapper参数,会报错RpcException
        //QueryWrapper queryWrapper = new QueryWrapper<MemberLevel>();
        //queryWrapper.eq("default_status" , defaultStatus);
        List<MemberLevel> list = memberLevelService.list();
        // 这里应该是查出全部的会员信息
        //List<MemberLevel> list = memberLevelService.memberLevelList(defaultStatus);
        return new CommonResult().success(list);
    }
}
