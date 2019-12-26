package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.ProductAttribute;
import com.mdh.gmall.pms.entity.ProductAttributeCategory;
import com.mdh.gmall.pms.mapper.ProductAttributeCategoryMapper;
import com.mdh.gmall.pms.mapper.ProductAttributeMapper;
import com.mdh.gmall.pms.service.ProductAttributeService;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductAttributeParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {

    @Autowired
    ProductAttributeMapper productAttributeMapper;

    @Autowired
    ProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public PageInfoVo getCategoryAttributes(Long cid, Integer type, Integer pageSize, Integer pageNum) {

        QueryWrapper<ProductAttribute> queryWrapper = new QueryWrapper<ProductAttribute>()
                .eq("product_attribute_category_id", cid)
                .eq("type", type);
        IPage<ProductAttribute> iPage = productAttributeMapper.selectPage(new Page<ProductAttribute>(pageNum, pageSize), queryWrapper);
        return PageInfoVo.getVo(iPage, pageSize.longValue());
    }

    @Override
    public int create(PmsProductAttributeParam productAttributeParam) {
        ProductAttribute productAttribute = new ProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, productAttribute);
        int insert = productAttributeMapper.insert(productAttribute);
        ProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectById(productAttribute.getProductAttributeCategoryId());
        if(productAttribute.getType().intValue() == 0){
            productAttributeCategory.setAttributeCount(Integer.valueOf(productAttributeCategory.getAttributeCount().intValue() + 1));
        } else if(productAttribute.getType().intValue() == 1){
            productAttributeCategory.setParamCount(Integer.valueOf(productAttributeCategory.getParamCount().intValue() + 1));
        }
        productAttributeCategoryMapper.updateById(productAttributeCategory);
        return insert;
    }
}
