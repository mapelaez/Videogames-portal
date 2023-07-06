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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import edu.ucam.acciones.AccionAgregarCategoria;
import edu.ucam.acciones.AccionAgregarVideojuego;
import edu.ucam.us.Categoria;
import edu.ucam.us.Copia;
import edu.ucam.us.Usuario;
import edu.ucam.us.Videojuego;

@Path("/videojuego")
public class ServicioVideojuego {

	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegos(@javax.ws.rs.core.Context HttpServletRequest req) {
		//Se crea un objeto para generar el JSON con la respuesta
		JSONObject jsonRespuesta = new JSONObject();
		HashMap<String,Videojuego> videojuegos = (HashMap<String,Videojuego>) req.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		for(Videojuego videojuego : videojuegos.values()) {
			
			JSONObject videojuegoJSON = new JSONObject();
			
			videojuegoJSON.put("nombreVid", videojuego.getNombre());
			videojuegoJSON.put("nombreCat", videojuego.getCategoria().getNombre());
			
			//Se añade al JSON la respuesta
			jsonRespuesta.append("videojuegos", videojuegoJSON);
		}
		return Response.status(201).entity(jsonRespuesta.toString()).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarVideojuego(InputStream incomingData, @javax.ws.rs.core.Context HttpServletRequest req) {
		
		//Se crea un objeto para generar el JSON con la respuesta
		JSONObject jsonRespuesta = new JSONObject();
		
		HashMap<String,Videojuego> videojuegos = (HashMap<String,Videojuego>)req.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		
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
		
		String nombreCat = jsonRecibido.getString("nombreCat");
		String nombreVid = jsonRecibido.getString("nombreVid");
		
		//Comprobamos si se encuentra vacio el formulario
		if((!"".equals(nombreVid) && !"".equals(nombreCat))) {
			
			//Comprobamos si no se encuentra ese nombre en el HashMap
			if(!(videojuegos.containsKey(nombreVid))) {
				
				//Comprobamos si se encuentra la categoria en el HashMap
				if(categorias.containsKey(nombreCat)) {
					
					Categoria categoria = categorias.get(nombreCat);
					
					//Añadimos al HashMap de videojuegos
					videojuegos.put(nombreVid,new Videojuego(nombreVid,categoria, new HashMap<String, Usuario>(), new HashMap<String, Copia>()));
					Videojuego videojuego = videojuegos.get(nombreVid);
					
					//Añadimos al HashMap de categorias que contiene videojuegos
					categorias.get(nombreCat).getVideojuegos().put(nombreVid,videojuego);
					
					req.setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
					req.setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
					
					JSONObject videojuegoJSON = new JSONObject();
					
					videojuegoJSON.put("nombreVid", videojuego.getNombre());
					videojuegoJSON.put("nombreCat", videojuego.getCategoria().getNombre());
					
					//Se añade al JSON la respuesta
					jsonRespuesta.put("videojuego", videojuegoJSON);
				
					return Response.status(201).entity(jsonRespuesta.toString()).build();				
				}
				else {
					jsonRespuesta.put("resultado", "No existe esa categoria");
					return Response.status(409).entity(jsonRespuesta.toString()).build();
				}
			}
			else {
				jsonRespuesta.put("resultado", "Ya existe un videojuego con ese nombre");
				return Response.status(409).entity(jsonRespuesta.toString()).build();
			}
		}
		else {
			jsonRespuesta.put("resultado", "Debe de enviar un nombre de categoria y un nombre de videojuego.");
			return Response.status(409).entity(jsonRespuesta.toString()).build();
		}
		
	}
	
	
	@DELETE
	@Path("/{nombreVid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrarVideojuego(@PathParam("nombreVid") String nombreVid,InputStream incomingData, @javax.ws.rs.core.Context HttpServletRequest req) {
		
		//Se crea un objeto para generar el JSON con la respuesta
		JSONObject jsonRespuesta = new JSONObject();
		
		HashMap <String,Videojuego> videojuegos = (HashMap<String, Videojuego>) req.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		HashMap <String, Categoria> categorias = (HashMap<String,Categoria>) req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		
		videojuegos.remove(nombreVid);
		
		for(Categoria categoria : categorias.values()) {
			if(categoria.getVideojuegos().containsKey(nombreVid)){
				categoria.getVideojuegos().remove(nombreVid);
			}
		}
		
		jsonRespuesta.put("resultado", "Borrado");
		return Response.status(204)
				.header("Access-Control-Allow-Origin", "*")
				.entity(jsonRespuesta.toString())
				.build();
		
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modificarVideojuego(InputStream incomingData, @javax.ws.rs.core.Context HttpServletRequest req) {
		
		//Se crea un objeto para generar el JSON con la respuesta
		JSONObject jsonRespuesta = new JSONObject();
		
		HashMap<String,Categoria> categorias = (HashMap<String,Categoria>) req.getServletContext().getAttribute(AccionAgregarCategoria.ATR_CATEG);
		HashMap <String,Videojuego> videojuegos = (HashMap<String, Videojuego>) req.getServletContext().getAttribute(AccionAgregarVideojuego.ATR_VIDEO);
		
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
		
		String nombreNuevaCat = jsonRecibido.getString("nombreCat");
		String nombreVid = jsonRecibido.getString("nombreVid1");
		String nombreNuevoVid = jsonRecibido.getString("nombreVid2");
		
		//Comprobamos si se encuentra vacias o no las casillas para modificar datos
		if((!"".equals(nombreNuevaCat) && !"".equals(nombreVid) && !"".equals(nombreNuevoVid))) {
			
			if(videojuegos.containsKey(nombreVid)) {
				
				//Comprobamos si no se encuentra el nuevo nombre en el HashMap de videojuegos
				if(!(videojuegos.containsKey(nombreNuevoVid))) {
				
					if(categorias.containsKey(nombreNuevaCat)) {
						
						Categoria categoria = categorias.get(nombreNuevaCat);
						
						//Recuperamos las copias y usuarios del antiguo videojuego.
						HashMap <String,Copia> copias = videojuegos.get(nombreVid).getCopias();
						HashMap <String,Usuario> usuarios = videojuegos.get(nombreVid).getPuntUsuarios();
						
						//Borramos y actualizamos con el nuevo nombre
						videojuegos.remove(nombreVid);
						videojuegos.put(nombreNuevoVid, new Videojuego(nombreNuevoVid,categoria,usuarios,copias));
						
						//Recorremos el HashMap de categorias para cambiar al nuevo contenido de videojuegos.
						for(Categoria categoriaRecorrer : categorias.values()) {
							
							//Comprobamos que el HashMap de categoria contiene el nombre del antiguo videojuego.
							if(categoriaRecorrer.getVideojuegos().containsKey(nombreVid)) {
								categoriaRecorrer.getVideojuegos().remove(nombreVid);
							}
							
							if(categoriaRecorrer.getNombre().equals(nombreNuevaCat)) {
								categoriaRecorrer.getVideojuegos().put(nombreNuevoVid, new Videojuego(nombreNuevoVid,categoria,usuarios,copias));
							}
						}
						
						req.getServletContext().setAttribute(AccionAgregarVideojuego.ATR_VIDEO, videojuegos);
						req.getServletContext().setAttribute(AccionAgregarCategoria.ATR_CATEG, categorias);
						
						JSONObject videojuegoJSON = new JSONObject();
						
						videojuegoJSON.put("nombreVid2", videojuegos.get(nombreNuevoVid).getNombre());
						videojuegoJSON.put("nombreCat", videojuegos.get(nombreNuevoVid).getCategoria().getNombre());
						
						//Se añade al JSON la respuesta
						jsonRespuesta.put("videojuego", videojuegoJSON);
						
						return Response.status(201).entity(jsonRespuesta.toString()).build();
						
					}
					else {
						jsonRespuesta.put("resultado", "No se encuentra una categoria con ese nombre");
						return Response.status(409).entity(jsonRespuesta.toString()).build();
					}
				}
				else {
					jsonRespuesta.put("resultado", "Ya se encuentra un videojuego con ese nombre");
					return Response.status(409).entity(jsonRespuesta.toString()).build();
				}
					
			}
			else {
				jsonRespuesta.put("resultado", "No se encuentra un videojuego con ese nombre");
				return Response.status(409).entity(jsonRespuesta.toString()).build();
			}
		}
		else {
			jsonRespuesta.put("resultado", "Debe de enviar un nombre de categoria y un nombre de videojuego.");
			return Response.status(409).entity(jsonRespuesta.toString()).build();
		}
	}
	
}
