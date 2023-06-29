/**
 * Created by Think on 2017/10/25.
 */
var TableInfoDbImportDlg = {
    param: {}

}

TableInfoDbImportDlg.import = function () {
    if (GEN.param.tableName) {
        $.getJSON('/tableinfo/dbimport?' + $.param(GEN.param),function (data) {
            Feng.success(data.message);
        });
    }
}