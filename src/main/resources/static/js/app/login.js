require(['jquery','contextPath','module/api','module/loadManager'],function($,contextPath,api,loadManager){
  var formData = {
    accountName : null,
    password : null
  };

  var loadFlags = {
    dataInit : false,
    tryLogin : true
  };

  var actions = {};
  actions = {
    formDataChanged : function(input){
      var name = $(input).attr('name');
      var value = $(input).val();
      formData[name] !== undefined && (formData[name] = value);
    },
    tryLogin : function(){
      var formControls = $(
        '.x-loginForm input,'+
        '.x-loginForm password,' +
        '.x-loginForm .x-submit')
      api.post.tryLogin(
        formData,{
          before   : function(){ loadFlags.tryLogin = false; formControls.prop('disabled',true);},
          complete : function(){ loadFlags.tryLogin = true;formControls.prop('disabled',false); },
          success  : function(){ location.href = contextPath + '/content/'; }
        }
      );
    }
  };

  var initData = function(){
    $('.x-loginForm input,'+
      '.x-loginForm password').each( function(){actions.formDataChanged(this); });
    loadFlags.dataInit = true;
    console.log(formData);
  };
  var initScreen = function(){
    $('.x-loginForm input,'+
      '.x-loginForm password').on({'change' : function(){ actions.formDataChanged(this); } });
    $('.x-loginForm .x-submit').on({"click" : actions.tryLogin});
  };
  initData();
  loadManager.init({
    text:{
      start:'loading...',
      end : null,
      tryLogin : {
        start : 'trying to login...',
        end : null
      }
    },
    loadFlags      : loadFlags,
    timeOut        : 30 * 1000,
    onLoad         : initScreen,
      loadingStart   : 1000
  });
});