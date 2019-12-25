package com.mdh.gmall.pms.service;

import com.mdh.gmall.pms.entity.ProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdh.gmall.vo.PageInfoVo;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {

    /**
     * 分页获取所有商品属性分类
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo productAttributeCategoryPageInfo(Integer pageSize, Integer pageNum);

    void updateNameById(Long id, String name);
}
