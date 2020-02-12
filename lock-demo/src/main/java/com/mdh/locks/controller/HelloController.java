package com.mdh.locks.controller;

import com.mdh.locks.service.RedisIncrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madonghao
 * @create 2020-01-16 13:52
 **/
@RestController
public class HelloController {

    @Autowired
    RedisIncrService redisIncrService;

    @GetMapping("/incr")
    public String incr(){
        redisIncrService.incr();
        return "ok";
    }

    @GetMapping("/incr2")
    public String incr2(){
        redisIncrService.incrDistribute();
        return "ok";
    }

    @GetMapping("/getIncr")
    public String getIncr(){
        return redisIncrService.getIncr();
    }
}
