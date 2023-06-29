/**
 * 模板分享管理管理初始化
 */
var TemplateShare = {
    id: "TemplateShareTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TemplateShare.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
	     {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
	     {title: '模板名称', field: 'templateName', align: 'center', valign: 'middle'},
	     {title: '模板地址', field: 'templatePath', align: 'center', valign: 'middle'},
	     {title: '模板效果', field: 'templateEffect', align: 'center', valign: 'middle'},
	     {title: 'TemplateDesc', field: 'templateDesc', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
TemplateShare.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TemplateShare.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加模板分享管理
 */
TemplateShare.openAddTemplateShare = function () {
    var index = layer.open({
        type: 2,
        title: '添加模板分享管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/templateshare/goto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看模板分享管理详情
 */
TemplateShare.openTemplateShareDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '模板分享管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/templateshare/goto_update/' + TemplateShare.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除模板分享管理
 */
TemplateShare.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/templateshare/delete", function (data) {
            Feng.success("删除成功!");
            TemplateShare.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("templateshareId",this.seItem.id);
        ajax.start();
    }
};

TemplateShare.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 查询模板分享管理列表
 */
TemplateShare.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TemplateShare.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TemplateShare.initColumn();
    var table = new BSTable(TemplateShare.id, "/templateshare/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TemplateShare.formParams());
    TemplateShare.table = table.init();
});
