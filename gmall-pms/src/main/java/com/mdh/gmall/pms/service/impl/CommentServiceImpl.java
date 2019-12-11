package com.mdh.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.Comment;
import com.mdh.gmall.pms.mapper.CommentMapper;
import com.mdh.gmall.pms.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
