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

public class AccionModificarPermisos  implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response) {

		String jsp = "/paneladmin.jsp";
		String paramNombre = request.getParameter(Login.PARAM_NAME);											//Recogemos el valor asignado en el listado, al objeto en cuestion
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");	//Conectamos con la base de datos
			if(!con.isClosed()) {
				System.out.println("Succesfully database connected");
			}
		
			String query = "SELECT * FROM usuarios WHERE username='" + paramNombre +"'";	
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);															//Recuperamos el usuario
			if(rs.next()) {
				if(rs.getInt(3) == 1) {																			//Cambia los permisos del usuario 1 o 0
					String query2 = "UPDATE usuarios SET permiso=0 WHERE username='" + paramNombre +"'";		
					Statement stmt2 = con.createStatement();
					stmt2.executeUpdate(query2);
					System.out.println("user updated");
					con.close();
				}
				else {
					String query3 = "UPDATE usuarios SET permiso=1 WHERE username='" + paramNombre +"'";
					Statement stmt3 = con.createStatement();
					stmt3.executeUpdate(query3);
					System.out.println("user updated");
					con.close();
				}
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

