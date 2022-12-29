/**
 * Created by Think on 2017/10/25.
 */

var TableInfoSQLImportDlg = {
    param: {}

}

TableInfoSQLImportDlg.import = function () {
    TableInfoSQLImportDlg.param.dbType = $("#fileType").val();
    TableInfoSQLImportDlg.param.sql = $("#file").val();
    $.post('/tableinfo/sqlimport', TableInfoSQLImportDlg.param, function (data) {
        Feng.success(data.message);
    });
}

$(function () {
    //hljs.initHighlightingOnLoad();
    $("#codeFile").find('code').html(hljs.highlight('sql', $("#file").val()).value);
});
$("#file").blur(function () {
    $(this).hide();
    $("#codeFile").find('code').html(hljs.highlight('sql', $(this).val()).value);
    $("#codeFile").show();
});

$("#codeFile").click(function () {
    $(this).hide();
    $("#file").show();
});