<!DOCTYPE html>
<html>
<head>
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	
	<title>PhoneNumber</title>
</head>
<script>
function showFormat(element){
	if (element.length==7){
		formatedPhone = element.substring(0,3)+'-'+element.substring(3, 7);
	}else{
	    formatedPhone = element.substring(0, 3)
		+'-'+element.substring(3, 6)
		+'-'+element.substring(6, 10);
	}
	return formatedPhone;
}

function pageDisplay(msg){
	$( '#dialog-message' ).dialog('close');
	$('#message').hide();
	$('#nav').show();
	$('#total').html('Total '+ msg.total + ' Records');
	$('#page').html('Current page : '+ msg.currentPage);
	$('#row').html('Row per page :' + msg.rowPerPage);
	$.each(msg.data, function(index, element) {
        $('#data').append("<li class='list-group-item col-xs-2 list-group-item-info' align='center'>" + showFormat(element) + "</li>");
	});
}
function showMessage(){
	 $( '#dialog-message' ).dialog({
	        modal: true
	      });
     $( '#progressbar' ).progressbar({
		      value: false
		    });	  
     $( '#dialog-message' ).show();
}
function clearData(){
	 $('#data').empty();
	 $('#nav').hide();
	 $('#page').empty();
	 $('#row').empty();
}

	$(document).ready(function() {
		$('#next').hide();
		$('#previous').hide();
		$('#message').hide();
		clearData();
 //   	$( '#dialog-message' ).dialog('close');
	    $('#go_btn').on('click', function() {
	    	var numbers = $('#number').val();
	        if(numbers.length != 10 && numbers.length != 7){
	        	//alert('Please enter 7-10 digits.');
	     		$('#message').show();
	     		clearData();
	        	event.stopPropagation();
	        	return;
	        } 
	       showMessage();
	    	$('#next').hide();
			$('#previous').hide();
			clearData();
	        
	        var numbers = $('#number').val();
	        clearTimeout($(this).data('timer'));
	        $(this).data('timer', setTimeout(function() {
		        $.ajax({
	                type: 'GET',
	                dataType: 'json',
	                url: '/getResult',
	                data:{'numbers' : numbers}
	            }).done(function(msg) {
	            	//$('#content').html(JSON.stringify(msg));
					pageDisplay(msg);
					
	            	
	            	if(parseInt(msg.total) / parseInt(msg.rowPerPage) <= parseInt(msg.currentPage)){
	            		$('#next').hide();
	            	}else{
	            		$('#next').show();
	            	}
	            });
	        }));
	    });
	});
	
	$(document).ready(function() {
	    $('#previous').on('click', function() {
	        showMessage();
	    	$('#next').show();
	    	clearData();
	        clearTimeout($(this).data('timer'));
	        $(this).data('timer', setTimeout(function() {
		        $.ajax({
	                type: 'GET',
	                dataType: 'json',
	                url: '/getPrePageResult'
	            }).done(function(msg) {
	            	//$('#content').html(JSON.stringify(msg));
	            	pageDisplay(msg);
	            	if(1 == parseInt(msg.currentPage)){
	            		$('#previous').hide();
	            	}
	            });
	        }));
	    });
	});
	
	$(document).ready(function() {
		$('#next').on('click', function() {
			showMessage();
			$('#previous').show();
			clearData();
	        clearTimeout($(this).data('timer'));
	        $(this).data('timer', setTimeout(function() {
		        $.ajax({
	                type: 'GET',
	                dataType: 'json',
	                url: '/getNextPageResult'
	            }).done(function(msg) {
	            	pageDisplay(msg);
	            	if(parseInt(msg.total) / parseInt(msg.rowPerPage) <= parseInt(msg.currentPage)){
	            		$('#next').hide();
	            	}
	            });
	        }));
	    });
	});
	
</script> 
<body>
    <a href="${logout}"><span class="glyphicon glyphicon-log-in"></span> Sign out</a>
    <div align="center"> 
        <label for="number">Please Enter a number with length of 7 or 10:</label>
	    <div align="center" class="input-group" style="width:30%">
			    
			<input type="number" name="number" id="number" class="form-control"/>
			<span class="input-group-btn">
			<button class="btn btn-default" name="go_btn" id="go_btn"> GO</button></span>
	    
		</div>
		<div class="alert alert-danger" role="alert" id="message">Warning: The input should have a length of 7 or 10!</div>
	 </div> 
	
	<div id="dialog-message">
		<div id="progressbar" ></div>
	</div >
	<ul class="list-group row" name="data" id="data" style="margin-top: 50px"></ul>
	<div name="nav" id="nav">
		<nav class="navbar navbar-default" >
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <a class="navbar-brand" name="total" id="total"></a>
		    </div>
		    
			
		    <ul class="nav navbar-nav navbar-right">
		      <li><button type="button" class="btn btn-link" name="pre_btn" id="previous"> Previous Page</button></li>		      
		      <li><button type="button" class="btn btn-link" name="page" id = "page"></button></li>
		      <li><button type="button" class="btn btn-link" name="next_btn" id=next> Next Page</button></li>
		      
		    </ul>
		  </div>
		</nav>
	</div>
</body>
</html>