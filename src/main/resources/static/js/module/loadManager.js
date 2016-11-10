define(['jquery'],function ($){
  var showModal = function(){$('#x-loading-modal').modal('show');}
  var hideModal = function(){$('#x-loading-modal').modal('hide');}
  var loadManager = {};
  loadManager.defaultTimeoutCallBack = function(){
    alert(
        'ページの表示に時間がかかっています。\n' +
        '\n' +
        'システムが一時的にご利用いただけなくなっている可能性があります。\n' +
        '\n' +
        'お手数をお掛けいたしますが、\n' +
        '　しばらく待ってから再度表示いただくか、\n' +
        '　一旦ログアウトされてから再度ログインしてください。\n'
    );
    if(location.href.match(/\/user/) && !location.href.match(/\/login/)){
      location.href = '/user';
    }else{
      location.href = '/';
    }
  };
  loadManager.onLoad = function(){};
  loadManager.resetOnLoad = function(_onLoad){
    loadManager.onLoad = _onLoad?function(){
      loadManager.onLoad = function(){};
      _onLoad();
    } : function(){};
  };
  loadManager.init = function(params){
    // configure
    var text            = params.text            || {start:'読込中...',end:'完了しました'};
    var loadFlags       = params.loadFlags       || {contentLoaded:true};
    var timeOut         = params.timeOut         || 1 * 60 * 1000;
    var timeOutCallBack = params.timeOutCallBack || loadManager.defaultTimeoutCallBack;
    var loadingStart    = params.loadingStart    || 1000;
    loadManager.resetOnLoad(params.onLoad);

    if(typeof text === 'string'){
      text = {_default:{start:text,end:'完了しました'}};
    }else{
      var newText = {_default:{start:'読込中...',end:'完了しました'}};
      $.each(text,function(key,val){
        if(key === 'start'){
          newText._default.start = val;
        }else if(key === 'end'){
          newText._default.end = val;
        }else if(loadFlags[key] !== undefined){
          if(typeof val === 'string'){
            newText[key] = {start:val,end:'完了しました'};
          }else{
            newText[key] = {start:val.start,end:val.end};
          }
        }
      });
      text = newText;
    }

    var fields = [];
    $.each(loadFlags,function(k,v){
      fields.push(k);
    });

    var timeoutObj = null;
    var displayLoadingTimeout = null;

    var displayLoading = function(){
      if(displayLoadingTimeout === null){
        clearTimeout(timeoutObj);
        timeoutObj = setTimeout(function(){
          timeOutCallBack();
          timeoutObj = null
        },timeOut);
        displayLoadingTimeout = setTimeout(function(){
          showModal();
          displayLoadingTimeout = null;
        },loadingStart);
      }
    };

    $(function(){
      var loadingModal = $('#x-loading-modal');
      var snippetClass = 'x-loading-modal-'+Math.round((Math.random() * 1000));
      loadingModal.addClass(snippetClass);
      displayLoading();
      var prevShowBy = "_default";
      var loadingCheck = null;
      loadingCheck = function(){
        var loadedSize = 0;
        var showBy = prevShowBy;
        $.each(fields,function(idx,field){
          if(loadFlags[field]){
            loadedSize++;
          }else{
            if(prevShowBy == showBy && text[field]){
              showBy = field;
            }
          }
        });
        var loaded = fields.length <= loadedSize;
        var displayText = loaded ? text[prevShowBy].end : text[showBy].start;
        if(loaded){
          if(timeoutObj !== null){
            clearTimeout(timeoutObj);
            clearTimeout(displayLoadingTimeout);
            timeoutObj = null;
            displayLoadingTimeout = null;
            loadManager.onLoad();
            setTimeout(hideModal,displayText ? 500 : 1);
          }
        }else{
          prevShowBy = showBy;
          $('.'+snippetClass).find('.x-text').text(displayText);
          displayLoading();
        }
        setTimeout(loadingCheck,333);
      };
      loadingCheck();
    });
  };
  return loadManager;
});
