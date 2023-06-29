package com.heaven.palace.moonpalace.modular.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "查询环境-出参")
public class QueryEnvResVO implements Serializable {
    private static final long serialVersionUID = 3946090876633197203L;

    @ApiModelProperty(notes = "环境列表")
    private List<SimpleDbInfo> dbs;

    @ApiModelProperty(notes = "当前环境")
    private SimpleDbInfo currentDb;

    @ApiModelProperty(notes = "环境标签列表")
    private List<String> dbTags;

    @Data
    @ApiModel(description = "数据源")
    public static class SimpleDbInfo implements Serializable{

        private static final long serialVersionUID = 1653317543007169173L;

        @ApiModelProperty("db_info_id")
        private Integer id;

        @ApiModelProperty("别名")
        private String alias;

        @ApiModelProperty("标签")
        private String tag;
    }


}
