/**
 * 字典管理初始化
 */
var DictItem = {
    id: "DictTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    dictItemSearchData: {}
};

/**
 * 初始化表格的列
 */
DictItem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle', sortable: true},
        {title: '序号', field: 'dictSort', align: 'center', valign: 'middle', sortable: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: false},
        {title: '值', field: 'value', align: 'center', valign: 'middle', sortable: false},
        {title: '展示名', field: 'text', align: 'center', valign: 'middle', sortable: false},
        {title: '父节点id', field: 'parentId', align: 'center', valign: 'middle', sortable: true},
        {title: '层级关系', field: 'level', align: 'center', valign: 'middle', sortable: true},
        {title: '类型id', field: 'typeId', align: 'center', valign: 'middle', sortable: true},
        {title: '类型编码', field: 'typeCode', align: 'center', valign: 'middle', sortable: false},
        {title: '字典类型名称', field: 'typeText', align: 'center', valign: 'middle', sortable: false},
        {title: '创建人', field: 'createBy', align: 'center', valign: 'middle', sortable: false},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '修改人', field: 'updateBy', align: 'center', valign: 'middle', sortable: false},
        {title: '修改时间', field: 'updateDate', align: 'center', valign: 'middle', sortable: true}];
       
};

/**
 * 检查是否选中
 */
DictItem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        DictItem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加字典
 */
DictItem.openAddDict = function () {
    var index = layer.open({
        type: 2,
        title: '添加字典',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/custom/dict/dict_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看字典详情
 */
DictItem.openDictDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '字典详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/custom/dict/item/dict_edit/' + DictItem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除字典
 */
DictItem.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/custom/dict/item/delete", function (data) {
                Feng.success("删除成功!");
                DictItem.table.refresh();
                DictItem.reloadLog();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dictId", DictItem.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除字典 " + DictItem.seItem.name + "?", operation);
    }
};

/**
 * 查询字典列表
 */
DictItem.search = function () {
    this.clearData();
    this.collectData();
    DictItem.table.refresh({query: this.dictItemSearchData});
};

/**
 * 清除数据
 */
DictItem.clearData = function() {
    this.dictItemSearchData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DictItem.set = function(key, val) {
    if ($("#" + key).val() === ""){
        return this;
    }
    this.dictItemSearchData[key] = $("#" + key).val();
    return this;
}

/**
 * 收集数据
 */
DictItem.collectData = function() {
    this.set('id').set('dictSort').set('name').set('value').set('text').set('parentId').set('level').set('typeId');
}

DictItem.envChange = function (option, value) {
    var ajax = new $ax(Feng.ctxPath + "/custom/env/change", function (data) {
        Feng.success("切换成功!");
        document.getElementById("env-display-dict-item").innerHTML = value + "<span class=\"caret\"></span>";
        DictItem.table.refresh();
        DictItem.reloadLog();
    }, function (data) {
        Feng.success("切换失败!");
    });
    ajax.set("env", option);
    ajax.set("cacheParam", "customDictItem");
    ajax.start();

}

DictItem.reloadLog = function () {
    var ajax = new $ax(Feng.ctxPath + "/Business/log/list", function (data) {
        // debugger;
        // $("#business-log-operation").load("/common/business_log.html", {"logList":"data"});
        var prefix = "";
        for (let i = 0; i < data.length; i++) {
            var log = data[i];
            prefix = prefix + "<div class=\"vertical-timeline-content\">";
            if (log.title === '新增'){
                prefix = prefix + "<h2 style='color: greenyellow;font-weight:bold'>"+log.title+"</h2>";
            }else if (log.title === '修改'){
                prefix = prefix + "<h2 style='color: #00d9ff;font-weight:bold'>"+log.title+"</h2>";
            }else {
                prefix = prefix + "<h2 style='color: #ef7171;font-weight:bold' >"+log.title+"</h2>";
            }
            prefix = prefix + "<p>"+log.content+"</p>";
            prefix = prefix + "<i>"+log.createBy+"-"+log.createTime+"</i></div>";
        }
        document.getElementById("business-log-operation-item").innerHTML = prefix;
    }, function (data) {
    });
    ajax.set("type", "custom_DICT_ITEM");
    ajax.set("cacheParam", "customDictItem");
    ajax.start();

}

$(function () {
    var defaultColunms = DictItem.initColumn();
    var table = new BSTable(DictItem.id, "/custom/dict/item/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    DictItem.reloadLog();
    DictItem.table = table;
});
