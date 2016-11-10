var require = (function(){
  var contextPath = (function(){
    var thisPath = window.document.getElementById('requireConf').getAttribute('src');
    var hits = (/((\/[\w-_\.]+)\/)?js\/conf\/require\.conf\.js/g).exec(thisPath);
    return (hits[2]) ? hits[2]:'';
  })();
  return {
    baseUrl : contextPath + '/js',
    paths : {
      'jquery'          :'https://code.jquery.com/jquery-3.1.1.min',
      'jquery.cookie'  :'https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie',
      'bootstrap'       :'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min',
    },
    shim: {
      'jquery.cookie' : ['jquery'],
      'bootstrap': ['jquery']
    },
    deps : ['jquery','jquery.cookie','bootstrap'],
    callback : function(){
      define("contextPath",
        contextPath ? contextPath : '/'
      );
      $(function(){
        var scripts = [];
        $('script[data-required]').each(function(){
          var script = $(this).attr('data-required');
          if(script){
            scripts.push(script);
            console.log(script + ' will be loaded.');
          }
        });
        require(scripts);
      });
    }
  };
})();