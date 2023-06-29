/**
 * 初始化字典详情对话框
 */
var DictItemDlg = {
    count: $("#itemSize").val(),
    dictName: '',			//字典的名称
    mutiString: '',		//拼接字符串内容(拼接字典条目)
    itemTemplate: $("#itemTemplate").html(),
    ztreeInstance: null,
    dictItemInfoData : {},
    validateFields: {
        dictSort: {
            validators: {
                notEmpty: {
                    message: '序号不能为空'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                }
            }
        },
        value: {
            validators: {
                notEmpty: {
                    message: '值不能为空'
                }
            }
        },
        text: {
            validators: {
                notEmpty: {
                    message: '展示名不能为空'
                }
            }
        },
        typeId: {
            validators: {
                notEmpty: {
                    message: '字典类型不能为空'
                }
            }
        }
    }
};


/**
 * 关闭此对话框
 */
DictItemDlg.close = function () {
    parent.layer.close(window.parent.DictItem.layerIndex);
};




/**
 * 提交添加字典
 */
DictItemDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/custom/dict/item/add", function (data) {
        Feng.success("添加成功!");
        window.parent.DictItem.table.refresh();
        window.parent.DictItem.reloadLog();
        DictItemDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dictItemInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DictItemDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/custom/dict/item/update", function (data) {
        Feng.success("修改成功!");
        window.parent.DictItem.table.refresh();
        window.parent.DictItem.reloadLog();
        DictItemDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dictItemInfoData);
    ajax.start();
};

/**
 * 验证数据是否为空
 */
DictItemDlg.validate = function () {
    $('#dictItemForm').data("bootstrapValidator").resetForm();
    $('#dictItemForm').bootstrapValidator('validate');
    return $("#dictItemForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
DictItemDlg.clearData = function() {
    this.dictItemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DictItemDlg.set = function(key, val) {
    this.dictItemInfoData[key] = $("#" + key).val();
    return this;
}

/**
 * 收集数据
 */
DictItemDlg.collectData = function() {
    this.set('id').set('dictSort').set('name').set('value').set('text').set('parentId').set('level').set('typeId');
}

/**
 * 点击字典类型input框时
 */
DictItemDlg.onClickDept = function (e, treeId, treeNode) {
    $("#typeText").attr("value", DictItemDlg.ztreeInstance.getSelectedVal());
    $("#typeId").attr("value", treeNode.id);
};

/**
 * 显示父级菜单选择的树
 */
DictItemDlg.showMenuSelectTree = function () {
    Feng.showInputTree("typeText", "dictTypeTreeDiv", 15, 34);
};

$(function () {
    Feng.initValidator("dictItemForm", DictItemDlg.validateFields);

    var ztree = new $ZTree("dictTypeTree", "/custom/dict/type/tree/");
    ztree.bindOnClick(DictItemDlg.onClickDept);
    ztree.init();
    DictItemDlg.ztreeInstance = ztree;
    
});
