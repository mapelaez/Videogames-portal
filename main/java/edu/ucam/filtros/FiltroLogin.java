package edu.ucam.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.ucam.servlets.Login;
import edu.ucam.us.Usuario;

/**
 * Servlet Filter implementation class FiltroLogin
 */
@WebFilter(urlPatterns = {"/index.jsp"})
public class FiltroLogin implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest reqHttp = (HttpServletRequest)request;
		HttpSession sesion = reqHttp.getSession();
		String jsp = "index.jsp";
		Usuario user = (Usuario) sesion.getAttribute(Login.ATR_USER);
		
		//Comprobamos si se encuentra el usuario logueado
		if(user != null) {
			
			//Comprobamos si tiene permiso o no
			if(user.isPermiso() == true) {
				jsp = "paneladmin.jsp";
				request.getRequestDispatcher(jsp).forward(request, response);
			}
			else {
				jsp = "panelusuario.jsp";
				request.getRequestDispatcher(jsp).forward(request, response);
				
			}
		}
		else {
			request.getRequestDispatcher(jsp).forward(request, response);
		}
	}

	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
