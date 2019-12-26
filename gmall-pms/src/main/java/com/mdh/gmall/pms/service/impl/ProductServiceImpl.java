package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.*;
import com.mdh.gmall.pms.mapper.*;
import com.mdh.gmall.pms.service.ProductService;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductParam;
import com.mdh.gmall.vo.product.PmsProductQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    ProductFullReductionMapper productFullReductionMapper;

    @Autowired
    ProductLadderMapper productLadderMapper;

    @Autowired
    SkuStockMapper skuStockMapper;

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

    @Override
    public void saveProduct(PmsProductParam productParam) {

        // 1.pms_product:保存商品的基本信息
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        productMapper.insert(product);
        log.info("product :{}", JSON.toJSONString(product));
        // 2.pms_product_attribute_value:保存商品对应的所有属性值
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach((item) ->{
            item.setProductId(product.getId());
            productAttributeValueMapper.insert(item);
        });
        // 3.pms_product_full_reduction:保存商品的满减信息
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach((item) -> {
            item.setProductId(product.getId());
            productFullReductionMapper.insert(item);
        });
        // 4.pms_product_ladder:阶梯价格表
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach( (item) -> {
            item.setProductId(product.getId());
            productLadderMapper.insert(item);
        });
        // 5.pms_sku_stock:库存表
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 0; i < skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i);
            if(StringUtils.isBlank(skuStock.getSkuCode())){
                // skuCode必须有
                // 生成规则 商品id _ sku自增id
                skuStock.setSkuCode("");
            }
            skuStockMapper.insert(skuStock);
        }

    }
}
