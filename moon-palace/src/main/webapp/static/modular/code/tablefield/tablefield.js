/**
 * 管理初始化
 */
var TableField = {};

TableField.trClink = function () {
    $("#fieldInfoDiv").find('.fieldNav').each(function () {
        $(this).css('display','none');
    });
    var maxId = $(this).attr("id").replace('fieldTr', '');
    $("#fieldNav"+maxId).css('display','');
}

TableField.fieldAdd = function () {
    var maxId = $(
        $("#tableField").find('tbody').find('tr')[$("#tableField").find('tbody').find('tr').length
                                                  - 1]).attr("id");
    if (maxId) {
        maxId = maxId.replace('fieldTr', '')
        maxId = parseInt(maxId) + 1;
    } else {
        maxId = 1;
    }
    //初始化数据
    var tr = $("#tableField tbody tr").eq(0).clone();
    $(tr).find('[type="checkbox"]').each(function () {
        $(this).removeAttr("checked");
    });
    $(tr).find('[type="text"]').each(function () {
        $(this).val('');
    });
    $(tr).find('select').each(function () {
        $(this).find("option:selected").attr("selected", false);
        $(this).find("option").first().attr("selected", true);
    });
    tr = $(tr).attr('id','fieldTr'+maxId);
    $("#tableField").find('tbody').append(tr);

    $("#fieldInfoDiv").find('.fieldNav').each(function () {
       $(this).css('display','none');
    });
    // 校验值 和 数据字段
    var div = $("#fieldInfoDiv .fieldNav").eq(0).clone();
    div = $(div).attr('id','fieldNav'+maxId);
    $(div).find('[type="text"]').each(function () {
        $(this).val('');
    });
    $(div).find('select').each(function () {
        $(this).find("option:selected").attr("selected", false);
        $(this).find("option").first().attr("selected", true);
    });
    // 设置初始化数据
    $(div).find('.tab-pane').each(function () {
        $(this).removeClass('in active');
    });
    $($(div).find('.tab-pane')[0]).addClass('in active');
    $(div).find('li').each(function () {
        $(this).removeClass('active');
    });
    $($(div).find('li')[0]).addClass('active');
    $(div).css('display','');
    $("#fieldInfoDiv").append(div);

}

TableField.fieldTrRemove = function () {
    if($("#tableField").find('tbody').find('tr').length > 1){
        $(this).parent().parent().parent().remove();
        var id = $(this).parent().parent().parent().attr('id');
        id = id.replace('fieldTr','fieldNav');
        $('#'+id).remove();
    }
}

TableField.tabSwitch = function (e) {
    e.preventDefault();
    $(this).tab('show');
    var div = $(this).parent().parent().parent();
    $(div).find('.tab-pane').each(function () {
        $(this).removeClass('in active');
    });
    $(div).find($(this).attr('href')).addClass('in active');
}

$(function () {
    $(document).on('click',".fieldTr",TableField.trClink);
    $(document).on('click',".fieldAdd",TableField.fieldAdd);
    $(document).on('click',".fieldTrRemove",TableField.fieldTrRemove);
    $(document).on('click',".fieldNav a",TableField.tabSwitch);
});
