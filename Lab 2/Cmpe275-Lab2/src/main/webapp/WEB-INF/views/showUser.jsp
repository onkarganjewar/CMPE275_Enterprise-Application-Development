<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group 3: User</title>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!--  http://www.mytechnotes.biz/2012/11/spring-mvc-rest-calls-with-ajax.html  -->
<script type="text/javascript">
	$(document).ready(function() {
		$('#Delete').click(function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/user/' + $('#id').val(),
				type : 'DELETE',
				success: function (data) {
					console.log(data);
					if (data == "Success") {
						alert("User deleted successfully");
						var url1 = "http://localhost:8080/Cmpe275-Lab2/user";
						window.location.replace(url1);
					} else if (data == "Not found") {
						alert("User does not exist. Please try again.");
					}
	            },
	            error: function (textStatus, errorThrown) {
					console.log("Error function invoked");
					console.log("Textstatus = ",textStatus);
	            	alert("Error getting the data");
	            }
			});
		});
//		https://hostname/user/userId?firstname=XX&lastname=YY&title=abc&street=AAA&city=BBB&state=CCC&zip=95012
		$('#Update').click(function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/user/'
				+ $('#id').val()+'?firstname='+$('#firstName').val()+
				'&lastname='+$('#lastName').val()+
				'&title='+$('#title').val()+
				'&street='+$('#street').val()+
				'&city='+$('#city').val()+
				'&state='+$('#state').val()+
				'&zip='+$('#zip').val(),
				type : 'POST',
				success: function (data) {
					console.log(data);
						var url1 = "http://localhost:8080/Cmpe275-Lab2/user/"+ $('#id').val();
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
<h1>User Details</h1>

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
				<td><input type="text" name="firstName" id="firstName" value="${fname}"
					required /></td>
			</tr>

			<tr>
				<td><label>Last Name</label></td>
				<td><input type="text" name="lastName" id="lastName" value="${lname}"
					required /></td>
			</tr>

			<tr>
				<td><label>Title</label></td>
				<td><input type="text" name="title" id="title" value="${title}"
					required /></td>
			</tr>
			<tr>
				<td><label>City</label></td>
				<td><input type="text" name="address.city" id="city" value="${city}"
					required /></td>
			</tr>

			<tr>
				<td><label>Street</label></td>
				<td><input type="text" name="address.street" id="street" value="${street}"
					required /></td>

			</tr>
			<tr>
				<td><label>State</label></td>
				<td><input type="text" name="address.state" id="state" value="${state}"
					required /></td>
			</tr>
			<tr>
				<td><label>Pincode</label></td>
				<td><input type="text" name="address.zip" id="zip" value="${zip}"
					required /></td>
			</tr>
			<tr>
				<td><input type="button" class=button value="Delete"
					id="Delete" name="Delete"></td>

			<td><input type="button" class=button value="Update"
					id="Update" name="Update"></td>
			</tr>
		</table>
		</form:form>
<h2>List of phones for this user</h2>

    <hr />
<table border="1">
	<th>ID</th>
	<th>Phone Number</th>
	<th>Description</th>
	<c:forEach items="${listOfPhones}" var="phone">
		<tr>
			<td>${phone.id}</td>
			<td>${phone.number}</td>
			<td>${phone.description}</td>
		</tr>
	</c:forEach>
</table>
<hr />
</body>
</html>