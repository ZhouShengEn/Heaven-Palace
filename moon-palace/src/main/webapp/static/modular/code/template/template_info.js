/**
 * 初始化模板管理详情对话框
 */
var TemplateInfoDlg = {
    TemplateInfoData : {}
};

/**
 * 清除数据
 */
TemplateInfoDlg.clearData = function() {
    this.TemplateInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TemplateInfoDlg.set = function(key, val) {
    this.TemplateInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TemplateInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TemplateInfoDlg.close = function() {
    parent.layer.close(window.parent.Template.layerIndex);
}

/**
 * 收集数据
 */
TemplateInfoDlg.collectData = function() {
    this.set('id').set('templateName').set('templatePath').set('templateDesc').set('fileName')
        .set('groupId').set('templateType').set('fileType').set('file').set('localPath');
}

/**
 * 提交添加
 */
TemplateInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/template/add", function(data){
        Feng.success("添加成功!");
        window.parent.Template.table.refresh();
        TemplateInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TemplateInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TemplateInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/template/update", function(data){
        Feng.success("修改成功!");
        window.parent.Template.table.refresh();
        TemplateInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TemplateInfoData);
    ajax.start();
}

$(function() {
    //hljs.initHighlightingOnLoad();
    $("#codeFile").find('code').html(hljs.highlight($("#fileType").val(),$("#file").val()).value);
});

$("#codeFile").click(function () {
    $(this).hide();
    $("#file").show();
});

$("#fileType").change(function () {
    $("#codeFile").find('code').html(hljs.highlight($("#fileType").val(),$("#file").val()).value);
});

$("#file").blur(function(){
    $(this).hide();
    $("#codeFile").find('code').html(hljs.highlight($("#fileType").val(),$(this).val()).value);
    $("#codeFile").show();
});