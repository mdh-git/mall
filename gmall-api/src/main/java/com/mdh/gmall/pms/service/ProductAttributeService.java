package com.mdh.gmall.pms.service;

import com.mdh.gmall.pms.entity.ProductAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdh.gmall.vo.PageInfoVo;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
public interface ProductAttributeService extends IService<ProductAttribute> {

    PageInfoVo getCategoryAttributes(Long cid, Integer type, Integer pageSize, Integer pageNum);
}
