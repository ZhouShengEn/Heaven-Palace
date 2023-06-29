/**
 * 初始化生成参数详情对话框
 */
var GenParamInfoDlg = {
    GenParamInfoData : {},
    validateFields: {
        alias: {
            validators: {
                notEmpty: {
                    message: '别名不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 3,
                    max: 20,
                    message: '别名长度必须在3到20之间'
                }
            }
        },
        author: {
            validators: {
                notEmpty: {
                    message: '作者不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 3,
                    max: 20,
                    message: '用户名长度必须在3到20之间'
                }
            }
        },
        codePackage: {
            validators: {
                notEmpty: {
                    message: '代码目录不能为空'
                },
                regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                    regexp: /^[a-zA-Z0-9\.]+$/,
                    message: '只能是数字和字母和.'
                },
                stringLength: {/*长度提示*/
                    min: 1,
                    max: 100,
                    message: '代码目录长度必须在1到100之间'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
GenParamInfoDlg.clearData = function() {
    this.GenParamInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GenParamInfoDlg.set = function(key, val) {
    this.GenParamInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GenParamInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
GenParamInfoDlg.close = function() {
    parent.layer.close(window.parent.GenParam.layerIndex);
}

/**
 * 收集数据
 */
GenParamInfoDlg.collectData = function() {
    this.set('id').set("alias").set("author").set("codePackage").set("jsPackage").set("htmlPackage")
        .set("xmlPackage").set("localPath").set("encoded").set("ipAddress").set("sftpAccount").set("sftpPassword");
}

/**
 * 验证数据
 */
GenParamInfoDlg.validate = function () {
    $('#genParamsForm').data("bootstrapValidator").resetForm();
    $('#genParamsForm').bootstrapValidator('validate');
    return $("#genParamsForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
GenParamInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/genparam/add", function(data){
        Feng.success("添加成功!");
        window.parent.GenParam.table.refresh();
        GenParamInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.GenParamInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
GenParamInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/genparam/update", function(data){
        Feng.success("修改成功!");
        window.parent.GenParam.table.refresh();
        GenParamInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.GenParamInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("genParamsForm", GenParamInfoDlg.validateFields);
});
