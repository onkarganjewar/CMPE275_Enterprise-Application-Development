<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Details</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!--  http://www.mytechnotes.biz/2012/11/spring-mvc-rest-calls-with-ajax.html  -->
<script type="text/javascript">
	$(document).ready(function() {
		$('#Delete').click(function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/user/' + $('#id').val(),
				type : 'DELETE',
				success : function(msg) {
					window.location.href = "http://localhost:8080/Cmpe275-Lab2/user/"
				},
	            error: function(e)
	            {
	                alert("Error: " + e);
	            }
			});
		});
	});
</script>

</head>
<body>

	<form:form action="updateUser" method="POST" modelAttribute="user">
		<table>
			<!-- The name attribute, however, is used in the HTTP request sent by your browser to 
					the server as a variable name associated with the data contained in the value attribute. -->
			<tr>
				<td><label>ID:</label></td>
				<td><input type="text" name="userId" id="id" value="${id}" readonly /></td>
			</tr>
			<!-- If you add an ID attribute, it will not change anything in the HTTP header. 
					It will just make it easier to hook it with CSS and JavaScript. -->

			<tr>
				<td><label>First Name</label></td>
				<td><input type="text" name="firstName" value="${fname}"
					required /></td>
			</tr>

			<tr>
				<td><label>Last Name</label></td>
				<td><input type="text" name="lastName" value="${lname}"
					required /></td>
			</tr>

			<tr>
				<td><label>Title</label></td>
				<td><input type="text" name="title" value="${title}"
					required /></td>
			</tr>
			<tr>
				<td><label>Email</label></td>
				<td><input type="email" value="${email}" name="email" ></td>
			</tr>
			<tr>
				<td><label>City</label></td>
				<td><input type="text" name="address.city" value="${city}"
					required /></td>
			</tr>
			
			<tr>
				<td><label>Street</label></td>
				<td><input type="text" name="address.street" value="${street}"
					required /></td>
					
			</tr>
			<tr>
				<td><label>State</label></td>
				<td><input type="text" name="address.state" value="${state}"
					required /></td>
			</tr>
			<tr>
				<td><label>Pincode</label></td>
				<td><input type="text" name="address.zip" value="${zip}"
					required /></td>
			</tr>
			<tr>
				<td><input type="submit" class="submit" name="Update" 
					value="Update"></td>

				<td><input type="button" class=button value="Delete"
					id="Delete" name="Delete"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>