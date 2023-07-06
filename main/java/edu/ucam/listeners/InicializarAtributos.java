package edu.ucam.listeners;

import java.sql.*;
import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.ucam.acciones.AccionAgregarCategoria;
import edu.ucam.acciones.AccionAgregarVideojuego;
import edu.ucam.servlets.Login;
import edu.ucam.us.Categoria;
import edu.ucam.us.Usuario;
import edu.ucam.us.Videojuego;

/**
 * Application Lifecycle Listener implementation class InicializarAtributos
 *
 */
@WebListener
public class InicializarAtributos implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InicializarAtributos() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("Inicializando atributos de contexto...");
    	
    	
    	//Inicializar BBDD
    	
    	try {
    		
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");
    		String query = "CREATE TABLE usuarios (username varchar(255),pass varchar(255),permiso int, PRIMARY KEY (username))";
    		Statement stmt = con.createStatement(); //Creamos la tabla de usuarios
    		stmt.executeUpdate(query);
    		con.close();
    	
    		
    	} catch (SQLException e) {
    		System.out.println("Error: "+ e.toString());
    	} catch (ClassNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	try {
    		
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root");

    		
    		String query = "INSERT INTO usuarios " +
    		"VALUES('admin', " +
    		"'admin',1)";
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate(query); //Añadimos el usuario admin a la aplicacion
    		System.out.println("Admin creado");
    		con.close();
    	
    		
    	} catch (SQLException e) {
    		System.out.println("Error: "+ e.toString());
    	} catch (ClassNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	HashMap<String,Categoria> categorias = new HashMap<String,Categoria>();
		arg0.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
		HashMap<String, Videojuego> videojuegos = new HashMap<String, Videojuego>();
		arg0.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
    }
	
}
