/*
* page init
* 2016.11.20
* guowenjuan
* */
(function() {
    if (window.JSObject) {
        return;
    }

    var cache = {};

    function bindEvent(el,event,fn,options) {
        if(typeof fn === 'function'){
//             $(el).bind(event, function(e){
//                 fn.call(this,e,options);
//             });
            $(document).on(event, el, function(e){
                fn.call(this,e,options);
            });
        }
    }

    function bindEvents(options) {
        var events = options.events;
        for(var i in events){
            var reg = /^(\S+)\s*(.+)$/,e = i.split(',');
            for(var j=0,l=e.length;j<l;j++){
                var result = reg.exec(e[j]);
                if(result.length>2) {
                    bindEvent(result[1],result[2],options[events[i]],options);
                }
            }
        }

    }

    function show(options) {

        if (options.events) {
            bindEvents(options);
        }

        options.show(options);
    }

    function create(options){
        cache = options;
    }

    $(document).ready(function() {
        show(cache);
    })

    var utils = {

        request: function(m){
            var re = new RegExp(m + "=([^\&]*)", "i");
            var sValue = re.exec(document.location.search);
            if (sValue == null)
                return '';
            return sValue[1];

        },

        ajax : function(settings){
            settings.type = (settings.type && settings.type == "POST")?"POST":"GET";
//            var load;
//            if(!settings.hideLoading)load = loading("加载中…");

            $.ajax({
                url : settings.url,
                type : settings.type,
                cache:false,
                data : settings.params,
                dataType : "json",
                success : function(result){
                    if (settings.successCallback) {
                        settings.successCallback(result);
                        return;
                    }
                },
                error : function(){
                    if(settings.failureCallback){
                        settings.failureCallback();
                    }
                    else{
//                        JDSY.app.alert({msg:"网络错误"});
//                        JDSY.app.alert({msg:JSON.stringify(settings)});

                    }
                },
                complete : function(){
//                    if(load) $(load).hide();
                }
            });

        },

        post : function(url, params, callBack, failureCallback, hideLoading) {
            utils.ajax({
                url : url,
                type : "POST",
                params : params,
                successCallback : callBack,
                failureCallback : failureCallback,
                hideLoading : hideLoading
            });
        },

        get : function(url, params, callBack, failureCallback , hideLoading) {
            utils.ajax({
                url : url,
                type : "GET",
                params : params,
                successCallback : callBack,
                failureCallback : failureCallback,
                hideLoading : hideLoading
            });
        }
    }


    window.JSObject = {
          create:create,
          utils:utils

    }

})();

