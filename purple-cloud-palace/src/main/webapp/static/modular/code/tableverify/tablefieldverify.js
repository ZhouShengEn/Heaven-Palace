/**
 * 管理初始化
 */
var TableFieldVerify = {
    id: "TableFieldVerifyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TableFieldVerify.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
	     {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
	     {title: '字段ID', field: 'fieldId', align: 'center', valign: 'middle'},
	     {title: '前端校验', field: 'viewVerification', align: 'center', valign: 'middle'},
	     {title: '后台校验', field: 'serverVerification', align: 'center', valign: 'middle'},
	     {title: '允许空', field: 'notNull', align: 'center', valign: 'middle'},
	     {title: '最小', field: 'minNum', align: 'center', valign: 'middle'},
	     {title: '最大', field: 'maxNum', align: 'center', valign: 'middle'},
	     {title: '正则', field: 'regex', align: 'center', valign: 'middle'},
	     {title: '是否邮箱', field: 'isEmail', align: 'center', valign: 'middle'},
	     {title: '是否手机', field: 'isPhone', align: 'center', valign: 'middle'},
	     {title: '是否电话', field: 'isTelephone', align: 'center', valign: 'middle'},
	     {title: '是否身份证', field: 'isIdcard', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
TableFieldVerify.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TableFieldVerify.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
TableFieldVerify.openAddTableFieldVerify = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tablefieldverify/goto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
TableFieldVerify.openTableFieldVerifyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tablefieldverify/goto_update/' + TableFieldVerify.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
TableFieldVerify.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tablefieldverify/delete", function (data) {
            Feng.success("删除成功!");
            TableFieldVerify.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tablefieldverifyId",this.seItem.id);
        ajax.start();
    }
};

TableFieldVerify.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 查询列表
 */
TableFieldVerify.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TableFieldVerify.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TableFieldVerify.initColumn();
    var table = new BSTable(TableFieldVerify.id, "/tablefieldverify/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TableFieldVerify.formParams());
    TableFieldVerify.table = table.init();
});
