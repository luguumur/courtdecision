//(function ($){
//    T5.extendInitializers(function(){
//          function confirmation(spec){
//                $("#"+spec.id).bind("click", function(e){ 
//                	
//                	var t = confirm(spec.message);
//                	
//                	 if(t == true){
//                		 e.preventDefault();
//                     }
//                	
//                     
//                      
//                });
//          }
//          return { confirmation : confirmation}
//    });
//}) (jQuery);
(function($) {
	T5.extendInitializers(function() {
		function confirmation(spec) {
			$("#" + spec.id).bind("click", function(e) {
				if (!confirm(spec.message))
					e.preventDefault();
			});
		}
		console.log(confirmation);
		return {
			confirmation : confirmation
		}
	});
})(jQuery);