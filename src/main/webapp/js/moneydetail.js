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
        '.sheet click':'showMoneyDetail'
    },

    show:function(the) {
        var data = [{}, {}, {}, {}, {}];
        var html = template($("#data_tpl").html(),{list:data});
        $("#data").html(html);
    },

    showMoneyDetail:function(){
        if($(this).attr('class').indexOf('wm-ft-ff725f')!= -1) return;

        $('.sheet').removeClass('wm-ft-ff725f');
        $('.sheet').removeClass('wm-ft-88');
        $('.sheet').addClass('wm-ft-88');
        $(this).removeClass('wm-ft-88');
        $(this).addClass('wm-ft-ff725f');

        var data = [{}, {}, {}, {}, {}];
        if($(this).parent().index() == 0) data = [{}, {}, {}];
        if($(this).parent().index() == 2) data = [{}, {}];
        if($(this).parent().index() == 3) data = [{}, {}, {}, {}];
        if($(this).parent().index() == 4) data = [{}, {}, {}, {}, {}];
        $("#data").html('');
        var html = template($("#data_tpl").html(),{list:data});
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


