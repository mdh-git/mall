package com.mdh.gmall.to;

import lombok.Data;

/**
 * 获取OSS上传授权返回结果
 *
 * @author madonghao
 * @create 2019-12-26 16:51
 **/
@Data
public class OssPolicyResult {

    private String accessKeyId;
    private String policy;
    private String signature;
    private String dir;
    private String host;
}
