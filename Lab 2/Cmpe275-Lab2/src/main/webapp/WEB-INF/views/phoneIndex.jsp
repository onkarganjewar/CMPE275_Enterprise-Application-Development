<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/WEB-INF/views/includes.jsp"%>

<html>
<head>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#Save').click( function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/test/addPhone',
				type : 'POST',
				data:'desc='+ $('#desc').val() +'&number='+ $('#number').val() + '&userId='+ $('#userId').val(),
				dataType: 'json',
			    success: function (data) {
			    	console.log(data);
			    	console.log(data['Status']);
			    	if (data['Status'] == 'Failure') {
			    		alert('Phone number already exists in the database. Please try with a different phone number. ');
			    	} else if (data['Status'] == 'Exception') {
			    		alert('Oops.. something went wrong! Please try again later.');
			    	} else {
			    		var url = "http://localhost:8080/Cmpe275-Lab2/phone/" + data;
				    	console.log(url);
						window.location.replace(url);
			    	}
			    },
				error: function (textStatus, errorThrown) {
	                alert("Error getting the data");
	            }
			});
		});
	});
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CMPE275 - Lab 2</title>
</head>
<body>
	<div align="center">
		<h1>Register a new PHONE</h1>
		<table>
			<form>
				<tr>
					<td>Phone Number:</td>
					<td><input id = "number"/></td>
				</tr>
				<tr>
					<td>Phone Description:</td>
					<td><input id = "desc" /></td>
				</tr>
				<tr>
					<td>Phone Owner ID:</td>
					<td><input id = "userId"/>
					</td>
					
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" class=button 
					id="Save" name="Save" value="Save"></td>
				</tr>
			</form>
		</table>
	</div>
</body>
</html>
