package com.mdh.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.Brand;
import com.mdh.gmall.pms.mapper.BrandMapper;
import com.mdh.gmall.pms.service.BrandService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
