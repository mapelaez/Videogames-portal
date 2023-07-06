<%@page import="edu.ucam.servlets.Login"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%

	String msg = (String)request.getAttribute(Login.MSG_ERROR);

	if (msg != null){
		out.println("<i>"+msg+"</i><br>");
	}
	
	
	
%>

<form action="login" method="post">
Nombre:<input type="text" name="PARAM_NAME" value=""><br>
Contraseña:<input type="password" name="PARAM_PASS" value=""><br>
<input type="submit" value="Iniciar Sesión">
</form>
<a href="registro.jsp">Registrarse</a>
</body>
</html>