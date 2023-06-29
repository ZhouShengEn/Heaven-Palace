package com.heaven.palace.moonpalace.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据库表字段属性
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@ApiModel(description = "生成字段实体")
public class GenFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否为主键
     */
    @ApiModelProperty(notes = "是否为主键")
    private Integer isKey;
    /**
     * 数据库字段名称
     */
    @ApiModelProperty(notes = "数据库字段名称")
    private String fieldName;
    /**
     * 字段类型
     */
    @ApiModelProperty(notes = "字段类型")
    private String type;
    /**
     * 字段名称
     */
    @ApiModelProperty(notes = "字段名称")
    private String name;
    /**
     * 字段中文名称
     */
    @ApiModelProperty(notes = "字段中文名称")
    private String chinaName;
    /**
     * 数据库字段注释
     */
    @ApiModelProperty(notes = "数据库字段注释")
    private String comment;
    /**
     * 数据库字段类型
     */
    @ApiModelProperty(notes = "数据库字段类型")
    private String fieldType;
    /**
     * 是否显示新增
     */
    @ApiModelProperty(notes = "是否显示新增")
    private Integer isShowAdd;

    /**
     * 是否显示编辑
     */
    @ApiModelProperty(notes = "是否显示编辑")
    private Integer isShowEdit;

    /**
     * 是否显示详情
     */
    @ApiModelProperty(notes = "是否显示详情")
    private Integer isShowDetail;

    /**
     * 是否列表显示
     */
    @ApiModelProperty(notes = "是否列表显示")
    private Integer isShowList;

    /**
     * 是否Excel导入
     */
    @ApiModelProperty(notes = "是否Excel导入")
    private Integer isImport;

    /**
     * 是否导出Excel
     */
    @ApiModelProperty(notes = "是否导出Excel")
    private Integer isExport;

    /**
     * 是否查询
     */
    @ApiModelProperty(notes = "是否查询")
    private String isQuery;

    /**
     * 查询类型
     */
    @ApiModelProperty(notes = "查询类型")
    private String queryMode;

    /**
     * 显示类型
     */
    @ApiModelProperty(notes = "显示类型")
    private String showType;

    /**
     * 排序
     */
    @ApiModelProperty(notes = "排序")
    private Integer orderNum;

    /**
     * 字段名称
     */
    @ApiModelProperty(notes = "字段名称")
    private String dictName;

    /**
     * 字段类型 1 枚举 2 字段 3 列表
     */
    @ApiModelProperty(notes = "字段类型 1 枚举 2 字段 3 列表")
    private Integer dictType;

    /**
     * 前端校验
     */
    @ApiModelProperty(notes = "前端校验")
    private Integer viewVerification;

    /**
     * 后台校验
     */
    @ApiModelProperty(notes = "后台校验")
    private Integer serverVerification;

    /**
     * 允许空
     */
    @ApiModelProperty(notes = "允许空")
    private Integer notNull;

    /**
     * 最小
     */
    @ApiModelProperty(notes = "最小")
    private String minNum;

    /**
     * 最大
     */
    @ApiModelProperty(notes = "最大")
    private String maxNum;

    /**
     * 正则
     */
    @ApiModelProperty(notes = "正则")
    private String regex;

    /**
     * 0 自定义 1 -邮箱 2 手机 3- 电话 4-身份证
     */
    @ApiModelProperty(notes = "0 自定义 1 -邮箱 2 手机 3- 电话 4-身份证")
    private Integer regexType;

    /**
     * 字段默认值
     */
    @ApiModelProperty(notes = "字段默认值")
    private String fieldDefault;

    /**
     * 字段注释
     */
    @ApiModelProperty(notes = "字段注释")
    private String fieldContent;

    /**
     * 字段长度
     */
    @ApiModelProperty(notes = "字段长度")
    private BigDecimal fieldLength;

    /**
     * 小数点位数
     */
    @ApiModelProperty(notes = "小数点位数")
    private Integer fieldPointLength;
    /**
     * 对象
     */
    @ApiModelProperty(notes = "对象")
    private GenBeanEntity bean;

    public Integer getKey() {
        return isKey;
    }

    public void setKey(Integer key) {
        isKey = key;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }

    public Integer getIsKey() {
        return isKey;
    }

    public void setIsKey(Integer isKey) {
        this.isKey = isKey;
    }

    public Integer getIsShowAdd() {
        return isShowAdd;
    }

    public void setIsShowAdd(Integer isShowAdd) {
        this.isShowAdd = isShowAdd;
    }

    public Integer getIsShowEdit() {
        return isShowEdit;
    }

    public void setIsShowEdit(Integer isShowEdit) {
        this.isShowEdit = isShowEdit;
    }

    public Integer getIsShowDetail() {
        return isShowDetail;
    }

    public void setIsShowDetail(Integer isShowDetail) {
        this.isShowDetail = isShowDetail;
    }

    public Integer getIsShowList() {
        return isShowList;
    }

    public void setIsShowList(Integer isShowList) {
        this.isShowList = isShowList;
    }

    public Integer getIsImport() {
        return isImport;
    }

    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
    }

    public Integer getIsExport() {
        return isExport;
    }

    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    public String getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(String isQuery) {
        this.isQuery = isQuery;
    }

    public String getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Integer getDictType() {
        return dictType;
    }

    public void setDictType(Integer dictType) {
        this.dictType = dictType;
    }

    public Integer getViewVerification() {
        return viewVerification;
    }

    public void setViewVerification(Integer viewVerification) {
        this.viewVerification = viewVerification;
    }

    public Integer getServerVerification() {
        return serverVerification;
    }

    public void setServerVerification(Integer serverVerification) {
        this.serverVerification = serverVerification;
    }

    public Integer getNotNull() {
        return notNull;
    }

    public void setNotNull(Integer notNull) {
        this.notNull = notNull;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Integer getRegexType() {
        return regexType;
    }

    public void setRegexType(Integer regexType) {
        this.regexType = regexType;
    }

    public String getFieldDefault() {
        return fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent;
    }

    public BigDecimal getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(BigDecimal fieldLength) {
        this.fieldLength = fieldLength;
    }

    public Integer getFieldPointLength() {
        return fieldPointLength;
    }

    public void setFieldPointLength(Integer fieldPointLength) {
        this.fieldPointLength = fieldPointLength;
    }

    public GenBeanEntity getBean() {
        return bean;
    }

    public void setBean(GenBeanEntity bean) {
        this.bean = bean;
    }

}
