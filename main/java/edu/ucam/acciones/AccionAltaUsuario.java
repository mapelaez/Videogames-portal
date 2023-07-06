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

public class AccionAltaUsuario implements Accion {

	@Override
	public String ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String paramNombre = request.getParameter(Login.PARAM_NAME);//Recogemos el nombre del usuario
		String paramContra = request.getParameter(Login.PARAM_PASS);//Recpogemos la contraseña
		
		String jsp = "paneladmin.jsp";
		
		
		if((!"".equals(paramNombre) && !"".equals(paramContra))) {//Comprobación de campos vacios
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");	//Instancia del driver de oracle
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");//creamos una sesion de conexion con la BD
				if(!con.isClosed()) {
					System.out.println("Succesfully database connected");
				}
				
				String query = "SELECT username FROM usuarios WHERE username='" + paramNombre +"'";
				Statement stmt = con.createStatement();
				System.out.println("User found");
				ResultSet rs = stmt.executeQuery(query);//Ejecutamos la query y recogemos el resultado en "rs"
				
				//Si no existe el usuario....
				if(!rs.next()) {						
						try {
						
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con2 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");
						if(!con2.isClosed()) {
							System.out.println("Succesfully database connected");
						}
						
						String query2 = "INSERT INTO usuarios " +
								"VALUES('" +paramNombre+"', " +
								"'"+paramContra+"',0)";
						Statement stmt2 = con2.createStatement();
						stmt2.executeUpdate(query2);					//Ejecutamos la sentencia para insertar el usuario en la base de datos.
						System.out.println("user created");
						con2.close();
					
						
					} catch (SQLException e) {
						System.out.println("Error: "+ e.toString());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					
				}
				else {
					request.setAttribute(Login.MSG_ERROR,"Ya se encuentra ese nombre de usuario registrado");
				}
				
				
			con.close();
				
			} catch (SQLException e) {
				System.out.println("Error: "+ e.toString()+"User not found");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		else {
			request.setAttribute(Login.MSG_ERROR, "Debe de enviar un usuario y contraseña");
		}
		
		
		return jsp;
	}

}
