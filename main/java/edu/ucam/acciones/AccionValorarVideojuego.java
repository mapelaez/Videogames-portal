package edu.ucam.acciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.servlets.Login;
import edu.ucam.us.Categoria;
import edu.ucam.us.Usuario;
import edu.ucam.us.Videojuego;

public class AccionValorarVideojuego implements Accion{

	public static final String MSG_ERR_VALORACION = "MSG_ERR_VALORACION";
	public static final String ATR_VALOR = "ATR_VALOR";
	
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp="panelusuario.jsp";
		
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) request.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap<String,Videojuego> videojuegos = (HashMap <String,Videojuego>) request.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		Usuario usuario = (Usuario) request.getSession().getAttribute(Login.ATR_USER);
		
		String nombreVideojuego = (String) request.getParameter(AccionAgregarVideojuego.ATR_NOMVID);
		String valorString = (String) request.getParameter(AccionValorarVideojuego.ATR_VALOR);
		//Declaro la variable valorFloat para comprobar que el valor introducido es un entero o no.
		
		float valorFloat = Float.parseFloat(valorString);
		int valorInt = (int) valorFloat;
		if(!("".equals(valorString))) {
			//Comprobamos que el número es un entero y que su valor esta entre [0-5]
			if(valorFloat % 1 == 0 && (valorFloat >= 0 && valorFloat <= 5)) {
				if(videojuegos.containsKey(nombreVideojuego)) {
					//Comprobamos si el usuario ya ha votado a este videojuego o no.
					if(!(videojuegos.get(nombreVideojuego).getPuntUsuarios().containsKey(usuario.getUsername()))) {
						videojuegos.get(nombreVideojuego).getPuntUsuarios().put(usuario.getUsername(), usuario);
						videojuegos.get(nombreVideojuego).setPuntuacion(valorInt);
						ArrayList<Integer> puntuacion = videojuegos.get(nombreVideojuego).getPuntuacion();
						videojuegos.get(nombreVideojuego).calcularMedia(puntuacion);
						
						//Recorremos el HashMap de categorias para actualizar el videojuego con su puntuación
						for(Categoria categoria : categorias.values()) {
							if(categoria.getVideojuegos().containsKey(nombreVideojuego)) {
								categoria.getVideojuegos().get(nombreVideojuego).getPuntUsuarios().put(usuario.getUsername(), usuario);
								categoria.getVideojuegos().get(nombreVideojuego).setPuntuacion(valorInt);
								categoria.getVideojuegos().get(nombreVideojuego).calcularMedia(puntuacion);
							}
						}
					}
					else {
						request.setAttribute(AccionValorarVideojuego.MSG_ERR_VALORACION, "Ya has votado a este videojuego");
					}			
				}
				else {
					request.setAttribute(AccionValorarVideojuego.MSG_ERR_VALORACION, "No hay un videojuego con ese nombre");
				}
			}
			else {
				request.setAttribute(AccionValorarVideojuego.MSG_ERR_VALORACION, "El numero tiene que ser un entero entre 1-10");
			}
		}
		else {
			request.setAttribute(AccionValorarVideojuego.MSG_ERR_VALORACION, "Hay que introducir un valor en el campo");
		}
		
		
		request.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
		request.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
					
		return jsp;
		
		
	}

}
