package edu.ucam.us;

import java.util.HashMap;

public class Categoria {
	
	private String nombre; 
	private HashMap<String,Videojuego> videojuegos;
	
	
	
	
	public Categoria(String nombre, HashMap<String, Videojuego> videojuegos) {
		this.nombre = nombre;
		this.videojuegos = videojuegos;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public HashMap<String, Videojuego> getVideojuegos() {
		return videojuegos;
	}
	public void setVideojuegos(HashMap<String, Videojuego> videojuegos) {
		this.videojuegos = videojuegos;
	}

	
	
}
