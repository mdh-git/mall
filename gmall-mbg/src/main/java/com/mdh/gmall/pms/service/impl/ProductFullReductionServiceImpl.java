package com.mdh.gmall.pms.service.impl;

import com.mdh.gmall.pms.entity.ProductFullReduction;
import com.mdh.gmall.pms.mapper.ProductFullReductionMapper;
import com.mdh.gmall.pms.service.ProductFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-24
 */
@Service
public class ProductFullReductionServiceImpl extends ServiceImpl<ProductFullReductionMapper, ProductFullReduction> implements ProductFullReductionService {

}
