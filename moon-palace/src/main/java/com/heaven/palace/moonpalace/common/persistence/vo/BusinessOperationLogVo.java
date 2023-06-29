package com.heaven.palace.moonpalace.common.persistence.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhouShengEn
 * @Date 2022-09-06 15:42
 */
@Data
public class BusinessOperationLogVo implements Serializable{

    private static final long serialVersionUID = 9195422593779478670L;
    /**
     * Id
     */
    private Integer id;

    /**
     * 操作标题（新增、修改、删除）
     */
    private String title;

    /**
     * 操作内容
     */
    private String content;
    
    /**
     * 业务类型
     */
    private String type;
    /**
     * 环境
     */
    private Integer env;
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private String updateTime;

    


}
