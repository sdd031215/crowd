/*
* index_person page
* 2016.11.27
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
        '.modity click':'showDetail',
        '.commodity click':'showDetail'
    },

    show:function(the) {
        the.reqCommend();
        the.reqList();
    },

    showDetail:function(){
         window.location.href = 'person.html?personId=' + $(this).attr('data-id');
    },

    reqCommend:function(){

        var data = [{
            id: '20',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            star: 4.0,
            trade:1106
        },{
            id: '22',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            star: 5.0,
            trade:1000
        },{
            id: '23',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            star: 2.0,
            trade:234
        },{
            id: '25',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            star: 4.0,
            trade:998
        },{
            id: '26',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            star: 3.0,
            trade:456
        }];
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
           star: 3.0,
           trade:1106
        },{
            id: '22',
            name:'当冉电商设计',
            img:'img/img4.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            star: 4.0,
            trade:3455
        },{
            id: '23',
            name:'当冉电商设计',
            img:'img/img3.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            star: 5.0,
            trade:1888
        },{
            id: '24',
            name:'当冉电商设计',
            img:'img/img2.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            star: 4.0,
            trade:668
        },{
            id: '25',
            name:'当冉电商设计',
            img:'img/img1.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            star: 1.0,
            trade:2311
        },{
            id: '26',
            name:'当冉电商设计',
            img:'img/img2.png',
            attr:['品牌全案', 'VI/SI设计', 'LOGO设计', '网络营销', '店铺推广', '移动端推广', 'SEO...'],
            flag: ['企业建站', '电子维修'],
            star: 4.0,
            trade:489
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
    }



});


