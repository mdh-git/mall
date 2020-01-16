package com.mdh.gmall.search;

import com.mdh.gmall.vo.search.SearchParam;
import com.mdh.gmall.vo.search.SearchResponse;

/**
 * @author madonghao
 * @create 2020-01-10 13:58
 *
 * 商品检索服务
 **/
public interface SearchProductService {
    SearchResponse searchProduct(SearchParam searchParam);
}
