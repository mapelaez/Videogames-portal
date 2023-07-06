<%@page import="edu.ucam.servlets.Login"%>
<%@page import="edu.ucam.servlets.Control"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registro de usuarios</title>
</head>
<body>
<%

	String msg = (String)request.getAttribute(Login.MSG_ERROR);

	if (msg != null){
		out.println("<i>"+msg+"</i><br>");
	}
	
%>

<form action="registrousuario" method="post">
Nombre:<input type="text" name="PARAM_NAME" value=""><br>
Contraseña:<input type="password" name="PARAM_PASS" value=""><br>
<input type="submit" value="Registrarse">
</form>
</body>
</html>