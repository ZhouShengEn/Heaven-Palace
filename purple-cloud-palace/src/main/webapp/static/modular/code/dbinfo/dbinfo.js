/**
 * 数据库管理管理初始化
 */
var DbInfo = {
    id: "DbInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DbInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
	     {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
	     {title: '别名', field: 'alias', align: 'center', valign: 'middle'},
	     {title: '数据库驱动', field: 'dbDriver', align: 'center', valign: 'middle'},
	     {title: '数据库地址', field: 'dbUrl', align: 'center', valign: 'middle'},
	     {title: '数据库账户', field: 'dbUserName', align: 'center', valign: 'middle'},
	     {title: '连接密码', field: 'dbPassword', align: 'center', valign: 'middle',formatter:function (value, row, index) {
             return "******";
         }},
	     {title: '数据库类型', field: 'dbType', align: 'center', valign: 'middle'},
	     {title: '创建人', field: 'crtUserName', align: 'center', valign: 'middle'},
	     {title: '创建时间', field: 'crtTime', align: 'center', valign: 'middle'},
	     {title: '修改人', field: 'mdfUserName', align: 'center', valign: 'middle'},
	     {title: '修改时间', field: 'mdfTime', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
DbInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DbInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加数据库管理
 */
DbInfo.openAddDbInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加数据库管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dbinfo/goto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看数据库管理详情
 */
DbInfo.openDbInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '数据库管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dbinfo/goto_update/' + DbInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除数据库管理
 */
DbInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/dbinfo/delete", function (data) {
            Feng.success("删除成功!");
            DbInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id",this.seItem.id);
        ajax.start();
    }
};

DbInfo.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 查询数据库管理列表
 */
DbInfo.search = function () {
    var queryData = {};
    queryData['alias'] = $("#alias").val();
    queryData['dbUrl'] = $("#dbUrl").val();
    queryData['dbType'] = $("#dbType").val();
    DbInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = DbInfo.initColumn();
    var table = new BSTable(DbInfo.id, "/dbinfo/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(DbInfo.formParams());
    DbInfo.table = table.init();
});
