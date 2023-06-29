var GEN = {
    dbbtn: '<button type="button" class="list-group-item" value="dbId" onclick="GEN.getDataBases(\'dbId\')">dbName</button>',
    tablesBtn: '<button type="button" class="list-group-item" value="tableName" onclick="GEN.selectTables(\'tableName\',\'chinaName\')">tableName-chinaName</button>',
    databasebtn: '<button type="button" class="list-group-item" value="name" onclick="GEN.getTables(\'name\')">name</button>',
    templatebtn: '<button type="button" class="list-group-item" value="id" onclick="GEN.selectTemplates(\'id\')">templateName</button>',
    param: {},
    validateFields: {
        className: {
            validators: {
                notEmpty: {
                    message: '类名不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 3,
                    max: 30,
                    message: '类名长度必须在3到30之间'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '功能不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 3,
                    max: 30,
                    message: '功能长度必须在3到30之间'
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
                    regexp: /^[a-zA-Z0-9_\-\.]+$/,
                    message: '只能是数字和字母和-_.'
                },
                stringLength: {/*长度提示*/
                    min: 1,
                    max: 100,
                    message: '代码目录长度必须在1到100之间'
                }
            }
        },
        htmlPackage: {
            validators: {
                regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                    regexp: /^[a-zA-Z0-9_\-\.]+$/,
                    message: '只能是数字和字母和-_.'
                },
                stringLength: {/*长度提示*/
                    min: 0,
                    max: 100,
                    message: 'Html目录长度必须在1到100之间'
                }
            }
        },
        jsPackage: {
            validators: {
                regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                    regexp: /^[a-zA-Z0-9_\-\.]+$/,
                    message: '只能是数字和字母和-_.'
                },
                stringLength: {/*长度提示*/
                    min: 0,
                    max: 100,
                    message: 'Js目录长度必须在1到100之间'
                }
            }
        },
        xmlPackage: {
            validators: {
                regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                    regexp: /^[a-zA-Z0-9_\-\.]+$/,
                    message: '只能是数字和字母和-_.'
                },
                stringLength: {/*长度提示*/
                    min: 0,
                    max: 100,
                    message: 'Xml目录长度必须在1到100之间'
                }
            }
        }
    }
}
// 查询按钮
GEN.getData = function (url, p) {
    var success = function (data) {
        if ('dbconnects' == p) {
            var html = '';
            $(data.rows).each(function (i, d) {
                html +=
                    GEN.dbbtn.replace("dbName", d.alias).replace("dbId", d.id)
                        .replace("dbId", d.id);
            });
            $("#dblinklist").html(html);
        } else if ('databases' == p) {
            var html = '';
            $(data.result).each(function (i, d) {
                html +=
                    GEN.databasebtn.replace("name", d.name).replace("name", d.name)
                        .replace("name", d.name);
            });
            $("#databaseslist").html(html);
        } else if ('tables' == p) {
            var html = '';
            $(data.result).each(function (i, d) {
                html +=
                    GEN.tablesBtn.replace("tableName", d.tableName)
                        .replace("tableName", d.tableName)
                        .replace("tableName", d.tableName)
                        .replace("chinaName", d.chinaName)
                        .replace("chinaName", d.chinaName);
            });
            $("#tableslist").html(html);
        } else if ('templates' == p) {
            var html = '';
            $(data.rows).each(function (i, d) {
                html += GEN.templatebtn.replace("id", d.id).replace("id", d.id)
                    .replace("templateName", d.templateName);
            });
            $("#templatesList").html(html);
        }
    };
    var ajax = new $ax(Feng.ctxPath + url, success);
    ajax.setData(GEN.param);
    ajax.start();
};

GEN.queryData = function () {
    GEN.getData();
};

GEN.getDataBases = function (id) {
    GEN.param.id = id;
    GEN.selectChange(id, 'dblinklist');
    $("#databaseslist").html('');
    $("#tableslist").html('');
    GEN.param.dbName = null;
    GEN.param.tableName = null;
    GEN.getData('/code/queryDatabses', 'databases');
    GEN.genBtnAble();
}

GEN.getTables = function (name) {
    GEN.param.dbName = name;
    GEN.selectChange(name, 'databaseslist');
    $("#tableslist").html('');
    GEN.param.tableName = null;
    GEN.getData('/code/queryTables', 'tables');
    GEN.genBtnAble();
}

GEN.selectTables = function (tableName,chinaName) {
    GEN.param.tableName = tableName;
    GEN.selectChange(tableName, 'tableslist');
    tableName = tableName.toLocaleLowerCase();
    var index = 0;
    var humpName = '';
    while ((index = tableName.indexOf("_")) != -1){
        humpName += tableName.substring(0, index);
        tableName = tableName.substring(index + 1, index + 2).toUpperCase() + tableName.substring(index + 2);
    }
    humpName += tableName;
    humpName = humpName.substring(0, 1).toUpperCase() + humpName.substring(1);

    $("#className").val(humpName);
    $("#name").val(chinaName);
    GEN.genBtnAble();

}

GEN.selectTemplates = function (id) {
    if (!GEN.param.templates) {
        GEN.param.templates = [];
    }
    if ($.inArray(id, GEN.param.templates) > -1) {
        GEN.param.templates.splice($.inArray(id, GEN.param.templates), 1);
        $("#templatesList").find("button").each(function (i, d) {
            if ($(this).val() == id) {
                $(this).removeClass('active');
            }
        });
    } else {
        $("#templatesList").find("button").each(function (i, d) {
            if ($(this).val() == id) {
                $(this).addClass('active');
            }
        });
        GEN.param.templates.push(id);
    }
    GEN.genBtnAble();

}

