package edu.ucam.acciones;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.servlets.Login;
import edu.ucam.us.Usuario;

public class AccionLogout implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp = "index.jsp";
		
		Usuario user = (Usuario) request.getSession().getAttribute(Login.ATR_USER);
		
		//Ponemos el user a null para desconectar la cuenta.
		user = null;
		request.getSession().setAttribute(Login.ATR_USER, user);
		
		return jsp;
		
	}

}
