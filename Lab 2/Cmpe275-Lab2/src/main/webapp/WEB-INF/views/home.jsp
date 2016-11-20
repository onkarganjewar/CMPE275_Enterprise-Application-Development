<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.Random"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group 3: User</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!--  http://www.mytechnotes.biz/2012/11/spring-mvc-rest-calls-with-ajax.html  -->
<script type="text/javascript">
var value = Math.floor((Math.random() * 1000) + 1);

	$(document).ready(function() {
//		https://hostname/user/userId?firstname=XX&lastname=YY&title=abc&street=AAA&city=BBB&state=CCC&zip=95012
		$('#Create').click(function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/user/'
				+ value +'?firstname='+$('#firstName').val()+
				'&lastname='+$('#lastName').val()+
				'&title='+$('#title').val()+
				'&street='+$('#street').val()+
				'&city='+$('#city').val()+
				'&state='+$('#state').val()+
				'&zip='+$('#zip').val(),
				type : 'POST',
				success: function (data) {
					console.log(data);
						var url1 = "http://localhost:8080/Cmpe275-Lab2/user/"+ value;
						window.location.replace(url1);
	            },
	            error: function (textStatus, errorThrown) {
					console.log("Error function invoked");
					console.log("Textstatus = ",textStatus);
	            	alert("Error getting the data");
	            }
			});
		});
	
	});
</script>
</head>
<body>
	<div align="center">
		<h1>Register a new user</h1>
		<table>
			<form:form action="addUser" method="POST" modelAttribute="user">
				<form:hidden path="id" />
				<tr>
					<td>First Name:</td>
					<td><form:input path="firstName" id="firstName" /></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><form:input path="lastName" id="lastName" /></td>
				</tr>

				<tr>
					<td>Title:</td>
					<td><form:input path="title" id="title" /></td>
				</tr>

				<tr>
					<td>Street:</td>
					<td><form:input path="address.street" id="street" /></td>
				</tr>
				<tr>
					<td>City:</td>
					<td><form:input path="address.city" id="city"  /></td>
				</tr>
				<tr>
					<td>State:</td>
					<td><form:input path="address.state" id="state" /></td>
				</tr>
				<tr>
					<td>Pincode:</td>
					<td><form:input path="address.zip" id="zip" /></td>
				</tr>
				<tr>
					<td><input type="button" class=button value="Create"
					id="Create" name="Create"></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>
