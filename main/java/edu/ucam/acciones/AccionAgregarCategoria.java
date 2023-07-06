package edu.ucam.acciones;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.servlets.Login;
import edu.ucam.us.Categoria;
import edu.ucam.us.Usuario;
import edu.ucam.us.Videojuego;

public class AccionAgregarCategoria implements Accion{

	public static final String ATR_CATEG = "ATR_CATEG";
	public static final String PARAMCAT = "PARAMCAT";
	public static final String MSG_ERR_C = "MSG_ERR_C";
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String,Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		
		String paramCategoria = request.getParameter(AccionAgregarCategoria.PARAMCAT);
		String jsp="panelusuario.jsp";
		
		//Comprobamos si se encuentra vacio el campo del formulario 
		if(!("".equals(paramCategoria))){
			
			//Comprobamos si no se encuentra ya esa categoria en el HashMap
			if(!(categorias.containsKey(paramCategoria))) {
					categorias.put(paramCategoria,new Categoria(paramCategoria,new HashMap<String, Videojuego>()));
					request.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
			}
			else {
					request.setAttribute(AccionAgregarCategoria.MSG_ERR_C, "Ya hay una categoria con ese nombre");
			}
		}
		else {
			request.setAttribute(AccionAgregarCategoria.MSG_ERR_C, "Debe de enviar un nombre de categoria");
		}
	
		return jsp;
	}

	
	
	
	
}
 