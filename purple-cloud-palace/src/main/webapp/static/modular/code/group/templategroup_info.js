/**
 * 初始化详情对话框
 */
var TemplateGroupInfoDlg = {
    TemplateGroupInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                }
            }
        },
        desc: {
            validators: {
                notEmpty: {
                    message: '描述不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TemplateGroupInfoDlg.clearData = function() {
    this.TemplateGroupInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TemplateGroupInfoDlg.set = function(key, val) {
    this.TemplateGroupInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TemplateGroupInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TemplateGroupInfoDlg.close = function() {
    parent.layer.close(window.parent.TemplateGroup.layerIndex);
}

/**
 * 收集数据
 */
TemplateGroupInfoDlg.collectData = function() {
    this.set('id').set("name").set("desc");
}

/**
 * 验证数据是否为空
 */
TemplateGroupInfoDlg.validate = function () {
    $('#templategroupForm').data("bootstrapValidator").resetForm();
    $('#templategroupForm').bootstrapValidator('validate');
    return $("#templategroupForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TemplateGroupInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/templategroup/add", function(data){
        Feng.success("添加成功!");
        window.parent.TemplateGroup.table.refresh();
        TemplateGroupInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TemplateGroupInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TemplateGroupInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/templategroup/update", function(data){
        Feng.success("修改成功!");
        window.parent.TemplateGroup.table.refresh();
        TemplateGroupInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TemplateGroupInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("templategroupForm", TemplateGroupInfoDlg.validateFields);
});
