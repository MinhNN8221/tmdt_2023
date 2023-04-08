   $(document).ready(function(){
	
	      loadTotal();
	 
	      $('#cart .top-cart-content').load('/freshfood/cartitems ul#cart-slidebar');	
 
    });
     
    function del(element){
	      let id = $(element).attr("item-id");
	      var token = $('meta[name = "_csrf"]').attr('content');
     	  var name = $('meta[name = "_csrf_header"]').attr('content');
          $.ajax({
	          url: '/freshfood/cart/delete/'+id,
              type: 'delete',
              dataType: 'json',
              beforeSend: function(xhr){
	             xhr.setRequestHeader(name, token);
              },
              success: function(json){
	              $('#cart .top-cart-content').load('/freshfood/cartitems ul#cart-slidebar');
    	          $('.cartCount2').html('('+json['total']+')');
              },
              error: function(){
	             alert("không thành công !");
              }
          });
     };
     
     function loadTotal(){
	    $.ajax({
		     url: '/freshfood/cart/total',
             type: 'get',
             dataType: 'json',
		     success: function(json){
    	          $('.cartCount2').html('('+json['total']+')');
              },
              error: function(){
	             alert("không thành công !");
              }
	    });
     }
