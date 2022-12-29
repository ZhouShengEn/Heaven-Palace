/**
 * 初始化详情对话框
 */
var TableInfoInfoDlg = {
    TableInfoInfoData: {},
    validateFields: {
        tableName: {
            validators: {
                notEmpty: {
                    message: '表名不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 3,
                    max: 50,
                    message: '别名长度必须在3到50之间'
                },
                regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                    regexp: /^[a-zA-Z0-9\_]+$/,
                    message: '只能是数字和字母和_'
                }
            }
        },
        className: {
            validators: {
                notEmpty: {
                    message: '类名不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 3,
                    max: 50,
                    message: '用户名长度必须在3到50之间'
                },
                regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                    regexp: /^[a-zA-Z0-9\_]+$/,
                    message: '只能是数字和字母和_'
                }
            }
        },
        content: {
            validators: {
                notEmpty: {
                    message: '功能不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 1,
                    max: 100,
                    message: '功能长度必须在1到100之间'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TableInfoInfoDlg.clearData = function () {
    this.TableInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableInfoInfoDlg.set = function (key, val) {
    this.TableInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableInfoInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TableInfoInfoDlg.close = function () {
    parent.layer.close(window.parent.TableInfo.layerIndex);
}

/**
 * 收集数据
 */
TableInfoInfoDlg.collectData = function () {
    //基础表单数据
    this.set('id').set("tableName").set("className").set("content")
        .set("isImport").set("isExport").set("isPagination").set("isLog");
    var serviceConfig = [];
    //配置数据
    $("#serviceConfigTable").find('tbody').find('tr').each(function () {
        var obj = new Object();
        $(this).find('td').each(function (index, data) {
            if (index == 0) {
                obj.type = $(data).attr('type');
            } else {
                obj[$(data).attr('data-field')] = $(data).find('select').val();
            }
        });
        serviceConfig.push(obj);

    });
    this.TableInfoInfoData.serviceConfig = serviceConfig;
    //字段信息
    var tableField = [];
    $("#tableField").find('tbody').find('tr').each(function () {
        var obj = new Object();
        $(this).find('td').each(function (index, data) {
            if (index > 0) {
                obj[$(data).attr('data-field')] = $(data).find('select,input[type="text"],input[type="checkbox"]:checked').val();
            }
        });
        TableInfoInfoDlg.getViewData(obj,this);
        obj.verifyModel = TableInfoInfoDlg.getVerifyModel(this);
        obj.dbinfoModel = TableInfoInfoDlg.getDbinfoModel(this);
        tableField.push(obj);

    });
    this.TableInfoInfoData.tableFields = tableField;
}

/**
 * 获取校验字段
 * @param param
 */
TableInfoInfoDlg.getVerifyModel = function (tr) {
    var obj = new Object();
    var trId = '#fieldNav' + $(tr).attr("id").replace('fieldTr', '');
    $(trId).find('.fieldVerify .row div').each(function (index, data) {
        obj[$(data).attr('data-field')] = $(data).find('select,input').val();
    });
    return obj;
};
/**
 * 数据库字段
 * @param param
 */
TableInfoInfoDlg.getDbinfoModel = function (tr) {
    var obj = new Object();
    var trId = '#fieldNav' + $(tr).attr("id").replace('fieldTr', '');
    $(trId).find('.fieldDbinfo .row div').each(function (index, data) {
        obj[$(data).find('select,input').attr('name')] = $(data).find('select,input').val();
    });
    return obj;
};

/**
 * 页面详情字段
 * @param obj
 * @param param2
 */
TableInfoInfoDlg.getViewData = function (obj, tr) {
    var trId = '#fieldNav' + $(tr).attr("id").replace('fieldTr', '');
    $(trId).find('.fieldShow .row div').each(function (index, data) {
        obj[$(data).attr('data-field')] = $(data).find('select,input').val();
    });
};

/**
 * 验证数据
 */
TableInfoInfoDlg.validate = function () {
    $('#tableNavContent').data("bootstrapValidator").resetForm();
    $('#tableNavContent').bootstrapValidator('validate');
    return $("#tableNavContent").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TableInfoInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var message = "添加成功!";
    var url = Feng.ctxPath + "/tableinfo/add";
    if($("#tableId").val() && $("#tableId").val() != ''){
        url = Feng.ctxPath + "/tableinfo/update";
        this.TableInfoInfoData.id = $("#tableId").val();
        message = "更新成功!";
    }

    //提交信息
    var ajax = new $ax(url, function (data) {
        Feng.success(message);
        window.parent.TableInfo.table.refresh();
        TableInfoInfoDlg.close();
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
    });
    ajax.setData(JSON.stringify(this.TableInfoInfoData));
    ajax.start();
}

/**
 * 提交修改
 */
TableInfoInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tableinfo/update", function (data) {
        Feng.success("修改成功!");
        window.parent.TableInfo.table.refresh();
        TableInfoInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableInfoInfoData);
    ajax.start();
}

$(function () {
    parent.layer.full(window.parent.TableInfo.layerIndex);
    Feng.initValidator("tableNavContent", TableInfoInfoDlg.validateFields);
});

$('#tableNav a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
});
