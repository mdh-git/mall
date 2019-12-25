package com.mdh.gmall.pms;

import com.alibaba.fastjson.JSONObject;
import com.mdh.gmall.pms.entity.Brand;
import com.mdh.gmall.pms.entity.Product;
import com.mdh.gmall.pms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class GmallPmsApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(GmallPmsApplicationTests.class);


    @Autowired
    ProductService productService;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
        Product product = productService.getById(1);
        logger.info("ahaha");
        System.out.println(JSONObject.toJSONString(product));
    }

    @Test
    public void stringRedisTemplate(){
        stringRedisTemplate.opsForValue().set("啊哈哈", "嘿嘿嘿");
        String s = stringRedisTemplate.opsForValue().get("啊哈哈");
        System.out.println(s);
    }

    @Test
    public void redisTemplate(){
        Brand brand = new Brand();
        brand.setId(0L);
        brand.setName("品牌");
        brand.setFirstLetter("啊");
        brand.setSort(0);
        brand.setFactoryStatus(0);
        brand.setShowStatus(0);
        brand.setProductCount(0);
        brand.setProductCommentCount(0);
        brand.setLogo("logo");
        brand.setBigPic("pic");
        brand.setBrandStory("story");


        redisTemplate.opsForValue().set("brand", brand);
        Brand b =(Brand) redisTemplate.opsForValue().get("brand");
        System.out.println(b);
    }

}
