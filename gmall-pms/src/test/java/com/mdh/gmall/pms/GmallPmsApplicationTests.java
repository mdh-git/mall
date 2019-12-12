package com.mdh.gmall.pms;

import com.alibaba.fastjson.JSONObject;
import com.mdh.gmall.pms.entity.Product;
import com.mdh.gmall.pms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GmallPmsApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(GmallPmsApplicationTests.class);


    @Autowired
    ProductService productService;

    @Test
    void contextLoads() {
        Product product = productService.getById(1);
        logger.info("ahaha");
        System.out.println(JSONObject.toJSONString(product));
    }


}
