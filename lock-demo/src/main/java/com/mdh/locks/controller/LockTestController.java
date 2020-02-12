package com.mdh.locks.controller;

import com.mdh.locks.service.RedissonLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author madonghao
 * @create 2020-01-19 11:09
 **/
@RestController
public class LockTestController {

    @Autowired
    RedissonLockService redissonLockService;

    /**
     * CountDownLatch
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/go")
    public Boolean gogogo(){
        return redissonLockService.gogogo();
    }


    @GetMapping("/suomen")
    public String suomen() throws InterruptedException {
        Boolean suomen = redissonLockService.suomen();
        return suomen?"锁门了":"门没锁";
    }


    @GetMapping("/rc")
    public Boolean release() throws InterruptedException {


        return redissonLockService.rc();
    }


    @GetMapping("/tc")
    public Boolean park() throws InterruptedException {


        return redissonLockService.tc();
    }


    @GetMapping("/read")
    public String read(){
        return redissonLockService.read();
    }

    @GetMapping("/write")
    public String write(){
        return redissonLockService.write();
    }


    @GetMapping("/lock")
    public Boolean lock(){

        return redissonLockService.lock();
    }


    @GetMapping("/unlock")
    public String unlock(){


        redissonLockService.unlock();
        return "ok";
    }
}
