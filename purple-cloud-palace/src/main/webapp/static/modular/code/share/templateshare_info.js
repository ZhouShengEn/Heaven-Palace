/**
 * 初始化模板分享管理详情对话框
 */
var TemplateShareInfoDlg = {
    TemplateShareInfoData : {}
};

/**
 * 清除数据
 */
TemplateShareInfoDlg.clearData = function() {
    this.TemplateShareInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TemplateShareInfoDlg.set = function(key, val) {
    this.TemplateShareInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TemplateShareInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TemplateShareInfoDlg.close = function() {
    parent.layer.close(window.parent.TemplateShare.layerIndex);
}

/**
 * 收集数据
 */
TemplateShareInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
TemplateShareInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TemplateShare/add", function(data){
        Feng.success("添加成功!");
        window.parent.TemplateShare.table.refresh();
        TemplateShareInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TemplateShareInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TemplateShareInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TemplateShare/update", function(data){
        Feng.success("修改成功!");
        window.parent.TemplateShare.table.refresh();
        TemplateShareInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TemplateShareInfoData);
    ajax.start();
}

$(function() {

});
