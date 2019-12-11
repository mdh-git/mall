package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.ProductVertifyRecord;
import com.mdh.gmall.pms.mapper.ProductVertifyRecordMapper;
import com.mdh.gmall.pms.service.ProductVertifyRecordService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品审核记录 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class ProductVertifyRecordServiceImpl extends ServiceImpl<ProductVertifyRecordMapper, ProductVertifyRecord> implements ProductVertifyRecordService {

}
