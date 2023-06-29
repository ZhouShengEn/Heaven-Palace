/**
 * 生成参数管理初始化
 */
var GenParam = {
    id: "GenParamTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
GenParam.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
	     {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
	     {title: '别名', field: 'alias', align: 'center', valign: 'middle'},
	     {title: '作者', field: 'author', align: 'center', valign: 'middle'},
         {title: 'ip地址', field: 'ipAddress', align: 'center', valign: 'middle'},
         {title: 'sftp账户', field: 'sftpAccount', align: 'center', valign: 'middle'},
         {title: 'sftp密码', field: 'sftpPassword', align: 'center', valign: 'middle'},
	     {title: 'CODE 包', field: 'codePackage', align: 'center', valign: 'middle'},
	     {title: 'JS 目录', field: 'jsPackage', align: 'center', valign: 'middle'},
	     {title: 'HTML 目录', field: 'htmlPackage', align: 'center', valign: 'middle'},
	     {title: 'XML 目录', field: 'xmlPackage', align: 'center', valign: 'middle'},
	     {title: '本地路径', field: 'localPath', align: 'center', valign: 'middle'},
	     {title: '编码', field: 'encoded', align: 'center', valign: 'middle'},
	     {title: '创建人', field: 'crtUserName', align: 'center', valign: 'middle'},
	     {title: '创建时间', field: 'crtTime', align: 'center', valign: 'middle'},
	     {title: '修改人', field: 'mdfUserName', align: 'center', valign: 'middle'},
	     {title: '修改时间', field: 'mdfTime', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
GenParam.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        GenParam.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加生成参数
 */
GenParam.openAddGenParam = function () {
    var index = layer.open({
        type: 2,
        title: '添加生成参数',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/genparam/goto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看生成参数详情
 */
GenParam.openGenParamDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '生成参数详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/genparam/goto_update/' + GenParam.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除生成参数
 */
GenParam.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/genparam/delete", function (data) {
            Feng.success("删除成功!");
            GenParam.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id",this.seItem.id);
        ajax.start();
    }
};

GenParam.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 查询生成参数列表
 */
GenParam.search = function () {
    var queryData = {};
    queryData['alias'] = $("#alias").val();
    GenParam.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = GenParam.initColumn();
    var table = new BSTable(GenParam.id, "/genparam/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(GenParam.formParams());
    GenParam.table = table.init();
});
