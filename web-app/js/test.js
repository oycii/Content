var Ajax;
if (Ajax && (Ajax != null)) {
	Ajax.Responders.register({
	  onCreate: function() {
        if($('spinner') && Ajax.activeRequestCount>0)
          Effect.Appear('spinner',{duration:0.5,queue:'end'});
	  },
	  onComplete: function() {
        if($('spinner') && Ajax.activeRequestCount==0)
          Effect.Fade('spinner',{duration:0.5,queue:'end'});
	  }
	});
}


jQuery(document).ready(function() {
	  jQuery(document).ajaxStart(function(){
	    $('#spinner').show();
	  });
	  jQuery(document).ajaxStop(function(){
	    $('#spinner').fadeOut(500);
	  });
	});
