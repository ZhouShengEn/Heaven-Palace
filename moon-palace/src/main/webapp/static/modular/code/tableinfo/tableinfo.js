/**
 * 管理初始化
 */
var TableInfo = {
    id: "TableInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TableInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
        {title: '表名', field: 'tableName', align: 'center', valign: 'middle'},
        {title: '类名', field: 'className', align: 'center', valign: 'middle'},
        {title: '功能', field: 'content', align: 'center', valign: 'middle'},
        {title: '字段数量', field: 'fieldNum', align: 'center', valign: 'middle'},
        {
            title: '导入',
            field: 'isImport',
            align: 'center',
            valign: 'middle',
            formatter: TableInfo.getIsOrNotName
        },
        {
            title: '导出',
            field: 'isExport',
            align: 'center',
            valign: 'middle',
            formatter: TableInfo.getIsOrNotName
        },
        {
            title: '分页',
            field: 'isPagination',
            align: 'center',
            valign: 'middle',
            formatter: TableInfo.getIsOrNotName
        },
        {
            title: '日志',
            field: 'isLog',
            align: 'center',
            valign: 'middle',
            formatter: TableInfo.getIsOrNotName
        },
        {
            title: '协议',
            field: 'isProtocol',
            align: 'center',
            valign: 'middle',
            formatter: TableInfo.getIsOrNotName
        },
        {title: '创建人', field: 'crtUserName', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'crtTime', align: 'center', valign: 'middle'},
        {title: '修改人', field: 'mdfUserName', align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'mdfTime', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
TableInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        TableInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
TableInfo.openAddTableInfo = function () {
    var index = layer.open({
                               type: 2,
                               title: '添加',
                               area: ['100%', '100%'],//宽高
                               fix: false, //不固定
                               maxmin: true,
                               shade: 0,
                               anim: 2,
                               content: Feng.ctxPath + '/tableinfo/goto_add'
                           });
    this.layerIndex = index;
};

/**
 * 打开查看编辑
 */
TableInfo.openTableInfoEdit = function () {
    if (this.check()) {
        var index = layer.open({
                                   type: 2,
                                   title: '修改',
                                   area: ['100%', '100%'],//宽高
                                   fix: false, //不固定
                                   maxmin: true,
                                   content: Feng.ctxPath + '/tableinfo/goto_update/'
                                            + TableInfo.seItem.id
                               });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
TableInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tableinfo/delete", function (data) {
            Feng.success("删除成功!");
            TableInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", this.seItem.id);
        ajax.start();
    }
};

/**
 * DB导入-from table
 */
TableInfo.dbimport = function () {
    var index = layer.open({
                               type: 2,
                               title: 'DB导入',
                               area: ['100%', '100%'],//宽高
                               fix: false, //不固定
                               maxmin: true,
                               content: Feng.ctxPath + '/tableinfo/goto_dbimport/'
                           });
    this.layerIndex = index;
};

/**
 * 导入-from sql
 */
TableInfo.sqlimport = function () {
    var index = layer.open({
                               type: 2,
                               title: 'SQL导入',
                               area: ['70%', '70%'],//宽高
                               fix: false, //不固定
                               maxmin: true,
                               content: Feng.ctxPath + '/tableinfo/goto_sqlimport/'
                           });
    this.layerIndex = index;
};

/**
 * 代码生成
 */
TableInfo.gen = function () {
    if (this.check()) {
        var index = layer.open({
                                   type: 2,
                                   title: '代码生成',
                                   area: ['80%', '70%'],//宽高
                                   fix: false, //不固定
                                   maxmin: true,
                                   content: Feng.ctxPath + '/code/tableGen/'
                                            + TableInfo.seItem.id
                               });
        this.layerIndex = index;
    }
};

TableInfo.formParams = function () {
    var queryData = {};
    return queryData;
};

/**
 * 查询列表
 */
TableInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TableInfo.table.refresh({query: queryData});
};

TableInfo.getIsOrNotName = function (value, row, index) {
    if (value == 1) {
        return '是';
    } else {
        return '否';
    }
}

$(function () {
    var defaultColunms = TableInfo.initColumn();
    var table = new BSTable(TableInfo.id, "/tableinfo/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TableInfo.formParams());
    TableInfo.table = table.init();
});