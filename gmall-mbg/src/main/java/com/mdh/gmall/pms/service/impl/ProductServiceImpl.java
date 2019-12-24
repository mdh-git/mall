package com.mdh.gmall.pms.service.impl;

import com.mdh.gmall.pms.entity.Product;
import com.mdh.gmall.pms.mapper.ProductMapper;
import com.mdh.gmall.pms.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-24
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
