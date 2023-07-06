<%@page import="edu.ucam.servlets.Control"%>
<%@page import="edu.ucam.acciones.AccionAgregarCategoria"%>
<%@page import="edu.ucam.us.Categoria"%>
<%@page import="edu.ucam.us.Videojuego"%>
<%@page import="edu.ucam.us.Copia"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.ucam.acciones.AccionAgregarVideojuego"%>
<%@page import="edu.ucam.acciones.AccionValorarVideojuego"%>
<%@page import="edu.ucam.acciones.AccionAgregarCopia"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Panel de usuario</title>
</head>
<body>
<tiles:insertDefinition name="myheader" />

<%

String msg = (String)request.getAttribute(AccionAgregarCategoria.MSG_ERR_C);

//Imprimimos el error de categorias(si lo hay)
if (msg != null){
	out.println("<i>"+msg+"</i><br>");
}
%>

<h4>Agregar Categoria</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="ADD_CATEGORIA">
Nombre de Categoria<input type="text" name="PARAMCAT" value=""><br>
<input type="submit" value="Agregar Categoria">	 
</form>

<br><br>

<h4>Modificar Categoria</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="MODIFICAR_CATEGORIA">
Nombre de Categoria<input type="text" name="PARAMCAT" value=""><br>
Nuevo Nombre de Categoria<input type="text" name="PARAMCAT2" value=""><br>
<input type="submit" value="Modificar Categoria">	 
</form>

