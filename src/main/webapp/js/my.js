/*
* my page
* 2016.11.28
* guowenjuan
* */
JSObject.create({

    events:{
        '.moreLi mouseover':'showItem',
        '.moreLi mouseout':'hideItem',
        '.moreLi1 mouseover':'showItem1',
        '.moreLi1 mouseout':'hideItem1',
        '.sheet click':'showMyProject',
        '.view click':'showDetail'

    },

    show:function(the) {
        var data = [{}, {}, {}];
        var html = template($("#data_tpl").html(),{list:data});
        $("#data").html(html);
    },

    showMyProject:function(){
        if($(this).attr('class').indexOf('wm-ft-ff725f')!= -1) return;

        $('.sheet').removeClass('wm-ft-ff725f');
        $(this).removeClass('wm-ft-88');
        $('.sheet').addClass('wm-ft-88');
        $(this).removeClass('wm-ft-88');
        $(this).addClass('wm-ft-ff725f');

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
        if($(this).parent().index() == 0) data = [{}, {}, {}];
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
    },

    showDetail:function() {
        window.location.href = 'proinfo.html?id=1';
    }

});


