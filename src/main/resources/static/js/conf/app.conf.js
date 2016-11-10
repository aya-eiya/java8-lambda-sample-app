require(['jquery'],function($){
  $.ajaxPrefilter(function(options, originalOptions, xhr) {
    var token = $.cookie('XSRF-TOKEN');
    var ref = $.cookie('REF-CODE');
    if (token) {
        return xhr.setRequestHeader('X-Requested-With','XMLHttpRequest')
                   .setRequestHeader('X-XSRF-TOKEN', token);
    }
    return xhr.setRequestHeader('X-Requested-With','XMLHttpRequest');
  });
});