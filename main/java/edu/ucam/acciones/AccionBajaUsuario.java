package edu.ucam.acciones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.servlets.Login;
import edu.ucam.us.Usuario;

public class AccionBajaUsuario  implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {
		
		String paramName = request.getParameter(Login.PARAM_NAME); //Recogemos el nombre de usuario de la peticion
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");
			if(!con.isClosed()) {
				System.out.println("Succesfully database connected");
			}
		
			String query = "DELETE FROM usuarios WHERE username='" + paramName +"'";//usamos el nombre para encontrar la fila en la BD
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);												//Ejecutamos la sentencia SQL
			System.out.println("usuario eliminado");

		} catch (SQLException e) {
			System.out.println("Error: "+ e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String jsp = "/paneladmin.jsp"; //Nos devuelve al panel de admin
		return jsp;
	}
	
}
