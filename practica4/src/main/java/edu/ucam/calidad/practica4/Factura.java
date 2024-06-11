package edu.ucam.calidad.practica4;

import java.io.Serializable;
import java.util.ArrayList;

public class Factura implements Serializable{

	private String codigo;
	private String precio;
	private ArrayList<Cliente> clientesEnFactura;
	private ArrayList<Producto> productosEnFactura;
	
	public Factura(String codigo, String precio) {
		this.codigo = codigo;
		this.precio = precio;
		this.clientesEnFactura = new ArrayList<Cliente>();
		this.productosEnFactura = new ArrayList<Producto>();
	}

	
	public String toString() {
		String string = "Codigo: "+ this.codigo+ ", Precio: "+ this.precio + this.clientesEnFactura.toString() + this.productosEnFactura.toString() +"\n";
		return string;
	}
	
	public String getCodigo() {
		return codigo;
	}	
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPrecio() {
		return this.precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	public ArrayList<Cliente> getClientes(){
		return this.clientesEnFactura;
	}
	
	public ArrayList<Producto> getProductos(){
		return this.productosEnFactura;
	}
	
	public void setClientes(Cliente cliente) {
		this.clientesEnFactura.add(cliente);
	}
	
	public void setProductos(Producto producto) {
		this.productosEnFactura.add(producto);
	}
	public int removeCliente(String DNI) {
		int borrado = 1;
		for(int i=0; i<clientesEnFactura.size(); i++) {
			if(clientesEnFactura.get(i).getDNI().equals(DNI)) {
				clientesEnFactura.remove(i);
				borrado = 2;
			}
		}
		return borrado;
	}
	public int removeProducto(String ref) {
		int borrado = 1;
		for(int i=0; i<productosEnFactura.size(); i++) {
			if(productosEnFactura.get(i).getRef().equals(ref)) {
				productosEnFactura.remove(i);
				borrado = 2;
			}
		}
		return borrado;
	}
}


