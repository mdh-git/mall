package com.mdh.gmall.pms.service;

import com.mdh.gmall.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdh.gmall.vo.PageInfoVo;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    PageInfoVo productCategoryPageInfo(Integer pageSize, Integer pageNum, Long parentId);
}
