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
        '.eject click':'cancel',
        '.close click':'close',
        '.submit click':'close',
        '.view click':'goDetail'
    },

    show:function(the) {
        var data = [{}, {}, {}];
        var html = template($("#data_tpl").html(),{list:data});
        $("#data").html(html);
    },

    cancel:function(){
        $(".backg").css({display:"block",height:$(document).height()});
        var yscroll =document.documentElement.scrollTop;
        $(".eject_box").css("top","100px");
        $(".eject_box").css("display","block");
        document.documentElement.scrollTop=0;
    },

    close:function(){
        $(".backg").css("display","none");
        $(".eject_box").css("display","none");
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

    goDetail:function(){
        window.location.href = 'proinfo.html?id=1';
    }

});


