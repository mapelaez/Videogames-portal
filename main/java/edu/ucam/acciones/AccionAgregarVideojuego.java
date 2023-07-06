package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.servlets.Login;
import edu.ucam.us.Categoria;
import edu.ucam.us.Copia;
import edu.ucam.us.Usuario;
import edu.ucam.us.Videojuego;

public class AccionAgregarVideojuego implements Accion{

	public static final String ATR_VIDEO = "ATR_VIDEO";
	public static final String ATR_NOMVID = "ATR_NOMVID";
	public static final String MSG_ERR_V = "MSG_ERR_V";
	
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		String jsp = "panelusuario.jsp";
		HashMap <String,Videojuego> videojuegos = (HashMap<String, Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		String nombreVideojuego =  request.getParameter(AccionAgregarVideojuego.ATR_NOMVID);
		String nombreCategoria = request.getParameter(AccionAgregarCategoria.PARAMCAT);
		
		//Comprueba que los campos de los formularios no esten vacios
		if((!"".equals(nombreVideojuego) && !"".equals(nombreCategoria))) {
			
			//Comprueba si no se encuentra el nombre del videojuego en el HashMap
			if(!(videojuegos.containsKey(nombreVideojuego))) {
				
				//Comprueba si hay una categoria con ese nombre y crea el videojuego posteriormente para ambos HashMaps.
				if(categorias.containsKey(nombreCategoria)) {
					Categoria categoria	= categorias.get(nombreCategoria);
					videojuegos.put(nombreVideojuego, new Videojuego(nombreVideojuego,categoria,new HashMap<String, Usuario>(),new HashMap<String, Copia>()));
					Videojuego videojuego = videojuegos.get(nombreVideojuego);
					categorias.get(nombreCategoria).getVideojuegos().put(nombreVideojuego,videojuego);
					request.setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
					request.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
				}
				else {
					request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "No se encuentra una categoria con ese nombre");
				}
			}
			else {
				request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "Ya se encuentra un videojuego con ese nombre");
			}
			
			
		}
		else {
			request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "Debe de enviar un videojuego y su correspondiente categoria");
		}
	
		return jsp;
	}

}
