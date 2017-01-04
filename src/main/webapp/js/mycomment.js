/*
* my page
* 2016.11.29
* guowenjuan
* */
JSObject.create({

    events:{
        '.moreLi mouseover':'showItem',
        '.moreLi mouseout':'hideItem',
        '.moreLi1 mouseover':'showItem1',
        '.moreLi1 mouseout':'hideItem1',
        '.screen click':'showComment'
    },

    show:function(the) {
        var data = [{}, {}, {}];
        var html = template($("#receive_tpl").html(),{list:data});
        $("#data").html(html);
    },

    showComment:function(){
        if($(this).attr('class').indexOf('screen_xz')!= -1) return;
        $('.screen').removeClass('screen_xz');
        $(this).addClass('screen_xz');
        if ($(this).html() == '我收到的评价') {
            var data = [{}, {}, {}];
            var html = template($("#receive_tpl").html(),{list:data});
            $("#data").html(html);
            return;
        }
        var data = [{}, {}, {}, {}, {}, {}];
        var html = template($("#comment_tpl").html(),{list:data});
        $("#data").html(html);
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
    }

});


