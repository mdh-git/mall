package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.AlbumPic;
import com.mdh.gmall.pms.mapper.AlbumPicMapper;
import com.mdh.gmall.pms.service.AlbumPicService;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 画册图片表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class AlbumPicServiceImpl extends ServiceImpl<AlbumPicMapper, AlbumPic> implements AlbumPicService {

}
