package com.heaven.palace.purplecloudpalace.modular.code.service.impl.convert;

import com.heaven.palace.purplecloudpalace.model.GenBeanEntity;
import com.heaven.palace.purplecloudpalace.model.GenFieldEntity;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


import com.heaven.palace.purplecloudpalace.modular.code.model.TableFieldDbinfoModel;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableFieldModel;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableFieldVerifyModel;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableInfoModel;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableServiceConfigModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITableConvertServer;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITableInfoService;

/**
 * 导入的数据库版本实现
 *
 * @author ZhouShengEn on 2017/10/25.
 */
@Service("dbTableConvertServer")
public class TableConvertServiceOfDbImpl implements ITableConvertServer {

    @Autowired
    private ITableInfoService tableInfoService;

    @Override
    public void importBean(String json, int userId) {
        GenBeanEntity bean = JSON.parseObject(json, GenBeanEntity.class);
        TableInfoModel entity = new TableInfoModel();
        entity.setClassName(bean.getName());
        if (StringUtils.isEmpty(bean.getChinaName())) {
            entity.setContent(bean.getTableName());
        } else {
            entity.setContent(bean.getChinaName());
        }
        entity.setTableName(bean.getTableName());
        entity.setUserId(userId);
        entity.setServiceConfig(getDefaultServceConfig());
        entity.setTableFields(getTableFields(bean.getFields()));
        tableInfoService.insert(entity);
    }

    private List<TableFieldModel> getTableFields(List<GenFieldEntity> fields) {
        List<TableFieldModel> list = new ArrayList<TableFieldModel>();
        TableFieldModel fieldModel;
        GenFieldEntity tableFiedl;
        TableFieldVerifyModel verifyModel;
        TableFieldDbinfoModel dbinfoModel;
        for (int i = 0; i < fields.size(); i++) {
            fieldModel = new TableFieldModel();
            tableFiedl = fields.get(i);
            fieldModel.setFieldName(tableFiedl.getFieldName());
            fieldModel.setName(tableFiedl.getName());
            fieldModel.setContent(tableFiedl.getChinaName());
            fieldModel.setType(tableFiedl.getType());
            fieldModel.setIsKey(tableFiedl.getKey());
            verifyModel = new TableFieldVerifyModel();
            verifyModel.setNotNull(tableFiedl.getNotNull());
            fieldModel.setVerifyModel(verifyModel);
            dbinfoModel = new TableFieldDbinfoModel();
            dbinfoModel.setFieldName(tableFiedl.getFieldName());
            dbinfoModel.setFieldContent(tableFiedl.getComment());
            dbinfoModel.setFieldLength(tableFiedl.getFieldLength());
            dbinfoModel.setFieldPointLength(tableFiedl.getFieldPointLength());
            dbinfoModel.setFieldType(tableFiedl.getFieldType());
            fieldModel.setDbinfoModel(dbinfoModel);
            list.add(fieldModel);
        }
        return list;
    }

    private List<TableServiceConfigModel> getDefaultServceConfig() {
        List<TableServiceConfigModel> list = new ArrayList<TableServiceConfigModel>();
        list.add(new TableServiceConfigModel("list", 1, 1, 2, "02"));
        list.add(new TableServiceConfigModel("add", 1, 1, 1, "01"));
        list.add(new TableServiceConfigModel("edit", 1, 1, 1, "01"));
        list.add(new TableServiceConfigModel("delete", 1, 1, 1, "01"));
        list.add(new TableServiceConfigModel("detail", 1, 1, 2, "02"));
        return list;
    }
}
