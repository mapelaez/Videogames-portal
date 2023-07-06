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

public class AccionModificarVideojuego implements Accion {

	public static final String ATR_NOMVID2 = "ATR_NOMVID2";
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		
		String jsp = "panelusuario.jsp";
		
		HashMap<String,Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap <String,Videojuego> videojuegos = (HashMap<String, Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		
		String nombreVideojuego = (String) request.getParameter(AccionAgregarVideojuego.ATR_NOMVID);
		String nuevoNombreVideojuego = (String) request.getParameter(AccionModificarVideojuego.ATR_NOMVID2);
		String nombreNuevaCategoria = (String) request.getParameter(AccionAgregarCategoria.PARAMCAT);
		
		if((!"".equals(nombreVideojuego) &&  !"".equals(nuevoNombreVideojuego) && !"".equals(nombreNuevaCategoria))) {//Comprobamos campos vacios
			
			if(videojuegos.containsKey(nombreVideojuego)) {//Comprobamos que exista en el HashMap
				
				//Vemos si no se encuentra un videojuego con ese nombre
				if(!(videojuegos.containsKey(nuevoNombreVideojuego))) {
					
					//Vemos si se encuentra la nueva categoría en el HashMap de categorias.
					if(categorias.containsKey(nombreNuevaCategoria)) {
						
						Categoria categoria = categorias.get(nombreNuevaCategoria);
						
						//Recuperamos las copias y usuarios del antiguo videojuego.
						HashMap <String,Copia> copias = videojuegos.get(nombreVideojuego).getCopias();
						HashMap <String,Usuario> usuarios = videojuegos.get(nombreVideojuego).getPuntUsuarios();
						
						//Borramos y actualizamos con el nuevo nombre
						videojuegos.remove(nombreVideojuego);
						videojuegos.put(nuevoNombreVideojuego, new Videojuego(nuevoNombreVideojuego,categoria,usuarios,copias));
						
						//Recorremos el HashMap de categorias para cambiar al nuevo contenido de videojuegos.
						for(Categoria categoriaRecorrer : categorias.values()) {
							
							//Comprobamos que el HashMap de categoria contiene el nombre del antiguo videojuego.
							if(categoriaRecorrer.getVideojuegos().containsKey(nombreVideojuego)) {
								categoriaRecorrer.getVideojuegos().remove(nombreVideojuego);
							}
							
							if(categoriaRecorrer.getNombre().equals(nombreNuevaCategoria)) {
								categoriaRecorrer.getVideojuegos().put(nuevoNombreVideojuego, new Videojuego(nuevoNombreVideojuego,categoria,usuarios,copias));
							}
						
						request.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
						request.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
						
						}
					}				
					else {
						request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "No hay ninguna categoría con ese nombre");
					}
					
				}
				else {
					request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "Ya se encuentra un videojuego con ese nombre");
				}		
			}
			
			else {
				request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "No hay ningun videojuego con ese nombre");
			}
		}
		else {
			request.setAttribute(AccionAgregarVideojuego.MSG_ERR_V, "Debe de enviar el nombre del videojuego,del nuevo videojuego y de la categoria");
		}
		
		
		return jsp;
	
	
	}
}
