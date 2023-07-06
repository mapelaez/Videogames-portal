package edu.ucam.servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.us.Usuario;

/**
 * Servlet implementation class Login
 */
@WebServlet(value={"/login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String PARAM_NAME = "PARAM_NAME";
	public static final String PARAM_PASS = "PARAM_PASS";
	
	public static final String MSG_ERROR = "MSG_ERROR";
	public static final String ATR_USER = "ATR_USER";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramNombre = request.getParameter(PARAM_NAME);//Variables donde recogemos los datos de los campos de texto en la peticion
		String paramContra = request.getParameter(PARAM_PASS);//Variables donde recogemos los datos de los campos de texto en la peticion
		String jsp = "/index.jsp";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");	//Driver de oracle
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root"); //Hacemos la conexion con la base de datos
		
		
			String query = "SELECT * FROM usuarios WHERE username='" + paramNombre +"'";//Cadena SQL que devuelve el usuario si existe.
			Statement stmt = con.createStatement(); //Objeto usado para ejecutar una sentencia SQL y devolver resultado.
			ResultSet rs = stmt.executeQuery(query); //Guardamos el resultado de la query en "rs"
			
			if((!"".equals(paramNombre) && !"".equals(paramContra))) { //Comprobacion de campos vacios
					
				if(rs.next()) { //Si existe en la base de datos...
					if(paramContra.equals(rs.getString(2))) { //Comprobamos la contraseña
						if(rs.getInt(3) == 1) { 
								request.getSession().setAttribute(Login.ATR_USER, new Usuario(rs.getString(1),rs.getString(2),true)); //Creamos la sesion del usuario role-based
								jsp = "/paneladmin.jsp";
							}
							else {
								request.getSession().setAttribute(Login.ATR_USER, new Usuario(rs.getString(1),rs.getString(2),false)); //Creamos la sesion del usuario role-based
								jsp = "/panelusuario.jsp";
							}
						
						}else                                                                             
							request.setAttribute(Login.MSG_ERROR, "Clave incorrecta");
							
					}else {
						request.setAttribute(Login.MSG_ERROR, "Usuario no existe");
					}
				con.close();
					
				}
				else {
					request.setAttribute(Login.MSG_ERROR, "Debe de enviar un usuario y contraseña");
				}

			
		} catch (SQLException e) {
			System.out.println("Error: "+ e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher(jsp).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
