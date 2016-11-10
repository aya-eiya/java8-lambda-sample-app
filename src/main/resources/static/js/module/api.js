define(['jquery','contextPath'],function($,contextPath){

  var apiCommon = function(apiPath,options,params){
    if(!apiPath.match(/$http(s):\/\//)){
      apiPath = contextPath + ('/' + apiPath).replace(/^\/\//,'/');
    }
    var ajaxOptions = $.extend({
      success:function(result){
        options && options.success && options.success(result);
        options && options.complete && options.complete(result);
      },
      error: function(result,error){
        options && options.error && options.error(result,error);
        options && options.complete && options.complete(result);
      }
    },params);
    options && options.before && options.before();
    return $.ajax(apiPath,ajaxOptions);
  }

  var getBase = function(apiPath,options){
    return apiCommon(apiPath,options,{});
  };
  var postBase = function(apiPath,data,options){
    return apiCommon(apiPath,options,{
      contentType: 'application/json;charset=utf-8',
      data: JSON.stringify(data),
      method:'POST'
    });
  };
  return {
     get : {},
     post : {
       tryLogin : function(data,options){ return postBase('login/api/tryLogin',data,options); }
     }
  };
});