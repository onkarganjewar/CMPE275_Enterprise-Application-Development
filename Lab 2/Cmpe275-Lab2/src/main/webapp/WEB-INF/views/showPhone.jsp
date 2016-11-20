<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/WEB-INF/views/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group 3: Phone</title>

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
						alert("Can not delete the phone. It is assigned to a user.");
					} else if (data == "Exception") {
						alert("Can not delete the phone. Please try again.")
					}
	            },
	            error: function (textStatus, errorThrown) {
	                alert("Error getting the data");
	            }
			});
		});
		$("#addRow").on("click", function () {
			console.log("Clicked");
	        var newRow = $("<tr>");
	        var cols = "";
	        cols += '<tr><td>First Name: </td><td><input type="text" name="firstName"/></td></tr>';
	        cols += '<tr><td>Last Name: </td><td><input type="text" name="lastName"/></td></tr>';
	        cols += '<tr><td>Title: </td><td><input type="text" name="title"/></td></tr>';
	        cols += '<tr><td>Street: </td><td><input type="text" name="street"/></td></tr>';
	        cols += '<tr><td>City: </td><td><input type="text" name="city"/></td></tr>';
	        cols += '<tr><td>State: </td><td><input type="text" name="state"/></td></tr>';
	        cols += '<tr><td>Zip: </td><td><input type="text" name="zip"/></td></tr>';
	        cols += '<tr><td><input type="submit" name="AddUsers" class="submit" value="Add"></td></tr>';
	        newRow.append(cols);
	        $("table.user-list").append(newRow);
	    });

	$("#insertRow").on("click", function () {
		console.log("Clicked");
        var newRow = $("<tr>");
        var cols = "";
        cols += '<tr><td>User ID: </td><td><input type="text" name="userId"/></td></tr>';
        cols += '<tr><td>First Name: </td><td><input type="text" name="firstName"/></td></tr>';
        cols += '<tr><td>Last Name: </td><td><input type="text" name="lastName"/></td></tr>';
        cols += '<tr><td><input type="submit" name="insertUsers" class="submit" value="Add"></td></tr>';
        newRow.append(cols);
        $("table.users-list").append(newRow);
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
				<td><label>City</label></td>
				<td><input type="text" name="city" value="${city}"
					required /></td>
			</tr>
			
			<tr>
				<td><label>Street</label></td>
				<td><input type="text" name="street" value="${street}"
					required /></td>
					
			</tr>
			<tr>
				<td><label>State</label></td>
				<td><input type="text" name="state" value="${state}"
					required /></td>
			</tr>
			<tr>
				<td><label>Pincode</label></td>
				<td><input type="text" name="zip" value="${zip}"
					required /></td>
			</tr>
			
			<tr>
				<td><input type="submit" name="Update" class="submit"
					value="Update"></td>

				<td><input type="button" class=button value="Delete"
					id="Delete" name="Delete"></td>
			
				<td><input type="button" class=button value="Add New User"
					id="addRow" name="addRow"></td>
					
					<td><input type="button" class=button value="Attach Existing User"
					id="insertRow" name="insertRow"></td>
			</tr>
		</table>
	</form>

	<form:form action="addUsers" method="POST">
		<input type = "hidden" name = "phoneId" id = "phoneId" value = ${id } readonly/>
			<table class="user-list">
    		</table>
	</form:form>

	<form:form action="insertUsers" method="POST">
		<input type = "hidden" name = "phoneId" id = "phoneId" value = ${id } readonly/>
			<table class="users-list">
    		</table>
	</form:form>
	
	<form:form action="detachUser" method="POST">
	<h2>List of assigned users for this phone</h2>
	<input type = "hidden" name = "phoneId" id = "phoneId" value = ${id } readonly/>
	<c:forEach items="${listOfUsers}" var="user">
		<hr />
		<table border="4">
			<tr>
				<th>ID</th>
				<td><input type="text" id = "userId" name = "userId" value="${user.id}" readonly /></td>
			</tr>
			<tr>
				<th>First Name</th>
				<td><input type="text" value="${user.firstName}" /></td>
			</tr>
			<tr>
				<th>Last Name</th>
				<td><input type="text" value="${user.lastName}" /></td>
			</tr>
			<tr>
				<td><input type="submit" class=submit value="Remove user"
					id="detachUser" name="detachUser"></td>
			</tr>
		</table>
		<hr />
	</c:forEach>
</form:form>

</body>
</html>