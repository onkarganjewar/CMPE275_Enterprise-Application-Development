<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.Random"%>
<%!public int generateRandomNumber(int start, int end) {
		Random random = new Random();
		long fraction = (long) ((end - start + 1) * random.nextDouble());
		return ((int) (fraction + start));
	}%>
<!-- <h1>Generate Random Number:<%=generateRandomNumber(10, 8888)%></h1> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group 3: User</title>

</head>
<body>
	<div align="center">
		<h1>Register a new user</h1>
		<table>
			<form:form action="addUser" method="POST" modelAttribute="user">
				<form:hidden path="userId" />
				<tr>
					<td>First Name:</td>
					<td><form:input path="firstName" /></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><form:input path="lastName" /></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><form:input path="email" /></td>
				</tr>

				<tr>
					<td>Title:</td>
					<td><form:input path="title" /></td>
				</tr>

				<tr>
					<td>Street:</td>
					<td><form:input path="address.street" /></td>
				</tr>
				<tr>
					<td>City:</td>
					<td><form:input path="address.city" /></td>
				</tr>
				<tr>
					<td>State:</td>
					<td><form:input path="address.state" /></td>
				</tr>
				<tr>
					<td>Pincode:</td>
					<td><form:input path="address.zip" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Save"></td>
				</tr>
			</form:form>
		</table>
	</div>
</body>
</html>
