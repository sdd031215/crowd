/*
* person detail page
* 2016.11.28
* guowenjuan
* */
JSObject.create({
    events:{
        '.moreLi mouseover':'showItem',
        '.moreLi mouseout':'hideItem',
        '.moreLi1 mouseover':'showItem1',
        '.moreLi1 mouseout':'hideItem1',
        '.topNavList>li>a mouseover':'showNext',
        '.ee mouseleave':'hideNext'
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

    showNext:function(){
//        var idx = $(".topNavList a").index(this);
        var idx = 0;
        $(".subNavWrapper").show();
//
//        $(".subNavSet div").hide();
        $(".subNavSet div").eq(idx).show();
    },

    hideNext:function(){

        $(".subNavWrapper").hide();
    }




});


