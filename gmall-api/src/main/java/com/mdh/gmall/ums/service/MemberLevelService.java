package com.mdh.gmall.ums.service;

import com.mdh.gmall.ums.entity.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员等级表 服务类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
public interface MemberLevelService extends IService<MemberLevel> {

    List<MemberLevel> memberLevelList(String defaultStatus);
}
