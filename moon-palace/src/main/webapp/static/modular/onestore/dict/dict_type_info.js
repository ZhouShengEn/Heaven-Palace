/**
 * 初始化字典详情对话框
 */
var DictTypeInfoDlg = {
    count: $("#itemSize").val(),
    dictTypeInfoData : {},
    code: '',			//类型编码
    text: '',			//字典类型名称
    updateBy: '',			//修改人
    itemTemplate: $("#itemTemplate").html(),
    logList: {},
    validateFields: {
        code: {
            validators: {
                notEmpty: {
                    message: '类型编码不能为空'
                }
            }
        },
        text: {
            validators: {
                notEmpty: {
                    message: '字典类型名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
DictTypeInfoDlg.clearData = function() {
    this.dictTypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DictTypeInfoDlg.set = function(key, val) {
    this.dictTypeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 收集数据
 */
DictTypeInfoDlg.collectData = function() {
    this.set('id').set('code').set('text');
}

/**
 * item获取新的id
 */
DictTypeInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "dictItem" + this.count;
};

/**
 * 关闭此对话框
 */
DictTypeInfoDlg.close = function () {
    parent.layer.close(window.parent.DictType.layerIndex);
};



/**
 * 提交添加字典
 */
DictTypeInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/custom/dict/type/add", function (data) {
        Feng.success("添加成功!");
        window.parent.DictType.table.refresh();
        window.parent.DictType.reloadLog();
        DictTypeInfoDlg.close();

    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dictTypeInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DictTypeInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        console.log("返回！")
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/custom/dict/type/update", function (data) {
        Feng.success("修改成功!");
        window.parent.DictType.table.refresh();
        window.parent.DictType.reloadLog();
        DictTypeInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dictTypeInfoData);
    ajax.start();
};
/**
 * 验证数据是否为空
 */
DictTypeInfoDlg.validate = function () {
    $('#dictTypeInfoForm').data("bootstrapValidator").resetForm();
    $('#dictTypeInfoForm').bootstrapValidator('validate');
    return $("#dictTypeInfoForm").data('bootstrapValidator').isValid();
};

$(function() {
    Feng.initValidator("dictTypeInfoForm", DictTypeInfoDlg.validateFields);
});