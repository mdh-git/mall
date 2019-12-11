package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.ProductCategory;
import com.mdh.gmall.pms.mapper.ProductCategoryMapper;
import com.mdh.gmall.pms.service.ProductCategoryService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

}
