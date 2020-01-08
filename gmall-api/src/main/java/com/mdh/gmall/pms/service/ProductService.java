package com.mdh.gmall.pms.service;

import com.mdh.gmall.pms.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductParam;
import com.mdh.gmall.vo.product.PmsProductQueryParam;

import java.util.List;

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
     * 查询商品详情
     * @param id
     * @return
     */
    Product productInfo(Long id);

    /**
     * 根据查询条件查询分页数据
     * @param productQueryParam
     * @return
     */
    PageInfoVo productPageInfo(PmsProductQueryParam productQueryParam);

    /**
     * 保存商品数据
     * @param productParam
     */
    void saveProduct(PmsProductParam productParam);

    /**
     * 批量上下架
     * @param ids
     * @param publishStatus
     */
    void updatePublishStatus(List<Long> ids, Integer publishStatus);
}
