package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Categoria;
import edu.ucam.us.Videojuego;

public class AccionModificarCategoria implements Accion {

	public static final String PARAMCAT2 = "PARAMCAT2";

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String jsp = "panelusuario.jsp";
			HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
			String nombreCategoria = request.getParameter(AccionAgregarCategoria.PARAMCAT);
			String nombreNuevaCategoria = request.getParameter(AccionModificarCategoria.PARAMCAT2);
			HashMap <String, Videojuego> videojuegos = (HashMap <String, Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
			Categoria categoria; 
			
			if((!"".equals(nombreCategoria) && !"".equals(nombreNuevaCategoria))) {
				
				if(categorias.containsKey(nombreCategoria) ) {
						if(!(categorias.containsKey(nombreNuevaCategoria))) {
						
							HashMap <String,Videojuego> videojuegosCat = categorias.get(nombreCategoria).getVideojuegos();
							categorias.remove(nombreCategoria);
							
							//Modificamos los videojuegos pertenecientes a la categoría con el nuevo nombre.
							for(Videojuego videojuego: videojuegosCat.values()) {
								videojuego.getCategoria().setNombre(nombreNuevaCategoria);
							}
							
							categorias.put(nombreNuevaCategoria, new Categoria(nombreNuevaCategoria,videojuegosCat));
							categoria = categorias.get(nombreNuevaCategoria);
							
							//Modificamos los videojuegos del HashMap global que tenian la categoria antigua 
							for(Videojuego videojuego: videojuegos.values()) {
								if(videojuego.getCategoria().getNombre().equals(nombreCategoria)) {
									videojuego.setCategoria(categoria);
								}
							}
							request.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG,categorias);
							request.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
						 }
						else {
							request.setAttribute(AccionAgregarCategoria.MSG_ERR_C, "Ya se encuentra una categoria con ese nombre");
						}
					}
					else {
						request.setAttribute(AccionAgregarCategoria.MSG_ERR_C, "No hay ninguna categoria con ese nombre");
					}
			}
			else {
				request.setAttribute(AccionAgregarCategoria.MSG_ERR_C, "Debe de enviar la categoria y la nueva categoria");
			}
			
			
			return jsp;
	}	
	
}
