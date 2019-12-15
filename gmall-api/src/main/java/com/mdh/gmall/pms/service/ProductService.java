package com.mdh.gmall.pms.service;

import com.mdh.gmall.pms.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductQueryParam;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据查询条件查询分页数据
     * @param productQueryParam
     * @return
     */
    PageInfoVo productPageInfo(PmsProductQueryParam productQueryParam);
}
