package edu.ucam.calidad.practica4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Servidor {

	private Hashtable<String, String> tablaClientes = new Hashtable();
	private ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	private ArrayList<Factura> listaFacturas = new ArrayList<Factura>();
	private ArrayList<Producto> listaProductos = new ArrayList<Producto>();
	private ServerSocket serverDatos;
	private ServerSocket serverSocket;
	private Socket socket;
	private int port;
	
	
	public Servidor() {
		port =2023;
		tablaClientes.put("user", "pass");
		Cliente cliente0 = new Cliente("Jose","123Q");
		listaClientes.add(cliente0);
		Factura factura0 = new Factura("1111","10�");
		listaFacturas.add(factura0);
		Producto producto0 = new Producto("mesa","1234");
		listaProductos.add(producto0);
		
		try {
			
			this.serverSocket = new ServerSocket(2022);
			
			HiloServidor hiloAux;
			while(true) {
				this.socket = serverSocket.accept();					
				hiloAux = new HiloServidor(socket, this);
				hiloAux.start();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public boolean reconocerUsuario(BufferedReader br, PrintWriter pw) {
		
		boolean flag = false;
		String instruccion = "";		
		String [] partes;
		
			try {
				instruccion = br.readLine();
				partes = instruccion.split(" ");
				
				String user = partes[2];
				if(partes[1].equals("USER")){
					if(tablaClientes.containsKey(user)){
						pw.println("OK");
						pw.flush();
						
						instruccion = br.readLine();
						partes = instruccion.split(" ");
						String pass = partes[2];
						if(partes[1].equals("PASS")){
							if(pass.equals(tablaClientes.get(user))){
								pw.println("OK");
								pw.flush();
								flag = true;
							}else {
								pw.println("FALSE");
								pw.flush();
							}
						}else {
							pw.println("FALSE");
							pw.flush();
						}
					}else {
						pw.println("FALSE");
						pw.flush();
					}
				}else {
					pw.println("FALSE");
					pw.flush();
				}						
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return flag;			
	}
	
	public synchronized String addCliente(String id, PrintWriter pw) {
		boolean controlador = false;
		Cliente cliente;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 001 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
			
			try {
				cliente = (Cliente)ios.readObject();
				
				
				for(int i=0; i<listaClientes.size(); i++) {
					if(cliente.getDNI().equals(listaClientes.get(i).getDNI())) {
						controlador = true;
					}
				}						
				if(controlador==false) {
					listaClientes.add(cliente);						
					System.out.println(listaClientes.toString());
					return("OK "+ id +" 201 Ese_cliente_ya_ha_sido_a�adido");
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return("FAILED "+ id +" 001 Cliente_repetido_satisfactoriamente");
	}
	
	public synchronized String updateCliente(String id, String dni, PrintWriter pw) {	
		boolean encontrado = false;	
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id +" 002 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
			
			
			Cliente cliente;
			try {
				cliente = (Cliente)ios.readObject();
				for(int i=0; i<listaClientes.size(); i++) {
					if(listaClientes.get(i).getDNI().equals(dni)) {
						listaClientes.remove(i);
						listaClientes.add(i, cliente);
						encontrado = true;
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
						
			System.out.println(listaClientes.toString());
				
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(encontrado == true) {
			return("OK "+id+" 002 Cliente_modificado_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 402 Cliente_no_encontrado.");
		}		
			
	}
	
	public synchronized String getCliente(String id, String dni, PrintWriter pw) {
		boolean encontrado = false;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 003 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			
			for(int i=0; i<listaClientes.size(); i++) {
				if(listaClientes.get(i).getDNI().equals(dni)) {
					oos.writeObject(listaClientes.get(i));
					oos.flush();
					encontrado = true;
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(encontrado == true) {
			return("OK "+id+" 003 Cliente_entregado_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 403 Cliente_no_encontrado.");
		}
	}
	
	public synchronized String removeCliente(String id, String dni) {
		
		boolean encontrado = false;
		
		for(int i=0; i<listaClientes.size(); i++) {
			if(listaClientes.get(i).getDNI().equals(dni)) {
				listaClientes.remove(i);
				encontrado = true;
			}
		}
		
		System.out.println(listaClientes.toString());
		
		if(encontrado == true) {
			return("OK "+id+" 005 Cliente_eliminado_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 405 Cliente_no_encontrado.");
		
		}
	}
	
	public synchronized String listClientes(String id, PrintWriter pw) {
		
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 004 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			
			oos.writeObject(listaClientes);
			oos.flush();					
							
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("OK "+id+" 004 Lista_clientes_entregada_satisfactoriamente");
	}
	
	public synchronized String countClientes(String id) {
		
		return("OK "+id+" 006 N�mero_clientes:"+listaClientes.size());
		
	}
	
	public synchronized String addFactura(String id, PrintWriter pw) {
		Factura factura;
		boolean controlador = false;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 007 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
			try {
				factura = (Factura)ios.readObject();
				
				for(int i=0; i<listaFacturas.size(); i++) {
					if(factura.getCodigo().equals(listaFacturas.get(i).getCodigo())) {
						controlador = true;
					}
				}
				if(controlador == false) {
					listaFacturas.add(factura);	
					System.out.println(listaFacturas.toString());
					return("OK "+id+" 007 Factura_a�adida_satisfactoriamente");
				}
				
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return("FAILED "+id+" 007 Esta_factura_ya_se_encuentra_registrada");
	}
	
	public synchronized String getFactura(String id, String dni, PrintWriter pw) {
		boolean encontrado = false;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 008 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			
			for(int i=0; i<listaFacturas.size(); i++) {
				if(listaFacturas.get(i).getCodigo().equals(dni)) {
					oos.writeObject(listaFacturas.get(i));
					oos.flush();
					encontrado = true;
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(encontrado == true) {
			return("OK "+id+" 008 Factura_entregada_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 408 Factura_no_encontrada.");
			
		}	
		
	}
	
	public synchronized String listFacturas(String id, PrintWriter pw) {
		
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 009 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			
			oos.writeObject(listaFacturas);
			oos.flush();					
							
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("OK "+id+" 009 Lista_facturas_entregada_satisfactoriamente");
	}
	
	public synchronized String removeFactura(String id, String dni) {
		
		boolean encontrado = false;
		
		for(int i=0; i<listaFacturas.size(); i++) {
			if(listaFacturas.get(i).getCodigo().equals(dni)) {
				listaFacturas.remove(i);
				encontrado = true;
			}
		}
		
		System.out.println(listaFacturas.toString());
		
		if(encontrado == true) {
			return("OK "+id+" 009 Factura_eliminada_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 409 Factura_no_encontrada.");
			
		}
	}
	
	public synchronized String addProducto(String id, PrintWriter pw) {
		Producto producto;
		boolean controlador = false;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 010 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
			try {
				producto = (Producto)ios.readObject();
				
				for(int i=0; i<listaProductos.size(); i++) {
					if(producto.getRef().equals(listaProductos.get(i).getRef())) {
						controlador = true;
					}
				}
				
				if(controlador == false) {
					listaProductos.add(producto);	
					System.out.println(listaProductos.toString());
					return("OK "+id+" 210 Producto_a�adido_satisfactoriamente");
				}
				
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return("FAILED "+id+" 010 El_producto_ya_se_encuentra_a�adido");
		
	}
	
	public synchronized String updateProducto(String id, String dni, PrintWriter pw) {
		boolean encontrado = false;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 011 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
			try {
				
				Producto producto = (Producto)ios.readObject();
				for(int i=0; i<listaProductos.size(); i++) {
					if(listaProductos.get(i).getRef().equals(dni)) {
						listaProductos.remove(i);
						listaProductos.add(i, producto);
						encontrado = true;
					}
				}
				
				System.out.println(listaProductos.toString());
				
						
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(encontrado == true) {
			return("OK "+id+" 011 Producto_modificado_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 411 Producto_no_encontrado.");
			
		}	
		
		
	}
	
	public synchronized String getProducto(String id, String dni, PrintWriter pw) {
		boolean encontrado = false;
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 012 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			
			for(int i=0; i<listaProductos.size(); i++) {
				if(listaProductos.get(i).getRef().equals(dni)) {
					oos.writeObject(listaProductos.get(i));
					oos.flush();
					encontrado = true;
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(encontrado == true) {
			return("OK "+id+" 012 Producto_entregado_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 412 Producto_no_encontrado.");
			
		}
		
	}
	
	public synchronized String removeProducto(String id, String dni) {
		
		boolean encontrado = false;
		
		for(int i=0; i<listaProductos.size(); i++) {
			if(listaProductos.get(i).getRef().equals(dni)) {
				listaProductos.remove(i);
				encontrado = true;
			}
		}
		
		System.out.println(listaProductos.toString());
		
		if(encontrado == true) {
			return("OK "+id+" 014 Producto_eliminado_satisfactoriamente");
			
		}else {
			return("FAILED "+id+" 414 Producto_no_encontrado.");
			
		}	
	}
	
	public synchronized String listProductos(String id, PrintWriter pw) {
		
		try {
			serverDatos = new ServerSocket(port);
			pw.println("PREOK "+ id+" 013 localhost "+port);
			pw.flush();
			Socket socketDatos = serverDatos.accept();
			port++;
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			
			oos.writeObject(listaProductos);
			oos.flush();					
							
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("OK "+id+" 013 Lista_productos_entregada_satisfactoriamente");
		
	}
	
	public synchronized String countProductos(String id) {
		
		return("OK "+id+" 015 N�mero_productos:"+listaProductos.size());
		
	}
	
	public synchronized String addCliente2Fact(String id, String dni, String idFact) {
		
		int flag = 0; 
		for(int i=0; i<listaClientes.size(); i++) {
			if(listaClientes.get(i).getDNI().equals(dni)) {
				flag = 1;
				for(int j=0; j<listaFacturas.size(); j++) {
					if(listaFacturas.get(j).getCodigo().equals(idFact)){
						listaFacturas.get(j).setClientes(listaClientes.get(i));
						flag = 2;
					}
				}
			}
		}
		
		System.out.println(listaFacturas.toString());
		
		if(flag == 0) {
			return("FAILED "+id+" 416 Cliente_no_encontrado.");
			
		}else if(flag == 1) {
			return("FAILED "+id+" 416 Factura_no_encontrada.");
			
		}else {
			return("OK "+id+" 016 Cliente_a�adido_a_factura_correctamente.");
			
		}
	}
	
	public synchronized String removeCliFromFact(String id, String dni, String idFact) {

		int borrado = 0;
		for(int i=0; i<listaFacturas.size(); i++) {
			if(listaFacturas.get(i).getCodigo().equals(idFact)) {
				borrado = listaFacturas.get(i).removeCliente(dni);						
			}
		}
		
		System.out.println(listaFacturas.toString());
		
		if(borrado == 0) {
			return("FAILED "+id+" 417 Factura_no_encontrada.");
			
		}else if(borrado == 1) {
			return("FAILED "+id+" 417 Cliente_no_encontrado en la factura con ID: "+ idFact);
			
		}else {
			return("OK "+id+" 017 Cliente_borrado_de_factura_correctamente.");
			
		}
	}
	
	public synchronized String addProd2Fact(String id, String ref, String idFact) {
		
		int flag = 0; 
		for(int i=0; i<listaProductos.size(); i++) {
			if(listaProductos.get(i).getRef().equals(ref)) {
				flag = 1;
				for(int j=0; j<listaFacturas.size(); j++) {
					if(listaFacturas.get(j).getCodigo().equals(idFact)){
						listaFacturas.get(j).setProductos(listaProductos.get(i));
						flag = 2;
					}
				}
			}
		}
		
		System.out.println(listaFacturas.toString());
		
		if(flag == 0) {
			return("FAILED "+id+" 418 Producto_no_encontrado.");
			
		}else if(flag == 1) {
			return("FAILED "+id+" 418 Factura_no_encontrada.");
		
		}else {
			return("OK "+id+" 018 Producto_a�adido_a_factura_correctamente.");
			
		}
	}
	
	public synchronized String removeProdFromFact(String id, String idProd, String idFact) {
		
		int borrado = 0;
		for(int i=0; i<listaFacturas.size(); i++) {
			if(listaFacturas.get(i).getCodigo().equals(idFact)) {
				borrado = listaFacturas.get(i).removeProducto(idProd);						
			}
		}
		
		System.out.println(listaFacturas.toString());
		
		if(borrado == 0) {
			return("FAILED "+id+" 417 Factura_no_encontrada.");
			
		}else if(borrado == 1) {
			return("FAILED "+id+" 417 Producto_no_encontrado en la factura con ID: "+ idFact);
			
		}else {
			return("OK "+id+" 017 Producto_borrado_de_factura_correctamente.");
			
		}
	}
	
	
	
	public static void main(String[] args) {
		Servidor nuevoServidor = new Servidor();
	}
}
