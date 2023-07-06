package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Categoria;
import edu.ucam.us.Videojuego;
import edu.ucam.us.estadoVideojuego;

public class AccionModificarEstadoCopia implements Accion{

	private static final String ATR_ESTADOCOPIA = "ATR_ESTADOCOPIA";
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	String jsp = "panelusuario.jsp";
		
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap<String,Videojuego> videojuegos = (HashMap <String,Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		
		// Usamos esta variable para comprobar si se encuentra ya una copia con ese nombre en el HashMap de videojuegos.
		int activador = 0;
				
				
		String idCopia = (String) request.getParameter(AccionAgregarCopia.ATR_NOMCOPIA);
		String estadoCopia = (String) request.getParameter(AccionModificarEstadoCopia.ATR_ESTADOCOPIA);
		estadoVideojuego estado;
		// Ponemos el estado de copia en mayusculas.
		estadoCopia = estadoCopia.toUpperCase();
		if((!"".equals(idCopia)) && (!"".equals(estadoCopia))) {
			// Recorremos el HashMap de videojuegos para ver si hay un videojuego que tenga el ID de la copia
			for(Videojuego videojuego: videojuegos.values()) {
				if(videojuego.getCopias().containsKey(idCopia)) {
					activador = 1;
				}
			}
			//Si se encuentra el videojuego procedemos a cambiar su estado antiguo por el nuevo
			if(activador == 1) {
				
				//Comprobamos si el estado de la copia introducido es una de las 3 opciones
				if(estadoCopia.equals("DISPONIBLE") || estadoCopia.equals("VENDIDO") || estadoCopia.equals("ALQUILADO")) {
					
					//Recorremos los videojuegos
					for(Videojuego videojuego : videojuegos.values()) {
						
						//Comprobamos si se encuentra la copia en el videojuego
						if(videojuego.getCopias().containsKey(idCopia)) {
							String nombreVideojuego = videojuego.getNombre();
							videojuego.getCopias().get(idCopia).setEstado(estadoVideojuego.valueOf(estadoCopia));
							
							//Recorremos las categorias
							for(Categoria categoria : categorias.values()) {
								
								//Comprobamos que tiene el videojuego en el HashMap
								if(categoria.getVideojuegos().get(nombreVideojuego) != null) {
								
									//Establecemos el estado de la copia dentro del HashMap de categorias
									if(categoria.getVideojuegos().get(nombreVideojuego).getCopias().containsKey(idCopia)) {
										categoria.getVideojuegos().get(nombreVideojuego).getCopias().get(idCopia).setEstado(estadoVideojuego.valueOf(estadoCopia));
										
										request.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
										request.setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
									}
								}
							}
						
						}
					}
				}
				else{
					request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "Debes de enviar un estado correcto: ALQUILADO, DISPONIBLE o  VENDIDO");
				}
			}
			else {
				request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "No se encuentra ese ID de la copia");
			}
				
		}
		else {
			request.setAttribute(AccionAgregarCopia.MSG_ERR_COPIA, "Debes de enviar el ID  y el nuevo estado al que se quiere modificar");
		}
		
		
		return jsp;
		
		
	}

}
