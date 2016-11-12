<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/WEB-INF/views/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CMPE275 - Lab 2</title>


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#Delete').click( function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/phone/'+ $('#id').val(),
				type : 'DELETE',
				success: function (data) {
					console.log(data);
					if (data == "Success") {
						alert("Phone deleted successfully");
						var url = "http://localhost:8080/Cmpe275-Lab2/phone";
						window.location.replace(url);
					} else if (data == "Not found") {
						alert("Phone ID does not exists. Please try again.");
					} else if (data == "Failure") {
						alert("Can not delete a phone. It is assigned to a user");
					} else if (data == "Exception") {
						alert("Can not delete the phone. Please try again.")
					}
	            },
	            error: function (textStatus, errorThrown) {
	                alert("Error getting the data");
	            }
			});
		});
	});
</script>

</head>
<body>
<h1>Phone Details</h1>

	<form method="POST" action="updatePhone">
		<table>
			<tr>
				<td><label>ID:</label></td>
				<td><input type="text" name="id" id="id" value="${id}" readonly /></td>
			</tr>

			<tr>
				<td><label>Phone Number</label></td>
				<td><input type="text" name="phoneNumber" value="${number}"
					required /></td>
			</tr>

			<tr>
				<td><label>Description</label></td>
				<td><input type="text" name="description" value="${desc}"
					required /></td>
			</tr>

			<tr>
				<td><input type="submit" name="Update" class="submit"
					value="Update"></td>

				<td><input type="button" class=button value="Delete"
					id="Delete" name="Delete"></td>
			</tr>
		</table>
	</form>

	<h2>List of assigned users for this phone</h2>
	<c:forEach items="${listOfUsers}" var="user">
		<hr />
		<table border="4">
			<tr>
				<th>ID</th>
				<td><input type="text" value="${user.userId}" readonly /></td>
			</tr>
			<tr>
				<th>First Name</th>
				<td><input type="text" value="${user.firstName}" /></td>
			</tr>
			<tr>
				<th>Last Name</th>
				<td><input type="text" value="${user.lastName}" /></td>
			</tr>
		</table>
		<hr />
	</c:forEach>

</body>
</html>