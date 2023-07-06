package edu.ucam.us;

public class Usuario {
	private String username;
	private String password;
	private boolean permiso;
	

	public Usuario(String username, String password, boolean permiso) {
		super();
		this.username = username;
		this.password = password;
		this.permiso = permiso;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isPermiso() {
		return permiso;
	}
	public void setPermiso(boolean permiso) {
		this.permiso = permiso;
	}
	
	
	
	
}