/**
 * 验证数据
 */
GEN.validate = function () {
    $('#genForm').data("bootstrapValidator").resetForm();
    $('#genForm').bootstrapValidator('validate');
    return $("#genForm").data('bootstrapValidator').isValid();
};

GEN.selectChange = function (data, eleName) {
    $("#" + eleName).find("button").each(function (i, d) {
        if ($(this).val() == data) {
            $(this).addClass('active');
        } else {
            $(this).removeClass('active');
        }
    });
}

GEN.genBtnAble = function () {
    if($("#tableId").val() && $("#tableId").val() != ''){
        if(GEN.param.templates && GEN.param.templates.length >0){
            $("#genBtn").removeAttr("disabled");
        }else{
            $("#genBtn").attr("disabled","disabled");
        }
    }else {
        if(GEN.param.tableName && GEN.param.templates && GEN.param.templates.length >0){
            $("#genBtn").removeAttr("disabled");
        }else{
            $("#genBtn").attr("disabled","disabled");
        }
    }
}

GEN.genCode = function () {
    if (!this.validate()) {
        return;
    }
    GEN.param.author = $("#author").val();
    GEN.param.codePackage = $("#codePackage").val();
    GEN.param.htmlPackage = $("#htmlPackage").val();
    GEN.param.jsPackage = $("#jsPackage").val();
    GEN.param.entityName = $("#className").val();
    GEN.param.xmlPackage = $("#xmlPackage").val();
    GEN.param.localPath = $("#localPath").val();
    GEN.param.encoded = $("#encoded").val();
    GEN.param.ipAddress = $("#ipAddress").val();
    GEN.param.sftpAccount = $("#sftpAccount").val();
    GEN.param.sftpPassword = $("#sftpPassword").val();
    //本地生产就ajax访问后台就可以了
    if(GEN.param.localPath){
        if(GEN.param.ipAddress && GEN.param.sftpAccount && GEN.param.sftpPassword){
            var ajax = new $ax(Feng.ctxPath + '/code/genCode?' + $.param(GEN.param), function (data){
                if (data.code === 200){
                    Feng.success("code已生成!");
                }else {
                    Feng.error(data.message);
                }
                
            }, function (data) {
                Feng.error("生成失败!");
            });
            ajax.start();
            
        }else {
            Feng.error("需要代码生成在本地路径时请填写ip地址、sftp账户、sftp密码信息！");
        }
        
        
    }else{
        window.location.href = '/code/genCode?' + $.param(GEN.param);
    }

}

GEN.genTableCode = function () {
    if (!this.validate()) {
        return;
    }
    GEN.param.tableId = $("#tableId").val();
    GEN.param.codePackage = $("#codePackage").val();
    GEN.param.htmlPackage = $("#htmlPackage").val();
    GEN.param.jsPackage = $("#jsPackage").val();
    GEN.param.entityName = $("#className").val();
    GEN.param.xmlPackage = $("#xmlPackage").val();
    GEN.param.localPath = $("#localPath").val();
    GEN.param.encoded = $("#encoded").val();
    GEN.param.ipAddress = $("#ipAddress").val();
    GEN.param.sftpAccount = $("#sftpAccount").val();
    GEN.param.sftpPassword = $("#sftpPassword").val();
    //本地生产就ajax访问后台就可以了
    if(GEN.param.localPath){
        if(GEN.param.ipAddress && GEN.param.sftpAccount && GEN.param.sftpPassword){
            var ajax = new $ax(Feng.ctxPath + '/code/genCode?' + $.param(GEN.param), function (data){
                if (data.code === 200){
                    Feng.success("code已生成!");
                }else {
                    Feng.error(data.message);
                }

            }, function (data) {
                Feng.error("生成失败!");
            });
            ajax.start();

        }else {
            Feng.error("需要代码生成在本地路径时请填写ip地址、sftp账户、sftp密码信息！");
        }
    }else{
        window.location.href = '/code/genTableCode?' + $.param(GEN.param);
    }

}

// 页面初始化
$(function () {
    GEN.param.limit = 100;
    GEN.param.offset = 0;
    GEN.getData('/dbinfo/queryAll', 'dbconnects');
    GEN.getData('/template/list', 'templates');
    Feng.initValidator("genForm", GEN.validateFields);
});

$("#params").change(function(){
    var ajax = new $ax(Feng.ctxPath + '/genparam/detail', function (data) {
        $("#author").val(data.author);
        $("#codePackage").val(data.codePackage);
        $("#htmlPackage").val(data.htmlPackage);
        $("#jsPackage").val(data.jsPackage);
        $("#xmlPackage").val(data.xmlPackage);
        $("#localPath").val(data.localPath);
        $("#encoded").val(data.encoded);
        $("#ipAddress").val(data.ipAddress);
        $("#sftpAccount").val(data.sftpAccount);
        $("#sftpPassword").val(data.sftpPassword);
    });
    ajax.set('id',$("#params").val());
    ajax.start();
});

$("#groupId").change(function(){
    var p = GEN.param;
    GEN.param = {};
    GEN.param.limit = 100;
    GEN.param.offset = 0;
    GEN.getData(Feng.ctxPath + '/template/list?groupId='+$("#groupId").val(), 'templates');
    GEN.param = p;
});