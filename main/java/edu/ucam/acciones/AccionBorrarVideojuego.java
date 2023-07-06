package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Categoria;
import edu.ucam.us.Videojuego;

public class AccionBorrarVideojuego implements Accion{

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp = "panelusuario.jsp";
		
		HashMap <String,Videojuego> videojuegos = (HashMap<String, Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		String nombreVideojuego =  request.getParameter(AccionAgregarVideojuego.ATR_NOMVID);
		
		videojuegos.remove(nombreVideojuego);
		
		//Borramos en el HashMap de categorias aquel videojuego que tenga ese nombre
		for(Categoria categoria: categorias.values()) {
			if(categoria.getVideojuegos().containsKey(nombreVideojuego)){
				categoria.getVideojuegos().remove(nombreVideojuego);
			}
		}
		
		request.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
		request.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
		
		return jsp;
	
	}
	
	
}
