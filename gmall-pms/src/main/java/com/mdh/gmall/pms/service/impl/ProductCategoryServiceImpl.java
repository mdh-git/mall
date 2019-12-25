package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.constant.SysCacheConstant;
import com.mdh.gmall.pms.entity.ProductCategory;
import com.mdh.gmall.pms.mapper.ProductCategoryMapper;
import com.mdh.gmall.pms.service.ProductCategoryService;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
@Slf4j
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public PageInfoVo productCategoryPageInfo(Integer pageSize, Integer pageNum, Long parentId) {
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", parentId);
        IPage<ProductCategory> page = productCategoryMapper.selectPage(new Page<ProductCategory>(pageNum, pageSize), queryWrapper);
        return PageInfoVo.getVo(page, pageSize.longValue());
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(Integer i) {
        Object cacheMenu = redisTemplate.opsForValue().get(SysCacheConstant.CATEGORY_MENU_CACHE_KEY);
        List<PmsProductCategoryWithChildrenItem> items = null;
        if(cacheMenu != null){
            //缓存中有
            log.info("菜单数据命中缓存......");
            items = (List<PmsProductCategoryWithChildrenItem>) redisTemplate.opsForValue().get("");
        } else {
            items = productCategoryMapper.listCatelogWithChilder(i);
            redisTemplate.opsForValue().set(SysCacheConstant.CATEGORY_MENU_CACHE_KEY,items);
        }


        return items;
    }

}
