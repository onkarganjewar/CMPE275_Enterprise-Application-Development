<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/WEB-INF/views/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h1>Register a new PHONE</h1>
		<table>
			<form:form action="addPhone" method="POST" modelAttribute="phone">
				<form:hidden path="phoneId" />
				<tr>
					<td>Phone Number:</td>
					<td><form:input path="phoneNumber" /></td>
				</tr>
				<tr>
					<td>Phone Description:</td>
					<td><form:input path="description" /></td>
				</tr>
				<tr>
					<td>Phone Owner:</td>
					<td><form:input path="listOfUsers" /></td>
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
