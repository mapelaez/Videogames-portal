package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Categoria;
import edu.ucam.us.Videojuego;

public class AccionBorrarCopia implements Accion{

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp = "panelusuario.jsp";
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap<String,Videojuego> videojuegos = (HashMap <String,Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		
		String idCopia = (String) request.getParameter(AccionAgregarCopia.ATR_NOMCOPIA);
		
		// Usamos esta variable para comprobar si se encuentra ya una copia con ese nombre en el HashMap de videojuegos.
				int activador = 0;
		
		//Recorremos el HashMap de videojuegos
		for(Videojuego videojuego : videojuegos.values()) {
			
			//Comprobamos si el videojuego tiene esa copia,
			if(videojuego.getCopias().containsKey(idCopia)) {
				activador = 1;
				videojuego.getCopias().remove(idCopia);
				
				//Recorremos las categorias para posteriormente borrar 
				for(Categoria categoria : categorias.values()) {
					
					//Comprobamos si se encuentra ese videojuego en el HashMap de categorias para borrar la copia posteriormente,
					if(categoria.getVideojuegos().containsKey(videojuego.getNombre())) {
						categoria.getVideojuegos().get(videojuego.getNombre()).getCopias().remove(idCopia);
						request.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
						request.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);	
					}
				}
			}
		}
		
		if(activador == 0) {
			request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "No se encuentra una copia con ese nombre");
		}
		
		return jsp;
	}

}
