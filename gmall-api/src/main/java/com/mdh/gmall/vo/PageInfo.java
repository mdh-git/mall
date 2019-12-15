package com.mdh.gmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 封装分页信息
 *
 * @author madonghao
 * @create 2019-12-15 14:41
 **/
@Data
@ApiModel
public class PageInfo {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页码")
    private Long totalPage;

    @ApiModelProperty("每页显示记录数")
    private Long pageSize;

    @ApiModelProperty("分页查出的数据")
    private List<Object> list;

    @ApiModelProperty("当前页的页码")
    private Integer pageNum;
}
