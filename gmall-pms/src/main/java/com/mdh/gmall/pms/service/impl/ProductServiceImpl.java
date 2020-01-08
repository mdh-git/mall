package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.constant.EsConstant;
import com.mdh.gmall.pms.entity.*;
import com.mdh.gmall.pms.mapper.*;
import com.mdh.gmall.pms.service.ProductService;
import com.mdh.gmall.to.es.EsProduct;
import com.mdh.gmall.to.es.EsProductAttributeValue;
import com.mdh.gmall.to.es.EsSkuProductInfo;
import com.mdh.gmall.vo.PageInfoVo;
import com.mdh.gmall.vo.product.PmsProductParam;
import com.mdh.gmall.vo.product.PmsProductQueryParam;
import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 *
 * 查询多试验几次，增删改要快速失败。
 */
@Component
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    ProductFullReductionMapper productFullReductionMapper;

    @Autowired
    ProductLadderMapper productLadderMapper;

    @Autowired
    SkuStockMapper skuStockMapper;

    @Autowired
    JestClient jestClient;

    //当前线程共享同样的数据  多个方法使用同一个值
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
    //和ThreadLocal相同的操作
    private Map<Thread, Long> map = new HashMap<>();

    @Override
    public Product productInfo(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        // 品牌
        if(param.getBrandId() != null){
            // 前面为数据库的字段
            productQueryWrapper.eq("brand_id", param.getBrandId());
        }
        // 关键字
        if(StringUtils.isNotBlank(param.getKeyword())){
            productQueryWrapper.like("name", param.getKeyword().trim());
        }
        // 分类id
        if(param.getProductCategoryId() != null){
            // 前面为数据库的字段
            productQueryWrapper.eq("product_category_id", param.getProductCategoryId());
        }
        // 商品货号
        if(!StringUtils.isEmpty(param.getProductSn())){
            productQueryWrapper.like("product_sn", param.getProductSn());
        }
        // 审核状态
        if(param.getVerifyStatus() != null){
            productQueryWrapper.eq("verify_status", param.getVerifyStatus());
        }
        // 上架状态
        if(param.getPublishStatus() != null){
            productQueryWrapper.eq("publish_status", param.getPublishStatus());
        }
        // 未删除的状态
        productQueryWrapper.eq("delete_status", 0);
        IPage<Product> page = productMapper.selectPage(new Page<Product>(param.getPageNum(), param.getPageSize()), productQueryWrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(), page.getPages(), param.getPageSize(), page.getRecords(), page.getCurrent());
        return pageInfoVo;
    }


    /**
     * 保存商品及其商品相关的信息
     *
     * 考虑事务....
     * 1）、哪些东西是一定要回滚的、哪些即使出错了不必要回滚的。
     *      商品的核心信息（基本数据、sku）保存的时候，不要受到别的无关信息的影响。
     *      无关信息出问题，核心信息也不用回滚的。
     *
     * 2）、事务的传播行为;propagation:当前方法的事务[是否要和别人公用一个事务]如何传播下去（里面的方法如果用事务，是否和他公用一个事务）
     *
     *
     *      Propagation propagation() default Propagation.REQUIRED;
     *
     *
     *
     *      REQUIRED:(必须):
     *          Support a current transaction, create a new one if none exists.
     *          如果以前有事务，就和之前的事务公用一个事务，没有就创建一个事务；
     *      REQUIRES_NEW（总是用新的事务）:
     *           Create a new transaction, and suspend the current transaction if one exists.
     *          创建一个新的事务，如果以前有事务，暂停前面的事务。
     *
     *      SUPPORTS（支持）:
     *          Support a current transaction, execute non-transactionally if none exists.
     *          之前有事务，就以事务的方式运行，没有事务也可以；
     *
     *      MANDATORY（强制）:没事务就报错
     *          Support a current transaction, throw an exception if none exists
     *          一定要有事务，如果没事务就报错
     *
     *
     *      NOT_SUPPORTED（不支持）:
     *          Execute non-transactionally, suspend the current transaction if one exists
     *          不支持在事务内运行，如果已经有事务了，就挂起当前存在的事务
     *
     *      NEVER（从不使用）:
     *           Execute non-transactionally, throw an exception if a transaction exists.
     *           不支持在事务内运行，如果已经有事务了，抛异常
     *
     *
     *      NESTED:
     *          Execute within a nested transaction if a current transaction exists,
     *          开启一个子事务（MySQL不支持），需要支持还原点功能的数据库才行；
     *
     *
     * 一家人带着老王去旅游；
     *      一家人：开自己的车还是坐老王的车
     *
     *      Required：坐老王车
     *      Requires_new：一定得开车，开新的
     *
     *      SUPPORTS：用车，有车就用，没车走路；
     *      MANDATORY：用车，没车就骂街。。。
     *
     *      NOT_SUPPORTED：不支持用车。有车放那不用
     *      NEVER：从不用车，有车抛异常
     *
     *
     *
     *
     * 外事务{
     *
     *     A();//事务.Required：跟着回滚
     *
     *     b();//事务.Requires_new：不回滚
     *
     *     //自己给数据库插入数据
     *
     *     int i = 10/0;
     *
     * }
     *
     * Required_new
     * 外事务{
     *
     *     A（）；Required; A
     *     B（）;Requires_new B
     *     try{
     *         C();Required; C
     *     }catch(Exception e){
     *         //c出异常？
     *     }
     *
     *     D();Requires_new; D
     *
     *     //给数据库存 --外
     *
     *    // int i = 10/0;
     *
     * }
     *
     * 场景1：
     *      A方法出现了异常；由于异常机制导致代码停止，下面无法执行，数据库什么都没有
     * 场景2：
     *     C方法出现异常；A回滚，B成功，C回滚，D无法执行，外无法执行
     * 场景3：
     *      外成了后，int i = 10/0; B,D成功。A,C,外都执行了但是必须回滚
     * 场景4：
     *     D炸；抛异常。外事务感知到异常。A,C回滚，外执行不到，D自己回滚，B成功
     * 场景5：
     *     C用try-catch执行；C出了异常回滚，由于异常被捕获，外事务没有感知异常。A,B,D都成，C自己回滚
     *
     * 总结：
     *      传播行为过程中，只要Requires_new被执行过就一定成功，不管后面出不出问题。异常机制还是一样的，出现异常代码以后不执行。
     * Required只要感觉到异常就一定回滚。和外事务是什么传播行为无关。
     *
     * 传播行为总是来定义，当一个事务存在的时候，他内部的事务该怎么执行。
     *
     *
     *
     *
     *
     *
     * 如何让某些可以不回滚
     *
     *
     * 事务Spring中是怎么做的？
     * TransactionManager；
     * AOP做；
     *
     * 动态代理。
     *  hahaServiceProxy.saveBaseInfo();
     *
     *  A{
     *      A(){
     *          B(); //1,2,3
     *          C(); //4,5,6
     *          D(); //7,8,9
     *      }
     *  }
     *
     *  自己类调用自己类里面的方法，就是一个复制粘贴。归根到底，只是给
     *  controller{
     *      serviceProxy.a();
     *  }
     *  对象.方法()才能加上事务。
     *
     *
     *  A(){
     *      //1,2,3,4,5,6,7,8,9
     *      //
     *  }
     *
     *  A{
     *      A(){
     *          hahaService.B();
     *          hahaService.C();
     *          hahaService.D();
     *
     *      }
     *  }
     *
     *  事务的问题：
     *      Service自己调用自己的方法，无法加上真正的自己内部调整的各个事务
     *      解决：如果是  对象.方法()那就好了
     *       1）、要是能拿到ioc容器，从容器中再把我们的组件获取一下，用对象调方法。
     *
     *
     *
     * 复习：事务传播行为，
     * ====================================================================
     * 隔离级别：解决读写加锁问题的（数据底层的方案）。  可重复读（快照）；
     *
     * 读未提交：
     * 读已提交：
     * 可重复读：
     * 串行化：
     *
     * ===========================================================
     * 异常回滚策略
     * 异常：
     *      运行时异常（不受检查异常）
     *          ArithmeticException ......
     *      编译时异常（受检异常）
     *            FileNotFound；1）要么throw要么try- catch
     *
     * 运行的异常默认是一定回滚
     * 编译时异常默认是不回滚的；
     *      rollbackFor：指定哪些异常一定回滚的。
     *
     * @param productParam
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveProduct(PmsProductParam productParam) {

        ProductServiceImpl proxy = (ProductServiceImpl)AopContext.currentProxy();

        // 1.pms_product:保存商品的基本信息
        proxy.buildProduct(productParam);

        // 2.pms_product_attribute_value:保存商品对应的所有属性值
        proxy.buildProductAttributeValue(productParam);

        // 3.pms_product_full_reduction:保存商品的满减信息
        proxy.buildProductFullReduction(productParam);

        // 4.pms_product_ladder:阶梯价格表
        proxy.buildProductLadderList(productParam);

        // 5.pms_sku_stock:库存表
        proxy.buildSkuStockList(productParam);

    }

    /**
     * CudSerivce：增删改service
     * RService：读service
     *
     *1）、dubbo的默认集群容错哪几种，怎么做？
     * failover/failfast/failsafe/failback/forking；
     * @Service注解上一配置就行。
     *
     * 改掉默认的mapping信息；
     * 1）、改掉不分词的字段
     * 2）、嵌入式对象的Mapping一定要用nested声明，这样才能正确的检索到数据
     *
     * @param ids
     * @param publishStatus
     */
    @Override
    public void updatePublishStatus(List<Long> ids, Integer publishStatus) {
        if(publishStatus == 0){
            ids.forEach((id)->{
                //下架
                //改数据库状态
                setProductPublishStatus(publishStatus, id);
                //删es
                deleteProductFromEs(id);
            });

        }else {
            //上架
            ids.forEach((id)->{
                //该数据状态
                setProductPublishStatus(publishStatus, id);
                //加es
                saveProductToEs(id);
            });
        }
    }

    /**
     * 上架的数据保存到es 支持搜索
     *
     * 给数据库插入数据
     * 1）、dubbo远程调用插入数据服务，可能经常超时。dubbo默认会重试
     * 导致这个方法会被调用多次。可能导致数据库同样的数据有多个。
     *
     * 2）、dubbo有自己默认的集群容错。
     *
     * 给数据库做数据的，最好用dubbo的快速失败模式。我们手工重试
     * @param id
     */
    private void saveProductToEs(Long id) {
        //1、查出商品的基本新
        Product productInfo = productInfo(id);
        EsProduct esProduct = new EsProduct();


        //1、复制基本信息
        BeanUtils.copyProperties(productInfo,esProduct);


        //2、复制sku信息，对于es要保存商品信息,还要查出这个商品的sku，给es中保存
        List<SkuStock> stocks = skuStockMapper.selectList(new QueryWrapper<SkuStock>().eq("product_id", id));
        List<EsSkuProductInfo> esSkuProductInfos = new ArrayList<>(stocks.size());


        //查出当前商品的sku属性  颜色  尺码
        List<ProductAttribute>  skuAttributeNames = productAttributeValueMapper.selectProductSaleAttrName(id);
        stocks.forEach((skuStock)->{
            EsSkuProductInfo info = new EsSkuProductInfo();
            BeanUtils.copyProperties(skuStock,info);

            //闪亮 黑色
            String subTitle = esProduct.getName();
            if(!org.springframework.util.StringUtils.isEmpty(skuStock.getSp1())){
                subTitle+=" "+skuStock.getSp1();
            }
            if(!org.springframework.util.StringUtils.isEmpty(skuStock.getSp2())){
                subTitle+=" "+skuStock.getSp2();
            }
            if(!org.springframework.util.StringUtils.isEmpty(skuStock.getSp3())){
                subTitle+=" "+skuStock.getSp3();
            }
            //sku的特色标题
            info.setSkuTitle(subTitle);
            List<EsProductAttributeValue> skuAttributeValues = new ArrayList<>();

            for (int i=0;i<skuAttributeNames.size();i++){
                //skuAttr 颜色/尺码
                EsProductAttributeValue value = new EsProductAttributeValue();

                value.setName(skuAttributeNames.get(i).getName());
                value.setProductId(id);
                value.setProductAttributeId(skuAttributeNames.get(i).getId());
                value.setType(skuAttributeNames.get(i).getType());

                //颜色   尺码;让es去统计‘；改掉查询商品的属性分类里面所有属性的时候，按照sort字段排序好
                if(i==0){
                    value.setValue(skuStock.getSp1());
                }
                if(i==1){
                    value.setValue(skuStock.getSp2());
                }
                if(i==2){
                    value.setValue(skuStock.getSp3());
                }

                skuAttributeValues.add(value);

            }


            info.setAttributeValues(skuAttributeValues);
            //sku有多个销售属性；颜色，尺码
            esSkuProductInfos.add(info);
            //查出销售属性的名

        });

        esProduct.setSkuProductInfos(esSkuProductInfos);


        List<EsProductAttributeValue> attributeValues = productAttributeValueMapper.selectProductBaseAttrAndValue(id);
        //3、复制公共属性信息，查出这个商品的公共属性
        esProduct.setAttrValueList(attributeValues);

        try {
            //把商品保存到es中
            Index build = new Index.Builder(esProduct)
                    .index(EsConstant.PRODUCT_ES_INDEX)
                    .type(EsConstant.PRODUCT_INFO_ES_TYPE)
                    .id(id.toString())
                    .build();
            DocumentResult execute = jestClient.execute(build);
            boolean succeeded = execute.isSucceeded();
            if(succeeded){
                log.info("ES中；id为{}商品上架完成",id);
            }else {
                log.error("ES中；id为{}商品未保存成功，开始重试",id);
                //saveProductToEs(id);
            }
        }catch (Exception e){
            log.error("ES中；id为{}商品数据保存异常；{}",id,e.getMessage());
            //saveProductToEs(id);
        }

    }

    /**
     * 下架的数据从es中删除
     * @param id
     */
    private void deleteProductFromEs(Long id) {
        Delete delete = new Delete.Builder(id.toString()).index(EsConstant.PRODUCT_ES_INDEX)
                .type(EsConstant.PRODUCT_INFO_ES_TYPE)
                .build();
        try {
            DocumentResult execute = jestClient.execute(delete);
            if(execute.isSucceeded()){
                log.info("商品：{} ==》ES下架完成",id);
            }else {
                //deleteProductFromEs(id);
                log.error("商品：{} ==》ES下架失败",id);
            }
        }catch (Exception e){
            //deleteProductFromEs(id);
            log.error("商品：{} ==》ES下架失败",id);
        }
    }

    /**
     * 修改上下架的状态
     * @param publishStatus
     * @param id
     */
    private void setProductPublishStatus(Integer publishStatus, Long id) {
        //javaBean应该都去用包装类型
        Product product = new Product();
        //默认所有属性为null
        product.setId(id);
        product.setPublishStatus(publishStatus);
        //mybatis-plus自带的更新方法是哪个字段有值就更哪个字段
        productMapper.updateById(product);
    }


    // 1.pms_product:保存商品的基本信息
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buildProduct(PmsProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        productMapper.insert(product);
        threadLocal.set(product.getId());
        map.put(Thread.currentThread(), product.getId());
        log.debug("product :{}", JSON.toJSONString(product));
        log.debug("当前线程....{}-->{}",Thread.currentThread().getId(),Thread.currentThread().getName());
    }


    // 2.pms_product_attribute_value:保存商品对应的所有属性值
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buildProductAttributeValue(PmsProductParam productParam) {
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach((item) ->{
            item.setProductId(threadLocal.get());
            productAttributeValueMapper.insert(item);
        });
    }

    // 3.pms_product_full_reduction:保存商品的满减信息
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buildProductFullReduction(PmsProductParam productParam) {
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach((item) -> {
            Long id = map.get(Thread.currentThread());
            System.out.println("id:" + id.toString());
            item.setProductId(threadLocal.get());
            productFullReductionMapper.insert(item);
        });
    }

    // 4.pms_product_ladder:阶梯价格表
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buildProductLadderList(PmsProductParam productParam) {
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach( (item) -> {
            item.setProductId(threadLocal.get());
            productLadderMapper.insert(item);
        });
    }

    // 5.pms_sku_stock:库存表
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buildSkuStockList(PmsProductParam productParam) {
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 0; i < skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i);
            if(StringUtils.isBlank(skuStock.getSkuCode())){
                // skuCode必须有
                // 生成规则 商品id _ sku自增id
                skuStock.setSkuCode(threadLocal.get() + "_" + (i + 1));
            }
            skuStockMapper.insert(skuStock);
        }
    }


}
