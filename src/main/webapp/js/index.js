/*
* index page
* 2016.11.25
* guowenjuan
* */
JSObject.create({

    events:{
        '.moreLi mouseover':'showItem',
        '.moreLi mouseout':'hideItem',
        '.moreLi1 mouseover':'showItem1',
        '.moreLi1 mouseout':'hideItem1',
        '.topNavList>li>a mouseover':'showNext',
        '.ee mouseleave':'hideNext',
        '.adio>div>label click':'selectCert',
        '.adio_1>div>label click':'selectCert1',
        '.modity click':'showDetail',
        '.commodity click':'showDetail',
        '.signbtn click':'signup'
    },

    show:function(the) {
        the.reqCommend();
        the.reqList();
    },

    showDetail:function() {
        window.location.href = 'proinfo.html?id=' + $(this).attr('data-id');
    },

    signup:function(e) {
        e.stopPropagation();
        var href = 'proinfo.html?id=' + $(this).attr('data-id');
        if ($(this).attr('data-type')=='报名中')  href += "&extend=1";
        window.location.href = href;
    },

    reqCommend:function(){

         var data = [{
             id : 11,
             name: '企业网站设计及开发',
             detail: 'B2B2C店中店多用户电商系统',
             attr: 'java',
             price: '2000',
             status: '报名中',
             img:'img/img1.png'
         }, {id : 12,
             name: '企业网站设计及开发',
             detail: 'B2B2C店中店多用户电商系统',
             attr: 'java',
             price: '2000',
             status: '已关闭',
             img:'img/img2.png'
         }, {id : 13,
             name: '企业网站设计及开发',
             detail: 'B2B2C店中店多用户电商系统',
             attr: 'java',
             price: '2000',
             status: '报名中',
             img:'img/img3.png'
         }, {id : 14,
             name: '企业网站设计及开发',
             detail: 'B2B2C店中店多用户电商系统',
             attr: 'java',
             price: '2000',
             status: '已完成',
             img:'img/img4.png'
         }, {id : 15,
             name: '企业网站设计及开发',
             detail: 'B2B2C店中店多用户电商系统',
             attr: 'java',
             price: '2000',
             status: '实施中',
             img:'img/img5.png'}];
        var html = template($("#recommed_tpl").html(),{list:data});
        $("#recommend").html(html);
    },

    reqList:function(){
        var data = [{
           id: '20',
           name:'当冉电商设计',
           img:'img/img5.png',
           attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
           flag: ['企业建站', '电子维修'],
           company:'巴戈纽公司',
           price:'2000',
           status:'报名中'
        },{
            id: '22',
            name:'当冉电商设计',
            img:'img/img4.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            company:'巴戈纽公司',
            price:'2000',
            status:'报名中'
        },{
            id: '23',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            company:'巴戈纽公司',
            price:'2000',
            status:'报名中'
        },{
            id: '24',
            name:'当冉电商设计',
            img:'img/img2.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            company:'巴戈纽公司',
            price:'2000',
            status:'报名中'
        },{
            id: '25',
            name:'当冉电商设计',
            img:'img/img1.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            company:'巴戈纽公司',
            price:'2000',
            status:'实施中'
        },{
            id: '26',
            name:'当冉电商设计',
            img:'img/img2.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            company:'巴戈纽公司',
            price:'2000',
            status:'已完成'
        }];
        var html = template($("#datalist_tpl").html(),{list:data});
        $("#datalist").html(html);
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
    },

    selectCert:function(){
        var radioId = $(this).attr('name');
        $('.adio label').removeAttr('class') && $(this).attr('class', 'checked');
        $('.adio label').addClass("dxk_zj");
        $('.adio input[type="radio"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
    },

    selectCert1:function(){
        var radioId = $(this).attr('name');
        $('.adio_1 label').removeAttr('class');
        $(this).attr('class', 'checked');
        $('.adio_1 label').addClass("dxk_zj");
        $('.adio_1 input[type="radio"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
    }

});


