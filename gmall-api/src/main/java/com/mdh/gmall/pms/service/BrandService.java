package com.mdh.gmall.pms.service;

import com.mdh.gmall.pms.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdh.gmall.vo.PageInfoVo;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
public interface BrandService extends IService<Brand> {

    PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
