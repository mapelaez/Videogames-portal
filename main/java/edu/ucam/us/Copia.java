package edu.ucam.us;

public class Copia {
	private String id;
	private estadoVideojuego estado;
	
	
	
	public Copia(String id, estadoVideojuego estado) {
		super();
		this.id = id;
		this.estado = estado;
	}
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public estadoVideojuego getEstado() {
		return estado;
	}
	public void setEstado(estadoVideojuego estado) {
		this.estado = estado;
	}
	 
	
	
}
