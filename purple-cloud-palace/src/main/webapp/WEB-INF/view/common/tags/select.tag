@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/
<div class="form-group">
    @if(isNotEmpty(name)){
    <label class="col-sm-3 control-label">${name}</label>
    @}
    <div class="col-sm-9">
        <select class="form-control" id="${id}" name="${id}" style="${style!}">
            ${tagBody!}
        </select>
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}">
        @}
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}
@if(isNotEmpty(value)){
<script>$("#${id}").val("${value}");</script>
@}


