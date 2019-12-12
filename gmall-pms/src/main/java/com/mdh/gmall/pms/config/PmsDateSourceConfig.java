package com.mdh.gmall.pms.config;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


/**
 * PMS数据源配置
 *
 * @author madonghao
 * @create 2019-12-12 9:51
 **/
@Configuration
public class PmsDateSourceConfig {


    /**
     * <groupId>org.apache.shardingsphere</groupId>
     * <artifactId>sharding-jdbc-core</artifactId>
     * <version>4.0.0-RC2</version>
     *
     * 4.0.0-RC2这个版本 YamlShardingDataSourceFactory.createDataSource(file)这段报错
     *
     *
     * <groupId>io.shardingjdbc</groupId>
     * <artifactId>sharding-jdbc-core</artifactId>
     * <version>2.0.3</version>
     *  2.0.3遇见同样发的问题，还没解决
     *
     *
     * @return
     * @throws IOException
     * @throws SQLException
     */
//    @Bean
//    public DataSource dateSource() throws IOException, SQLException {
//        File file = ResourceUtils.getFile("classpath:sharding-jdbc.yml");
//        DataSource dataSource = ShardingDataSourceFactory.createDataSource(file);
//        return dataSource;
//    }

}
