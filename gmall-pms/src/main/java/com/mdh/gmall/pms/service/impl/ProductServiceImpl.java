package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.Product;
import com.mdh.gmall.pms.mapper.ProductMapper;
import com.mdh.gmall.pms.service.ProductService;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        // 品牌
        if(param.getBrandId() != null){
            // 前面为数据库的字段
            productQueryWrapper.eq("brand_id", param.getBrandId());
        }
        // 关键字
        if(StringUtils.isNotBlank(param.getKeyword())){
            productQueryWrapper.like("name", param.getKeyword().trim());
        }
        // 分类id
        if(param.getProductCategoryId() != null){
            // 前面为数据库的字段
            productQueryWrapper.eq("product_category_id", param.getProductCategoryId());
        }
        // 商品货号
        if(!StringUtils.isEmpty(param.getProductSn())){
            productQueryWrapper.like("product_sn", param.getProductSn());
        }
        // 审核状态
        if(param.getVerifyStatus() != null){
            productQueryWrapper.eq("verify_status", param.getVerifyStatus());
        }
        // 上架状态
        if(param.getPublishStatus() != null){
            productQueryWrapper.eq("publish_status", param.getPublishStatus());
        }
        // 未删除的状态
        productQueryWrapper.eq("delete_status", 0);
        IPage<Product> page = productMapper.selectPage(new Page<Product>(param.getPageNum(), param.getPageSize()), productQueryWrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(), page.getPages(), param.getPageSize(), page.getRecords(), page.getCurrent());
        return pageInfoVo;
    }
}
