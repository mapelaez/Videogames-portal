package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Categoria;

public class AccionBorrarCategoria implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		
		HashMap<String,Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		String paramCategoria = request.getParameter(AccionAgregarCategoria.PARAMCAT);
		
		Categoria categoria = categorias.get(paramCategoria);
		
		//Comprobamos si no tiene videojuegos esa categoria
		if(categoria.getVideojuegos().isEmpty()) {
			
			categorias.remove(paramCategoria);
			request.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
		}
		else {
			request.setAttribute(AccionAgregarCategoria.MSG_ERR_C, "Esta categoria contiene videojuegos, por lo que no se puede borrar");
		}
		String jsp = "panelusuario.jsp";
		
		return jsp;
	
	}

	
	
	
	
}
