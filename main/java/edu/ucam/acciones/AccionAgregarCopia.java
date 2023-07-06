package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Categoria;
import edu.ucam.us.Copia;
import edu.ucam.us.Videojuego;
import edu.ucam.us.estadoVideojuego;

public class AccionAgregarCopia implements Accion{

	public static final String MSG_ERR_COPIA = "MSG_ERR_COPIA";
	public static final String ATR_NOMCOPIA = "ATR_NOMCOPIA";
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp = "panelusuario.jsp";
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap<String,Videojuego> videojuegos = (HashMap <String,Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		
		String nombreVideojuego = (String) request.getParameter(AccionAgregarVideojuego.ATR_NOMVID);
		String idCopia = (String) request.getParameter(AccionAgregarCopia.ATR_NOMCOPIA);
		
		// Usamos esta variable para comprobar si se encuentra ya una copia con ese nombre en el HashMap de videojuegos.
		int activador = 0;
		
		if((!"".equals(idCopia) && !"".equals(nombreVideojuego))) {
			
			if(videojuegos.containsKey(nombreVideojuego)) {
				
				// Recorremos el HashMap de videojuego para ver si se encuentra una copia con ese id
				for(Videojuego videojuego: videojuegos.values()) {
					if(videojuego.getCopias().containsKey(idCopia)) {
						activador = 1;
					}
				}
				if(activador == 0) {
					videojuegos.get(nombreVideojuego).getCopias().put(idCopia, new Copia(idCopia,estadoVideojuego.DISPONIBLE));
					
					// Buscamos en el HashMap de categorias el videojuego con el nombre introducido por el usuario para añadir una copia a este
					for(Categoria categoria : categorias.values()) {
						if(categoria.getVideojuegos().containsKey(nombreVideojuego)) {
							categoria.getVideojuegos().get(nombreVideojuego).getCopias().put(idCopia, new Copia(idCopia,estadoVideojuego.DISPONIBLE));
						}
					}
					
					request.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
					request.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
					
				}
				else {
					request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "Ya se  encuentra una copia con ese id");
				}
				
			}
			else {
				request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "No se encuentra un videojuego con ese nombre");
			}
		}
		else {
			request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "Debes de enviar el videojuego y el id de la copia.");
		}
			
		return jsp;
	}	

}