<h4> Categorias </h4>
<% 


	HashMap<String,Categoria> categorias = (HashMap <String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
	
	//Comprobamos que las categorias no son null y que hay más de 0
	if(categorias != null && categorias.size() > 0){
		
		//Imprimimos las categorias
		for(Categoria categoria : categorias.values()){
			out.println("<br>"+categoria.getNombre() 
			+ " <a href=\"control?"
			+ Control.PARAM_ACTION_ID + "=BORRAR_CATEGORIA&" + AccionAgregarCategoria.PARAMCAT +"=" + categoria.getNombre()
			+ "\" > Borrar</a>"
			+ "<br>" + "&nbsp&nbspVideojuegos:" + "<br>");
			
			//Imprimimos los videojuegos de las categorias
			for(Videojuego videojuego: categorias.get(categoria.getNombre()).getVideojuegos().values()){
				out.println("&nbsp&nbsp&nbsp"+ videojuego.getNombre()+"<br>");
			}
		}
	}
	out.println("<br><br>");
%>

<h4>Agregar Videojuego</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="ADD_VIDEOJUEGO">
Nombre de Categoria <input type="text" name="PARAMCAT" value=""><br>
Nombre de Videojuego <input type="text" name="ATR_NOMVID" value=""><br>
<input type="submit" value="Agregar Videojuego">	 
</form>

<br><br>
<h4>Modificar Videojuego</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="MODIFICAR_VIDEOJUEGO">
Nombre de Videojuego <input type="text" name="ATR_NOMVID" value=""><br>
Nuevo Nombre de Videojuego <input type="text" name="ATR_NOMVID2" value=""><br>
Nuevo Nombre de Categoria <input type="text" name="PARAMCAT" value=""><br>
<input type="submit" value="Modificar Videojuego">	 
</form>

	
<%

String msg2 = (String)request.getAttribute(AccionAgregarVideojuego.MSG_ERR_V);

//Imprimimos el error de videojuegos(si lo hay)
if (msg2 != null){
	out.println("<i>"+msg2+"</i><br>");
}


%>

<h4> Videojuegos </h4>
<% 

	HashMap<String,Videojuego> videojuegos = (HashMap <String,Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
	
	//Comprobamos que los videojuegos no son null y que hay más de 0
	if(videojuegos != null && videojuegos.size() > 0){
		
		//Imprimimos los videojuegos
		for(Videojuego videojuego : videojuegos.values()){
			
			
			out.println(videojuego.getNombre()
			+ " "
			+ videojuego.getCategoria().getNombre()
			+ " "
			+ " <a href=\"control?"
			+ Control.PARAM_ACTION_ID + "=BORRAR_VIDEOJUEGO&" + AccionAgregarVideojuego.ATR_NOMVID +"=" + videojuego.getNombre()
			+ "\" > Borrar</a>");
			out.println("<form action=control method=post>" 
			+ "<input type=hidden name="+Control.PARAM_ACTION_ID+" value=VALORAR_VIDEOJUEGO>" 
			+ "<input type=number name=ATR_VALOR step=1 min=0 max=5 required>"
			+ "<input type=hidden name="+AccionAgregarVideojuego.ATR_NOMVID+" value="+ videojuego.getNombre()+" >"
			+ "<input type=submit value=Votar>" 
			+ "</form>");
			out.println("<p>Media Valoración:"+videojuego.getMediaPunt()+"</p>");
			
			
			
		}
	}
	out.println("<br>");
%>


<h4>Agregar Copia</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value="AGREGAR_COPIA">
Nombre de Videojuego <input type="text" name="ATR_NOMVID" value=""><br>
ID de la Copia <input type="text" name="ATR_NOMCOPIA" value=""><br>
<input type="submit" value="Agregar Copia">	 
</form>
<br><br>
<h4>Modificar ID de Copia</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value=MODIFICAR_IDCOPIA>
ID de la Copia <input type="text" name="ATR_NOMCOPIA" value=""><br>
Nuevo ID de la Copia <input type="text" name="ATR_NOMCOPIA2" value=""><br>
<input type="submit" value="Modificar ID de la Copia">	 
</form>
<br><br>
<h4>Modificar Estado de Copia</h4>
<form action="control" method="post">
<input type="hidden" name="<%= Control.PARAM_ACTION_ID %>" value=MODIFICAR_ESTADOCOPIA>
ID de la Copia <input type="text" name="ATR_NOMCOPIA" value=""><br>
Nuevo Estado de la Copia <input type="text" name="ATR_ESTADOCOPIA" value=""><br>
<input type="submit" value="Modificar estado de la Copia">	 
</form>

<%

String msg3 = (String)request.getAttribute(AccionAgregarCopia.MSG_ERR_COPIA);

//Imprimimos el error de copias(si lo hay)
if (msg3 != null){
	out.println("<i>"+msg3+"</i><br>");
}
%>



<h4> Copias </h4>

<%

	//Comprobamos que los videojuegos no son null y que hay más de 0
	if(videojuegos != null && videojuegos.size() > 0){
		
		//Recorremos los videojuegos
		for(Videojuego videojuego : videojuegos.values()){
			out.println("<br>"+videojuego.getNombre()+"<br>"+"&nbspCopias:<br>");
			
			//Recorremos las copias de los videojuegos
			for(Copia copia: videojuegos.get(videojuego.getNombre()).getCopias().values()){
				
				//Comprobamos que las copias no son null y que hay más de 0
				if(videojuegos.get(videojuego.getNombre()).getCopias() != null && videojuegos.get(videojuego.getNombre()).getCopias().size() > 0){
					out.println("&nbsp&nbsp"+copia.getId()
					+ " " 
					+ copia.getEstado()
					+ "<a href=\"control?" 
					+ Control.PARAM_ACTION_ID + "=BORRAR_COPIA&" 
					+ AccionAgregarCopia.ATR_NOMCOPIA + "=" + copia.getId()
					+ "\" > Borrar</a><br>");
				}
			}
		}
	}

	out.println("<br><br>");
	out.println("<a href=\"control?" + Control.PARAM_ACTION_ID + "=LOGOUT" + "\" > Cerrar Sesión </a>") ;
%>
<br>
<a href="rest.jsp">Panel Rest</a>

<tiles:insertDefinition name="myfooter" />
</body>
</html>	