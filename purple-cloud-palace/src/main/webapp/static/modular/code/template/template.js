/**
 * 模板管理管理初始化
 */
var Template = {
    id: "TemplateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Template.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
        {title: '模板名称', field: 'templateName', align: 'center', valign: 'middle'},
        {title: '模板类型', field: 'templateType', align: 'center', valign: 'middle'},
        {title: '所属组', field: 'groupId', align: 'center', valign: 'middle'},
        {title: '模板描述', field: 'templateDesc', align: 'center', valign: 'middle'},
        {title: '模板路径', field: 'templatePath', align: 'center', valign: 'middle'},
        {title: '本地路径', field: 'localPath', align: 'center', valign: 'middle'},
        {title: '文件名称', field: 'fileName', align: 'center', valign: 'middle'},
        {title: '创建人', field: 'crtUserName', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'crtTime', align: 'center', valign: 'middle'},
        {title: '修改人', field: 'mdfUserName', align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'mdfTime', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Template.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Template.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加模板管理
 */
Template.openAddTemplate = function () {
    var index = layer.open({
                               type: 2,
                               title: '添加模板管理',
                               area: ['100%', '100%'],
                               fix: false, //不固定
                               maxmin: true,
                               content: Feng.ctxPath + '/template/goto_add'
                           });
    this.layerIndex = index;
};

/**
 * 打开查看模板管理详情
 */
Template.openTemplateDetail = function () {
    if (this.check()) {
        var index = layer.open({
                                   type: 2,
                                   title: '模板管理编辑',
                                   area: ['100%', '100%'],//宽高
                                   fix: false, //不固定
                                   maxmin: true,
                                   content: Feng.ctxPath + '/template/goto_update/'
                                            + Template.seItem.id
                               });
        this.layerIndex = index;
    }
};

/**
 * 删除模板管理
 */
Template.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/template/delete", function (data) {
            Feng.success("删除成功!");
            Template.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", this.seItem.id);
        ajax.start();
    }
};

Template.formParams = function () {
    var queryData = {};
    return queryData;
};

/**
 * 查询模板管理列表
 */
Template.search = function () {
    var queryData = {};
    queryData['templateName'] = $("#templateName").val();
    queryData['templateType'] = $("#templateType").val();
    queryData['groupId'] = $("#groupId").val();
    Template.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Template.initColumn();
    var table = new BSTable(Template.id, "/template/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Template.formParams());
    Template.table = table.init();
});
