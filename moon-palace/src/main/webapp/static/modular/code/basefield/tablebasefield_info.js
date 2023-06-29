/**
 * 初始化详情对话框
 */
var TableBaseFieldInfoDlg = {
    TableBaseFieldInfoData : {}
};

/**
 * 清除数据
 */
TableBaseFieldInfoDlg.clearData = function() {
    this.TableBaseFieldInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableBaseFieldInfoDlg.set = function(key, val) {
    this.TableBaseFieldInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableBaseFieldInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TableBaseFieldInfoDlg.close = function() {
    parent.layer.close(window.parent.TableBaseField.layerIndex);
}

/**
 * 收集数据
 */
TableBaseFieldInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
TableBaseFieldInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TableBaseField/add", function(data){
        Feng.success("添加成功!");
        window.parent.TableBaseField.table.refresh();
        TableBaseFieldInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableBaseFieldInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TableBaseFieldInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TableBaseField/update", function(data){
        Feng.success("修改成功!");
        window.parent.TableBaseField.table.refresh();
        TableBaseFieldInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableBaseFieldInfoData);
    ajax.start();
}

$(function() {

});
