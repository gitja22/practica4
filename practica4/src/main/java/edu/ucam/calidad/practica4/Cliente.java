package edu.ucam.calidad.practica4;

import java.io.Serializable;

public class Cliente implements Serializable{
	
	private String nombre;
	private String DNI;
	
	public Cliente(String nuevoNombre, String nuevoDNI) {
		this.nombre = nuevoNombre;
		this.DNI = nuevoDNI;
	}

	public String toString() {
		String string = "nombre: "+ this.nombre+ ", DNI: "+this.DNI + "\n";
		return string;
	}
		
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

}
