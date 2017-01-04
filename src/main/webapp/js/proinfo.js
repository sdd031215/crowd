/*
* project info page
* 2016.11.27
* guowenjuan
* */
JSObject.create({
    events:{
        '.moreLi mouseover':'showItem',
        '.moreLi mouseout':'hideItem',
        '.moreLi1 mouseover':'showItem1',
        '.moreLi1 mouseout':'hideItem1',
        '.tgh>label click':'checkAttache',
        '.task_but_2 click':'showInput',
        '.task_but click': 'submit',
        '#pic change':'inputPic'
    },

    show:function(the) {
        $('.sche').hide();
        var id = parseInt(JSObject.utils.request('id'));
        var bm = [11,13,20,22,23,24];
        var wc = [14, 26];
        var gb = [12];
        var ss = [15, 25];
        if (bm.indexOf(id) !=-1) {
            $('.task_but_2').show();
            if (JSObject.utils.request("extend") == 1)
            $('#signup').show();
            $('#xmbm').show();
        }

        if (gb.indexOf(id) != -1)  $('#xmgb').show();

        if (ss.indexOf(id) != -1)  {
            $('#xmss').show();
            $('#zbxx').show();
        }

        if (wc.indexOf(id) != -1) {
            $('#xmwc').show();
            $('#zbxx').show();
            $('#comment').show();
        }


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

    checkAttache:function(){
        if ($(this).attr('class').indexOf('checked_fb') != -1) {
            $(this).attr('class', 'dxk_zj_fb');
        } else {
            $(this).attr('class', 'checked_fb dxk_zj_fb');
        }
    },

    showInput:function(){
        $('#signup').show();
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
        if ($(this).html() == '取消') {
            $('#signup').hide();
            $.$w.windowScroll(0);
            return;
        }

        if($('#projectCycle').val()=='' || $('#projectCycle').val() == '请输入预计工作周期') {
            alert('请填写预计工作周期');
            $('#projectCycle').focus();
            return;
        }

        if($('#superiority').val()=='') {
            alert('请填写您的优势');
            $('#superiority').focus();
            return;
        }

        var ret = '预计工作周期:'+ $('#projectCycle').val() + "\n优势:" +  $('#superiority').val();
        alert(ret);
        return;

    }




});


