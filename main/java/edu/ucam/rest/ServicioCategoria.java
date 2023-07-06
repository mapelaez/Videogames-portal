package edu.ucam.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PUT;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONObject;

import edu.ucam.acciones.AccionAgregarCategoria;
import edu.ucam.acciones.AccionAgregarVideojuego;
import edu.ucam.us.Categoria;
import edu.ucam.us.Videojuego;

@Path("/categoria")
public class ServicioCategoria {

	
	public ServicioCategoria() {
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCategorias(@javax.ws.rs.core.Context HttpServletRequest req) {
		//Se crea un objeto para generar el JSON con la respuesta
		JSONObject jsonRespuesta = new JSONObject();
		HashMap<String,Categoria> categorias = (HashMap<String,Categoria>)req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		for(Categoria categoria : categorias.values()) {
			
			JSONObject categoriaJSON = new JSONObject();
			
			categoriaJSON.put("nombre", categoria.getNombre());
			//Se añade al JSON la respuesta
			jsonRespuesta.append("categorias", categoriaJSON);
		}
		
		return Response.status(201).entity(jsonRespuesta.toString()).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarCategoria(InputStream incomingData, @javax.ws.rs.core.Context HttpServletRequest req) {
			//Se crea un objeto para generar el JSON con la respuesta
			JSONObject jsonRespuesta = new JSONObject();
			HashMap<String,Categoria> categorias = (HashMap<String,Categoria>)req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);

			//Recuperamos el String correspondiente al JSON que nos envia el navegador
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				System.out.println("Error Parsing: - ");
			}
					
				
			//Construimos un objeto JSON en base al recibido como cadena 
			JSONObject jsonRecibido = new JSONObject(sb.toString());
		
			Categoria categoria = new Categoria(jsonRecibido.getString("nombre"),new HashMap<String, Videojuego>());
				
			//Comprobamos si se encuentra vacio el campo de nombreCat
			if(!("".equals(jsonRecibido.getString("nombre")))){
				//Comprobamos si se encuentra el nombre de la nueva categoria en nuestro HashMap
				if(!(categorias.containsKey(categoria.getNombre()))) {
						
					JSONObject categoriaJSON = new JSONObject();
					categoriaJSON.put("nombre", categoria.getNombre());
					//Se añade al JSON la respuesta
					jsonRespuesta.put("categoria", categoriaJSON);
					categorias.put(categoria.getNombre(), new Categoria(categoria.getNombre(),new HashMap<String,Videojuego>()));
					req.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
					
					return Response.status(201).entity(jsonRespuesta.toString()).build();
				}
				else {
					jsonRespuesta.put("resultado", "La categoria ya existe");
					return Response.status(409).entity(jsonRespuesta.toString()).build();
				}
			}
			else {
				jsonRespuesta.put("resultado", "Debe de enviar un nombre de categoria");
				return Response.status(409).entity(jsonRespuesta.toString()).build();
			}
				
	}
	
	@DELETE
	@Path("/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrarCategoria(@PathParam("nombre") String nombre,  @javax.ws.rs.core.Context HttpServletRequest req) {
		HashMap<String,Categoria> categorias = (HashMap<String,Categoria>)req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		Categoria categoria = categorias.get(nombre);
		
		JSONObject jsonRespuesta = new JSONObject();
		
		
		//Comprobamos si los videojuegos de la categoria se encuentran vacio
		if(categoria.getVideojuegos().isEmpty()) {
			categorias.remove(nombre);
			req.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
			jsonRespuesta.put("resultado", "Borrado");
			return Response.status(204)
					.header("Access-Control-Allow-Origin", "*")
					.entity(jsonRespuesta.toString())
					.build();
		}
		else {
			jsonRespuesta.put("resultado", "Esta categoria contiene videojuegos, por lo que no se puede borrar");
			return Response.status(409).entity(jsonRespuesta.toString()).build();
		}
		
	}
	
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response modificarCategoria(InputStream incomingData, @javax.ws.rs.core.Context HttpServletRequest req) {
			
			JSONObject jsonRespuesta = new JSONObject();
			HashMap<String,Categoria> categorias = (HashMap<String,Categoria>)req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
			HashMap<String,Videojuego> videojuegos = (HashMap<String,Videojuego>)req.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
			Categoria categoria;
			
			
			//Recuperamos el String correspondiente al JSON que nos envia el navegador
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				System.out.println("Error Parsing: - ");
			}
			
	
			//Construimos un objeto JSON en base al recibido como cadena 
			JSONObject jsonRecibido = new JSONObject(sb.toString());
		
			String nombreAntiguo = jsonRecibido.getString("nombre");
			String nombreNuevo = jsonRecibido.getString("nombre2");
		
		
		//Comprobamos si se encuentra vacias o no las casillas para modificar datos
		if((!"".equals(nombreAntiguo) && !"".equals(nombreNuevo))) {
			
			//Comprobamos si se encuentra el nombre introducido (el antiguo)
			if(categorias.containsKey(nombreAntiguo)) {
				
				//Comprobamos si no se encuentra el nombre introducido (el nuevo al que se quiere modificar)
				if(!(categorias.containsKey(nombreNuevo))){
					
					
					HashMap<String,Videojuego> videojuegosCat = categorias.get(nombreAntiguo).getVideojuegos();
					categorias.remove(nombreAntiguo);
					
					//Modificamos los videojuegos pertenecientes a la categoría con el nuevo nombre.
					for(Videojuego videojuego: videojuegosCat.values()) {
						videojuego.getCategoria().setNombre(nombreNuevo);
					}
					
					categorias.put(nombreNuevo, new Categoria(nombreNuevo,videojuegosCat));
					categoria = categorias.get(nombreNuevo);
					
					//Modificamos los videojuegos del HashMap global que tenian la categoria antigua 
					for(Videojuego videojuego: videojuegos.values()) {
						if(videojuego.getCategoria().getNombre().equals(nombreNuevo)) {
							videojuego.setCategoria(categoria);
						}
					}
					
					req.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG,categorias);
					req.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
					
					JSONObject categoriaJSON = new JSONObject();
					categoriaJSON.put("nombre", categoria.getNombre());
					//Se añade al JSON la respuesta
					jsonRespuesta.put("categoria", categoriaJSON);
					return Response.status(201).entity(jsonRespuesta.toString()).build();
					
				}
				else {
					jsonRespuesta.put("resultado", "Ya se encuentra una categoria con ese nombre.");
					return Response.status(409).entity(jsonRespuesta.toString()).build();
				}
			}
			else {
				jsonRespuesta.put("resultado", "No se encuentra una categoria con ese nombre.");
				return Response.status(409).entity(jsonRespuesta.toString()).build();
			}
		}
		else {
			jsonRespuesta.put("resultado", "Debe de enviar el nombre de categoria antiguo y el nuevo al que se quiere modificar.");
			return Response.status(409).entity(jsonRespuesta.toString()).build();
		}
		
	}
	



}
	
	
	

