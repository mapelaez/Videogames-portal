package edu.ucam.tags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import edu.ucam.servlets.Control;
import edu.ucam.servlets.Login;
import edu.ucam.us.Usuario;

public class ListarUsuarios extends BodyTagSupport{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");
			
			String query = "SELECT * FROM usuarios";//Recogemos los usuarios de la BD
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			pageContext.getOut().println("<h3> Usuarios sin permisos <h3>");
			
			//recorremos los usuarios, mostrandolos en pantalla.
			while(rs.next()){						
						if(!(rs.getString(1).equals("admin"))){
							if(rs.getInt(3) == 0){
								pageContext.getOut().println(rs.getString(1) + " - " + rs.getString(2)
									+ " <a href=\"control?"
									+ Control.PARAM_ACTION_ID + "=BAJA_USUARIO&"+ Login.PARAM_NAME+"=" + rs.getString(1)
									+ "\" >Borrar</a>"
									+ " <a href=\"control?"
									+ Control.PARAM_ACTION_ID + "=MODIFICAR_PERMISO&"+ Login.PARAM_NAME+"=" + rs.getString(1)
									+ "\" > Conceder Permiso </a>" //Boton que le devuelve a Control la accion MODIFICAR PERMISO
									+ "<br>");
							}
						}
					
				}
			
			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query);
				
			pageContext.getOut().println("<h3> Usuarios con permisos <h3>");
			while(rs2.next()){
						if(!(rs2.getString(1).equals("admin"))){
							if(rs2.getInt(3) == 1){
								pageContext.getOut().println(rs2.getString(1) + " - " + rs2.getString(2)
									+ " <a href=\"control?"
									+ Control.PARAM_ACTION_ID + "=BAJA_USUARIO&"+ Login.PARAM_NAME+"=" + rs2.getString(1)
									+ "\" >Borrar</a>"
									+ " <a href=\"control?"
									+ Control.PARAM_ACTION_ID + "=MODIFICAR_PERMISO&"+ Login.PARAM_NAME+"=" + rs2.getString(1)
									+ "\" > Quitar Permiso </a>" 
									+ "<br>");
							}
						}
					
			}
				
				pageContext.getOut().println("<a href=\"control?" + Control.PARAM_ACTION_ID + "=LOGOUT" + "\" > Cerrar Sesión </a>") ;
				con.close();
		}
		catch(Exception e) {
		return ListarUsuarios.SKIP_BODY;
		}		
		return ListarUsuarios.EVAL_BODY_INCLUDE;
		
	}
}
