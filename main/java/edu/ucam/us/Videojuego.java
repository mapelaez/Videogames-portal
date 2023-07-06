package edu.ucam.us;

import java.util.ArrayList;
import java.util.HashMap;

public class Videojuego {
	private String nombre;
	private Categoria categoria;
	private HashMap<String, Usuario> puntUsuarios;
	private HashMap<String, Copia> copias;
	private ArrayList<Integer> puntuacion;
	private float mediaPunt;
	
	
	public Videojuego(String nombre, Categoria categoria, HashMap<String, Usuario> puntUsuarios,
			HashMap<String, Copia> copias) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.puntUsuarios = puntUsuarios;
		this.copias = copias;
		this.puntuacion = new ArrayList<Integer>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public HashMap<String, Usuario> getPuntUsuarios() {
		return puntUsuarios;
	}
	public void setPuntUsuarios(HashMap<String, Usuario> puntUsuarios) {
		this.puntUsuarios = puntUsuarios;
	}
	public HashMap<String, Copia> getCopias() {
		return copias;
	}
	public void setCopias(HashMap<String, Copia> copias) {
		this.copias = copias;
	}

	public float getMediaPunt() {
		return mediaPunt;
	}

	public void setMediaPunt(float mediaPunt) {
		this.mediaPunt = mediaPunt;
	}
	
	
	
	
	public ArrayList<Integer> getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int valorInt) {
		this.puntuacion.add(valorInt);		
	}

	public void calcularMedia(ArrayList<Integer> puntuacion) {
		int acumulador = 0;
		float media = 0;
		for(int i = 0; i < puntuacion.size(); i++) {
			acumulador += puntuacion.get(i);
		}
		media = (float)acumulador/(float)puntuacion.size();
		this.setMediaPunt(media);
	}
	
}
