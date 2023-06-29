/**
 * 字典类型初始化
 */
var DictType = {
    id: "DictTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    dictTypeSearchData: {}
};

var logList = {};

/**
 * 初始化表格的列
 */
DictType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle', sortable: true},
        {title: '类型编码', field: 'code', align: 'center', valign: 'middle', sortable: false},
        {title: '字典类型名称', field: 'text', align: 'center', valign: 'middle', sortable: false},
        {title: '修改人', field: 'updateBy', align: 'center', valign: 'middle', sortable: false},
        {title: '修改时间', field: 'updateDate', align: 'center', valign: 'middle', sortable: true}];
       
};

/**
 * 检查是否选中
 */
DictType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        DictType.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加字典
 */
DictType.openAddDict = function () {
    var index = layer.open({
        type: 2,
        title: '添加字典类型',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/custom/dict/type/dict_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看字典详情
 */
DictType.openDictDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '字典详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/custom/dict/type/dict_edit/' + DictType.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除字典
 */
DictType.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/custom/dict/type/delete", function (data) {
                Feng.success("删除成功!");
                DictType.table.refresh();
                // var logList = data.logList;
                DictType.reloadLog();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dictTypeId", DictType.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否刪除字典类型: " + DictType.seItem.text + " ?", operation);
    }
};

/**
 * 查询字典列表
 */
DictType.search = function () {
    this.clearData();
    this.collectData();
    DictType.table.refresh({query: this.dictTypeSearchData});
};

/**
 * 清除数据
 */
DictType.clearData = function() {
    this.dictTypeSearchData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DictType.set = function(key, val) {
    if ($("#" + key).val() === ""){
        return this;
    }
    this.dictTypeSearchData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

DictType.envChange = function (option, value) {
    var ajax = new $ax(Feng.ctxPath + "/custom/env/change", function (data) {
        Feng.success("切换成功!");
        document.getElementById("env-display-dict-type").innerHTML = value + "<span class=\"caret\"></span>";
        DictType.table.refresh();
        DictType.reloadLog();
    }, function (data) {
        Feng.success("切换失败!");
    });
    ajax.set("env", option);
    ajax.set("cacheParam", "customDictType");
    ajax.start();

}

/**
 * 收集数据
 */
DictType.collectData = function() {
    this.set('id').set('code').set('text');
}

DictType.reloadLog = function () {
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
        document.getElementById("business-log-operation-type").innerHTML = prefix;
    }, function (data) {
    });
    ajax.set("type", "custom_DICT_TYPE");
    ajax.set("cacheParam", "customDictType");
    ajax.start();
    
}

$(function () {
    var defaultColunms = DictType.initColumn();
    var table = new BSTable(DictType.id, "/custom/dict/type/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    DictType.reloadLog();
    DictType.table = table;
});
