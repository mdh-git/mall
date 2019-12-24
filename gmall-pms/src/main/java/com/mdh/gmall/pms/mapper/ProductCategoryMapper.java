package com.mdh.gmall.pms.mapper;

import com.mdh.gmall.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdh.gmall.vo.product.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author mdh
 * @since 2019-12-24
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(int i);
}
