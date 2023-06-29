package com.heaven.palace.moonpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 模板内容
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@EqualsAndHashCode(callSuper = true)
@TableName("code_template_file")
@Data
public class TemplateFileModel extends Model<TemplateFileModel> {

    private static final long serialVersionUID = 1L;

    public TemplateFileModel() {

    }

    public TemplateFileModel(Integer templateId) {
        this.templateId = templateId;
    }


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * TemplateId
     */
    @TableField(value = "template_id")
    private Integer templateId;

    /**
     * 文件内容
     */
    @TableField(value = "file")
    private String file;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 创建时间
     */
    @TableField(value = "CRT_TIME")
    private Date crtTime;

    /**
     * 修改时间
     */
    @TableField(value = "MDF_TIME")
    private Date mdfTime;

}
