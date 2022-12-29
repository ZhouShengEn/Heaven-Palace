package com.heaven.palace.purplecloudpalace.model;

import com.heaven.palace.purplecloudpalace.model.enmus.GenerationType;
import lombok.Data;

import java.io.Serializable;


/**
 * 主键的参数
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Data
public class GenerationEntity implements Serializable {


    private static final long serialVersionUID = -5758707294938160661L;
    /**
     * Java包名
     */
    private String codePackage ;
    /**
     * html包名,如果为空,使用codePackage
     */
    private String htmlPackage;
    /**
     * js包名,如果为空,使用codePackage
     */
    private String jsPackage;
    /**
     * xml包名,如果为空,使用codePackage
     */
    private String xmlPackage;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 主键名称
     */
    private String idName = "id";
    /**
     * 主键类型
     */
    private GenerationType idType = GenerationType.IDENTITY;
    /**
     * 类名,不填使用表名
     */
    private String entityName;
    /**
     * 生成时间
     **/
    private String date;
    /**
     * 作者
     **/
    private String author;

    /**
     * IP地址
     */
    private String ipAddress;
    /**
     * sftp账户
     */
    private String sftpAccount;
    /**
     * sftp密码
     */
    private String sftpPassword;

}
