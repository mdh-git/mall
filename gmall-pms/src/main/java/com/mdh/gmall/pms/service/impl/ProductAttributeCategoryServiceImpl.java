package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.Product;
import com.mdh.gmall.pms.entity.ProductAttributeCategory;
import com.mdh.gmall.pms.mapper.ProductAttributeCategoryMapper;
import com.mdh.gmall.pms.service.ProductAttributeCategoryService;
import com.mdh.gmall.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements ProductAttributeCategoryService {

    @Autowired
    private ProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public PageInfoVo productAttributeCategoryPageInfo(Integer pageSize, Integer pageNum) {
        IPage<ProductAttributeCategory> page = productAttributeCategoryMapper.selectPage(new Page<ProductAttributeCategory>(pageNum, pageSize), null);
        return PageInfoVo.getVo(page, pageSize.longValue());
    }
}
