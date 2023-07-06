package edu.ucam.acciones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.servlets.Login;
import edu.ucam.us.Usuario;

public class AccionModificarUsuario  implements Accion {

	public static final String PARAM_NAME2 = "PARAM_NAME2";
	public static final String PARAM_PASS2 = "PARAM_PASS2";
		
	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsp = "/paneladmin.jsp";
		String paramNombre = request.getParameter(Login.PARAM_NAME);
		String paramNombreNuevo = request.getParameter(AccionModificarUsuario.PARAM_NAME2);
		String paramContraNueva = request.getParameter(AccionModificarUsuario.PARAM_PASS2);
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");
			if(!con.isClosed()) {
				System.out.println("Succesfully database connected");
			}
		
			String query = "SELECT * FROM usuarios WHERE username='" + paramNombre +"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if((!"".equals(paramNombre) &&  !"".equals(paramNombreNuevo) && !"".equals(paramContraNueva))) {
				if(rs.next()) {
						String query2 = "UPDATE usuarios SET username='" + paramNombreNuevo+ "',pass='"+paramContraNueva+"' WHERE username='" + paramNombre +"'";
						Statement stmt2 = con.createStatement();
						stmt2.executeUpdate(query2);
						System.out.println("user updated");
						
					
					
				}
				else {
					request.setAttribute(Login.MSG_ERROR, "No se encuentra ese nombre de usuario en el sistema");
				}
				con.close();
			}
			else {
				request.setAttribute(Login.MSG_ERROR, "Debe de enviar un usuario, usuario nuevo y contraseña nueva");
			}
		
		} catch (SQLException e) {
			System.out.println("Error: "+ e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsp;
		
	}

}
