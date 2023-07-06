<%@page import="edu.ucam.servlets.Login"%>
<%@page import="edu.ucam.us.Usuario"%>
<%@page import="edu.ucam.servlets.Control"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.ucam.tags.ListarUsuarios"%>
<%@	taglib uri="mistags" prefix="dad2" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Panel de admin</title>
</head>
<body>
<tiles:insertDefinition name="myheader" />
<%

	String msg = (String)request.getAttribute(Login.MSG_ERROR);

	if (msg != null){
		out.println("<i>"+msg+"</i><br>");
	}
	
%>


<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="ALTA_USUARIO">
Nombre:<input type="text" name="PARAM_NAME" value=""><br>
Contraseña:<input type="password" name="PARAM_PASS" value=""><br>
<input type="submit" value="Alta Usuario">	 
</form>

<br><br>

<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="MODIFICAR_USUARIO">
Nombre:<input type="text" name="PARAM_NAME" value=""><br>
Nuevo Nombre: <input type="text" name="PARAM_NAME2" value=""><br>
Nueva Contraseña: <input type="text" name="PARAM_PASS2" value=""><br>
<input type="submit" value="Modificar Usuario">
</form>

<dad2:listarusuarios></dad2:listarusuarios>
<br>
<a href="panelusuario.jsp">Panel Usuario</a>

<tiles:insertDefinition name="myfooter" />
</body>
</html>