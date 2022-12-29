package com.heaven.palace.purplecloudpalace.common.persistence.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhouShengEn
 * @Date 2022-09-06 15:42
 */
@EqualsAndHashCode(callSuper = true)
@TableName("business_operation_log")
@Data
public class BusinessOperationLog extends Model<BusinessOperationLog> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作标题（新增、修改、删除）
     */
    @TableField(value = "TITLE")
    private String title;

    /**
     * 操作内容
     */
    @TableField(value = "CONTENT")
    private String content;
    
    /**
     * 业务类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 环境
     */
    @TableField(value = "env")
    private Integer env;
    
    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "UPDATE_BY")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME")
    private Date updateTime;

    


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
