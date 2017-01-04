/*
* release project page
* 2016.11.27
* guowenjuan
* */
JSObject.create({
    events:{
        '.moreLi mouseover':'showItem',
        '.moreLi mouseout':'hideItem',
        '.moreLi1 mouseover':'showItem1',
        '.moreLi1 mouseout':'hideItem1',
        '.screen click':'selectClass',
        '.tgh>label click':'checkAttache',
        '.orry>dd>label click':'selectProperty',
        '.orry_1>dd>label click':'selectInvoice',
        '#pic change':'inputPic',
        '.task_but click':'submit'
    },

    show:function(the) {
    },

    showItem:function(){
        $('.items').show();
    },

    hideItem:function(){
        $('.items').hide();
    },

    showItem1:function(){
        $('.items1').show();
    },

    hideItem1:function(){
        $('.items1').hide();
    },

    selectClass:function(){
        if($(this).attr('class').indexOf('screen_xz')!=-1) return;
        $(this).parent().children().attr('class','screen');
        $(this).addClass('screen_xz wm-ridus5');
        $(this).parent().find('input').value = $(this).html();
    },

    checkAttache:function(){
        if ($(this).attr('class').indexOf('checked_fb') != -1) {
            $(this).attr('class', 'dxk_zj_fb');
        } else {
            $(this).attr('class', 'checked_fb dxk_zj_fb');
        }
    },

    selectProperty:function(){
        var radioId = $(this).attr('name');
        $('.orry label').removeAttr('class');
        $(this).attr('class', 'checked_fb');
        $('.orry label').addClass("hange");
        $('.orry input[type="radio"]').removeAttr('checked_fb') && $('#' + radioId).attr('checked_fb', 'checked_fb');
    },

    selectInvoice:function(){
        var radioId = $(this).attr('name');
        $('.orry_1 label').removeAttr('class');
        $(this).attr('class', 'checked_fb');
        $('.orry_1 label').addClass("hange");
        $('.orry_1 input[type="radio"]').removeAttr('checked_fb') && $('#' + radioId).attr('checked_fb', 'checked_fb');
    },

    inputPic:function(){
        $(".img_2").attr("src",$('#pic').val());
        return;
        $.ajaxFileUpload({
            type:'POST',
            url:'',
            fileElementId:'pic',
            dataType:'json',
            secureuri:false,//一般设置为false
            success:function(data){
                if(data.id > 0){
                    //alert("图片上传成功！");


                    $(".img_2").attr("src", data.picPath);
                }else{
                    alert("上传失败，请上传jpg，png格式的图片！");
                }
            },
            error:function(data){
                alert("出错了，请稍候再试！");
            }
        });
    },

    submit:function(){
        if($('#projectName').val()=='' || $('#projectName').val() == '请输入项目名称') {
            alert('请填写项目名称');
            $('#projectName').focus();
            return;
        }
        if($('#projectRequ').val()=='') {
            alert('请填写项目需求');
            $('#projectRequ').focus();
            return;
        }
        if($('#projectBudget').val()=='' || $('#projectBudget').val() == '单位为元') {
            alert('请填写项目预算');
            $('#projectBudget').focus();
            return;
        }
        if($('#projectCycle').val()=='' || $('#projectCycle').val() == '请填写你的预期时间') {
            alert('请填写项目周期');
            $('#projectCycle').focus();
            return;
        }
        var ret = '项目名称:'+ $('#projectName').val() + "\n项目需求:" +  $('#projectRequ').val();
        ret += '\n项目预算:' + $('#projectBudget').val() + '\n项目周期:' + $('#projectCycle').val();
        ret += '\n接包方性质:' + $("input[name='sport']").val() + '\n发票说明:' + $("input[name='invoice']").val();
        alert(ret);
        return;
        alert($('#projectInfo').serialize());
    }




});


