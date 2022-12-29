/**
 * Copyright 2022-8-25
 *
 * Licensed under the Apache License; Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software distributed under the License
 * is distributed on an "AS IS" BASIS; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.heaven.palace.purplecloudpalace.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZhouShengEn
 * 2022年8月25日
 */
public class CodeGenModel {

    //数据库类型
    private String dbType;
    //数据库地址
    private String url;
    //数据库名称
    private String dbName;
    //数据库连接用户
    private String username;
    //数据库密码
    private String passwd;
    //表名称
    private String tableName;
    //模板类型
    private String parseType;
    //生成的参数
    private GenerationEntity generationEntity;
    //文件列表
    private List<String> fileList;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getParseType() {
        return parseType;
    }

    public void setParseType(String parseType) {
        this.parseType = parseType;
    }

    public GenerationEntity getGenerationEntity() {
        return generationEntity;
    }

    public void setGenerationEntity(GenerationEntity generationEntity) {
        this.generationEntity = generationEntity;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public void setFile(String file) {
        this.fileList = new ArrayList<String>();
        this.fileList.add(file);
    }

}
