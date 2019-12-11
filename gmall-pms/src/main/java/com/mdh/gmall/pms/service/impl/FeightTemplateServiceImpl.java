package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.FeightTemplate;
import com.mdh.gmall.pms.mapper.FeightTemplateMapper;
import com.mdh.gmall.pms.service.FeightTemplateService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 运费模版 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class FeightTemplateServiceImpl extends ServiceImpl<FeightTemplateMapper, FeightTemplate> implements FeightTemplateService {

}
