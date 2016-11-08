<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/WEB-INF/views/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Phone Details</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#Delete').click(function() {
			$.ajax({
				url : 'http://localhost:8080/Cmpe275-Lab2/phone/' + $('#id').val(),
				type : 'DELETE',
				success : function(msg) {
					window.location.href = "http://localhost:8080/Cmpe275-Lab2/phone/"
				}
			});
		});
	});
</script>

</head>
<body>

	<form method="POST" action="updatePhone">
		<table>
			<tr>
				<td><label>ID:</label></td>
				<td><input type="text" name="id" id="id" value="${id}" readonly /></td>
			</tr>

			<tr>
				<td><label>Phone Number</label></td>
				<td><input type="text" name="phoneNumber"
					value="${number}" required /></td>
			</tr>

			<tr>
				<td><label>Description</label></td>
				<td><input type="text" name="description"
					value="${desc}" required /></td>
			</tr>
			<tr>
				<td><label>List of owners</label></td>
				<c:forEach var="listOfUsers" items="${sectionName }">
					<option value="${parameter}">${parameter}</option>
				</c:forEach>
			</tr>

			<tr>
				<td><input type="submit" name="Update" class="submit"
					value="Update"></td>

				<td><input type="button" class=button value="Delete"
					id="Delete" name="Delete"></td>
			</tr>
		</table>
	</form>

</body>
</html>