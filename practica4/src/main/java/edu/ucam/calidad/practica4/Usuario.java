package edu.ucam.calidad.practica4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {
	
	
	public Usuario() {
		try {			
			
			Socket socket = new Socket("localhost",2022);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			Scanner teclado = new Scanner(System.in);
			
			String instruccion= "";
			
			boolean flag=false;
			String conf;
			
			while(flag==false) {
				System.out.println("Escriba su nombre de usuario:");
				instruccion = teclado.nextLine();
								
				pw.println(instruccion);
				pw.flush();
				conf= br.readLine();
				if(conf.equals("OK")) {
					System.out.println("Escriba la contrase�a:");
					instruccion = teclado.nextLine();
					pw.println(instruccion);
					pw.flush();
					
					conf=br.readLine();
					if(conf.equals("OK")) {
						flag = true;
					}else {
						System.out.println("Contrase�a incorrecta");
					}
					
				}else {
					System.out.println("Usuario incorrecto");
				}
			}
			
			while(!"exit".equalsIgnoreCase(instruccion)) {	
				System.out.println("Introduzca la instruccion:");
				instruccion = teclado.nextLine();
				pw.println(instruccion);
				pw.flush();
				
				String [] instruccionPart = instruccion.split(" ");
				String respuesta = br.readLine();
				String[] respuestaPart = respuesta.split(" ");
				
				if(respuestaPart[0].equalsIgnoreCase("PREOK")) {
					Socket socketDatos = new Socket(respuestaPart[3], Integer.parseInt(respuestaPart[4]));
					switch(respuestaPart[2]) {
						case "001": {
							System.out.println("Indique el nombre:");
							String nombreCliente = teclado.nextLine();
							System.out.println("Indique el DNI:");
							String DNICliente = teclado.nextLine();
							Cliente cliente = new Cliente(nombreCliente, DNICliente);
							ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
							oos.writeObject(cliente);
							oos.flush();							
							
							System.out.println(respuesta = br.readLine());
							break;
						}
						case "002":{
							System.out.println("Indique el nuevo nombre:");
							String nombreCliente = teclado.nextLine();
							System.out.println("Indique el DNI:");
							String DNICliente = teclado.nextLine();
							Cliente cliente = new Cliente(nombreCliente, DNICliente);
							ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
							oos.writeObject(cliente);
							oos.flush();
							
							System.out.println(respuesta = br.readLine());
							break;
						}
						case "003":{
							ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
							try {
								Cliente cliente = (Cliente)ios.readObject();
								
								System.out.println(cliente.toString());
								System.out.println(respuesta = br.readLine());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;

						}
						case "004":{
							ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
							try {
								ArrayList<Cliente> clientes = new ArrayList<Cliente>();
								clientes = (ArrayList<Cliente>) ios.readObject();
								
									System.out.println(clientes.toString());
																
								System.out.println(respuesta = br.readLine());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						case "007":{
							System.out.println("Indique el codigo:");
							String codigo = teclado.nextLine();
							System.out.println("Indique el precio:");
							String precio = teclado.nextLine();
							Factura factura = new Factura(codigo, precio);
							ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
							oos.writeObject(factura);
							oos.flush();							
							
							System.out.println(respuesta = br.readLine());
							break;
						}
						case "008":{
							ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
							try {
								Factura factura = (Factura)ios.readObject();
								
								System.out.println(factura.toString());
								System.out.println(respuesta = br.readLine());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						case "009":{
							ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
							try {
								ArrayList<Factura> facturas = new ArrayList<Factura>();
								facturas = (ArrayList<Factura>) ios.readObject();
								
									System.out.println(facturas.toString());																
									System.out.println(respuesta = br.readLine());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						case "010": {
							System.out.println("Indique el nombre:");
							String nombre = teclado.nextLine();
							System.out.println("Indique la referencia:");
							String ref = teclado.nextLine();
							Producto producto = new Producto(nombre, ref);
							ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
							oos.writeObject(producto);
							oos.flush();							
							
							System.out.println(respuesta = br.readLine());
							break;
						}
						case "011":{
							System.out.println("Indique el nuevo nombre:");
							String nombre = teclado.nextLine();
							System.out.println("Indique la nueva referencia:");
							String ref = teclado.nextLine();
							Producto producto = new Producto(nombre, ref);
							ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
							oos.writeObject(producto);
							oos.flush();
							
							System.out.println(respuesta = br.readLine());
							break;
						}
						case "012":{
							ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
							try {
								Producto producto = (Producto)ios.readObject();
								
								System.out.println(producto.toString());
								System.out.println(respuesta = br.readLine());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;

						}
						case "013":{
							ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
							try {
								ArrayList<Producto> productos = new ArrayList<Producto>();
								productos = (ArrayList<Producto>) ios.readObject();
								
									System.out.println(productos.toString());																
								System.out.println(respuesta = br.readLine());
								
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					
						default:{
							System.out.println("La respuesta no est� identificada.");
						}
					}					
					
				}else if(respuestaPart[0].equalsIgnoreCase("OK")) {
					System.out.println(respuesta);
					
				}else if(respuestaPart[0].equalsIgnoreCase("FAILED")) {
					System.out.println(respuesta);
				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cliente finalizado");
		}
	}
	
	public static void main(String[] args) {
		Usuario nuevoUsuario = new Usuario();
	}

}
