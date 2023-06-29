/**
 * 初始化详情对话框
 */
var TableFieldVerifyInfoDlg = {
    TableFieldVerifyInfoData : {}
};

/**
 * 清除数据
 */
TableFieldVerifyInfoDlg.clearData = function() {
    this.TableFieldVerifyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableFieldVerifyInfoDlg.set = function(key, val) {
    this.TableFieldVerifyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableFieldVerifyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TableFieldVerifyInfoDlg.close = function() {
    parent.layer.close(window.parent.TableFieldVerify.layerIndex);
}

/**
 * 收集数据
 */
TableFieldVerifyInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
TableFieldVerifyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TableFieldVerify/add", function(data){
        Feng.success("添加成功!");
        window.parent.TableFieldVerify.table.refresh();
        TableFieldVerifyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableFieldVerifyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TableFieldVerifyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TableFieldVerify/update", function(data){
        Feng.success("修改成功!");
        window.parent.TableFieldVerify.table.refresh();
        TableFieldVerifyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableFieldVerifyInfoData);
    ajax.start();
}

$(function() {

});
