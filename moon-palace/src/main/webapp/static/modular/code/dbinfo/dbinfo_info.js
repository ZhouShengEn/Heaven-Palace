/**
 * 初始化数据库管理详情对话框
 */
var DbInfoInfoDlg = {
    DbInfoInfoData : {},
    validateFields: {
        alias: {
            validators: {
                notEmpty: {
                    message: '别名不能为空'
                }
            }
        },
        dbUrl: {
            validators: {
                notEmpty: {
                    message: '链接不能为空'
                }
            }
        },
        dbPassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                }
            }
        },
        dbUserName: {
            validators: {
                notEmpty: {
                    message: '用户名名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
DbInfoInfoDlg.clearData = function() {
    this.DbInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DbInfoInfoDlg.set = function(key, val) {
    this.DbInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DbInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DbInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.DbInfo.layerIndex);
}

/**
 * 收集数据
 */
DbInfoInfoDlg.collectData = function() {
    this.set('id').set('alias').set('dbType').set('dbUrl').set('dbUserName').set('dbPassword');;
}

/**
 * 验证数据是否为空
 */
DbInfoInfoDlg.validate = function () {
    $('#dbinfoForm').data("bootstrapValidator").resetForm();
    $('#dbinfoForm').bootstrapValidator('validate');
    return $("#dbinfoForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加
 */
DbInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dbinfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.DbInfo.table.refresh();
        DbInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.DbInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DbInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dbinfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.DbInfo.table.refresh();
        DbInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.DbInfoInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("dbinfoForm", DbInfoInfoDlg.validateFields);
});
