package edu.ucam.calidad.practica4;

import java.io.Serializable;

public class Producto implements Serializable{
	private String nombre;
	private String ref;
	
	
	public Producto(String nombre, String ref) {
		this.nombre = nombre;
		this.ref = ref;
	}

	public String toString() {
		String string = "nombre: "+ this.nombre+ ", Referencia: "+this.ref + "\n";
		return string;
	}
		
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
}
