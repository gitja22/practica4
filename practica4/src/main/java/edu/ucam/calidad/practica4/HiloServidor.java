package edu.ucam.calidad.practica4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class HiloServidor extends Thread{
	
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private Servidor servidor;
	
	
	public HiloServidor(Socket socket, Servidor servidor) {
		this.socket = socket;
		this.servidor = servidor;
		
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		String ins;
		boolean usuario = false;
		
		while(usuario ==false) {
			usuario = this.servidor.reconocerUsuario(br, pw);
		}
		
		try {
			while(!"exit".equalsIgnoreCase(ins=br.readLine())) {							
				String [] partes = ins.split(" ");
				String respuesta;
				
				switch(partes[1]) {
					case "ADDCLIENTE":{
						respuesta = servidor.addCliente(partes[0], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "UPDATECLIENTE":{
						respuesta = servidor.updateCliente(partes[0], partes[2], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "GETCLIENTE":{
						respuesta = servidor.getCliente(partes[0], partes[2], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "REMOVECLIENTE":{
						respuesta = servidor.removeCliente(partes[0], partes[2]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "LISTCLIENTES":{
						respuesta = servidor.listClientes(partes[0], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "COUNTCLIENTES":{
						respuesta = servidor.countClientes(partes[0]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "ADDFACTURA":{
						respuesta = servidor.addFactura(partes[0], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "GETFACTURA":{
						respuesta = servidor.getFactura(partes[0], partes[2], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "LISTFACTURAS":{
						respuesta = servidor.listFacturas(partes[0], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "REMOVEFACTURA":{
						respuesta = servidor.removeFactura(partes[0], partes[2]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "ADDPRODUCTO":{
						respuesta = servidor.addProducto(partes[0], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "UPDATEPRODUCTO":{
						respuesta = servidor.updateProducto(partes[0], partes[2], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "GETPRODUCTO":{
						respuesta = servidor.getProducto(partes[0], partes[2], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "REMOVEPRODUCTO":{
						respuesta = servidor.removeProducto(partes[0], partes[2]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "LISTPRODUCTOS":{
						respuesta = servidor.listProductos(partes[0], pw);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "COUNTPRODUCTOS":{
						respuesta = servidor.countProductos(partes[0]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "ADDCLIENTE2FACT":{
						respuesta = servidor.addCliente2Fact(partes[0], partes[2], partes[3]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "REMOVECLIFROMFACT":{
						respuesta = servidor.removeCliFromFact(partes[0], partes[2], partes[3]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "ADDPROD2FACT":{
						respuesta = servidor.addProd2Fact(partes[0], partes[2], partes[3]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					case "REMOVEPRODFROMFACT":{
						respuesta = servidor.removeProdFromFact(partes[0], partes[2], partes[3]);
						pw.println(respuesta);
						pw.flush();
						break;
					}
					default:{
						pw.println("FAIL ");
						pw.flush();
					}
						
				}					
								
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	

}
