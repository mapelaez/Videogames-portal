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

public class AccionModificarIDCopia implements Accion{

	public static final String ATR_NOMCOPIA2 = "ATR_NOMCOPIA2";
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp = "panelusuario.jsp";
		
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap<String,Videojuego> videojuegos = (HashMap <String,Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		
		// Usamos esta variable para comprobar si se encuentra ya una copia con ese nombre en el HashMap de videojuegos.
		int activador = 0;
		
		// Usamos esta variable para comprobar si se encuentra ya una copia con ese nombre en el HashMap de videojuegos.
		int activador2 = 0;
		
		String idCopia = (String) request.getParameter(AccionAgregarCopia.ATR_NOMCOPIA);
		String idCopia2 = (String) request.getParameter(AccionModificarIDCopia.ATR_NOMCOPIA2);
		
		if((!"".equals(idCopia) && !"".equals(idCopia2))) {
			
			for(Videojuego videojuego : videojuegos.values()) {
				//Comprobamos si el nuevo id ya esta 
				if(videojuego.getCopias().containsKey(idCopia2)) {
					activador2 = 1;
				}
			}
			if(activador2 == 0) {
				for(Videojuego videojuego : videojuegos.values()) {
					//Comprobamos si el id antiguo se encuentra y recuperamos los atributos de videojuego.
					if(videojuego.getCopias().containsKey(idCopia)) {
						activador = 1;
						String nombreVideojuego = videojuego.getNombre();
						estadoVideojuego estado = videojuego.getCopias().get(idCopia).getEstado();
						videojuego.getCopias().remove(idCopia);
						videojuego.getCopias().put(idCopia2, new Copia(idCopia2,estado));
						
						//Recorremos las categorias
						for(Categoria categoria : categorias.values()) {
							
							//Comprobamos que tiene el videojuego en el HashMap
							if(categoria.getVideojuegos().get(nombreVideojuego) != null) {
								
								//Comprobamos si el HashMap de categoria contiene a la copia, para modificarla
								if(categoria.getVideojuegos().get(nombreVideojuego).getCopias().containsKey(idCopia)) {
									categoria.getVideojuegos().get(nombreVideojuego).getCopias().remove(idCopia);
									categoria.getVideojuegos().get(nombreVideojuego).getCopias().put(idCopia2, new Copia(idCopia2,estado));
										
									request.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
									request.setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
								}
							}
							
						}
					
					}
				}
				if(activador != 1) {
					request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "No hay ninguna copia con ese ID en el servidor");
				}
			}	
			else {
				request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "Ya se encuentra el nuevo ID de la copia en el servidor");
			}
		}
		else {
			request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "Debes de enviar el ID antiguo y el nuevo al que se quiere modificar");
		}
		
		
		
		
		return jsp;
	}

	
	
	
	
	
}
