package edu.ucam.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucam.acciones.Accion;
import edu.ucam.us.Usuario;


/**
 * Servlet implementation class Control
 */
@WebServlet("/control")
public class Control extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PARAM_ACTION_ID = "ACTION_ID";
       
	private Hashtable<String, Accion> acciones = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Control() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		if (this.acciones == null) {
			
			this.acciones = new Hashtable<String, Accion>();
			
			
			String realPath = config.getServletContext().getRealPath("WEB-INF\\acciones.properties");//asignamos la ruta de nuestro archivo properties para cargarlo a la aplicacion
			
			Properties ficheroAcciones = new Properties();
			try {
				ficheroAcciones.load(new FileInputStream(new File(realPath)));//Cargamos el fichero en la ruta determinada.
					
				String propiedadIDS = ficheroAcciones.getProperty("IDS");//Recogemos el campo "IDS"
				
				String [] ids = propiedadIDS.split(" ");//Almacenamos el campo "IDS" como un array de elementos.
				
				for(int i = 0; i < ids.length ; i++) {//Para cada accion
				
					String idClase = ids[i];//Guardamos el valor del campo IDS de nuestro fichero
					String nombreClase = ficheroAcciones.getProperty(idClase);//Recogemos del fichero la direccion completa de la accion

					Class<?> clase = (Class<?>) Class.forName(nombreClase);//Creamos una clase del tipo accion y la inicializamos 
					Constructor<?> constructor = clase.getConstructor();
					
									
					Accion accion = (Accion)constructor.newInstance();//Ejecutamos una instancia de la clase Ej: ALTA_USUARIO
					
					this.acciones.put(idClase,accion);//Guardamos la accion en nuestro Hashtable que usamos en el contexto.
					
					System.out.println("Accion " 
										+ ficheroAcciones.getProperty(ids[i])
										+ " con id " + ids[i]);
				}		
				
				
				
			} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		
		super.init(config);
	}
		

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String jsp;
		Usuario user = (Usuario) request.getSession().getAttribute(Login.ATR_USER);//Se recupera el usuario actual
		
		String paramActionID = request.getParameter(Control.PARAM_ACTION_ID);//Se recupera la ID de la accion actual
		System.out.println(paramActionID);
		
		Accion accion = this.acciones.get(paramActionID);//Recuperamos el objeto accion
		if(user!=null) {//Si hay usuario en la sesion...
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");	
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","java","root"); //Realizamos la conexion
			
				String query = "SELECT * FROM usuarios WHERE username='" + user.getUsername() +"'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);				//Ejecutamos la sentencia SQL
				if(rs.next()) {											//Si existe un resultado...
					
					//Comprobamos que sea una accion disponible.
					if(paramActionID.equals("ALTA_USUARIO") || paramActionID.equals("BAJA_USUARIO") || paramActionID.equals("MODIFICAR_USUARIO") || paramActionID.equals("MODIFICAR_PERMISO")) {
						
						//Comprobamos que el usuario tenga permisos
						//directamente desde el campo recogido de la base de datos.
						if(rs.getInt(3)==1) {
							jsp = accion.ejecutar(request, response);//llamamos a la interfaz accion y ejecutamos la accion actual
							request.getRequestDispatcher(jsp).forward(request, response);
						}
						else {
							jsp = "ErrorControl.jsp";
							request.getRequestDispatcher(jsp).forward(request, response);
						}
					}
					else {
						jsp = accion.ejecutar(request, response);
						request.getRequestDispatcher(jsp).forward(request, response);
					}
				}else {
					jsp = "ErrorControl.jsp";
					request.getRequestDispatcher(jsp).forward(request, response);
				}
				
	
			} catch (SQLException e) {
				System.out.println("Error: "+ e.toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}else {
		jsp = "ErrorControl.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
	}
		
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
