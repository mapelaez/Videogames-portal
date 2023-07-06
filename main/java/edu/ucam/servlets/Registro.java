package edu.ucam.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Usuario;

/**
 * Servlet implementation class Registro
 */
@WebServlet(value={"/registrousuario"})
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAM_NAME = "PARAM_NAME";
	public static final String PARAM_PASS = "PARAM_PASS";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramNombre = request.getParameter(Registro.PARAM_NAME);
		String paramContra = request.getParameter(Registro.PARAM_PASS);
		String jsp = "registro.jsp";
		
		if((!"".equals(paramNombre) && !"".equals(paramContra))) { //Control de campos de texto vacios
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver"); //Driver de oracle
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");//Nos conectamos a la base de datos
				
				String query = "SELECT username FROM usuarios WHERE username='" + paramNombre +"'"; //Cadena SQL que devuelve el usuario si existe.
				Statement stmt = con.createStatement(); //Objeto usado para ejecutar una sentencia SQL y devolver resultado.
				ResultSet rs = stmt.executeQuery(query); //Ejecutamos la query y guardamos el resultado en rs
				
				
				if(!rs.next()) {//Si no existe el usuario
						try {
							query = "INSERT INTO usuarios " +
								"VALUES('" +paramNombre+"', " +
								"'"+paramContra+"',0)";
							stmt.executeUpdate(query);//Ejecutamos la query para añadirlo a la base de datos

					} catch (SQLException e) {
						System.out.println("Error: "+ e.toString());
					}		
					jsp = "/index.jsp";//Nos devuelve al login
				}
				else {
					request.setAttribute(Login.MSG_ERROR,"Ya se encuentra ese usuario registrado");
				}
				
			con.close();//Cerramos siempre la conexion con la BD
				
			} catch (SQLException e) {
				System.out.println("Error: "+ e.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	
		}
		else {
			request.setAttribute(Login.MSG_ERROR,"Debe de enviar usuario y contraseña");
		}
		request.getRequestDispatcher(jsp).forward(request, response); //Redirige segun el valor de la String "jsp" como variable de control.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
