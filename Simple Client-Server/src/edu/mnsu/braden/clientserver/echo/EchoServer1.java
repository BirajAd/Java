/**
 * A simple Echo Server. It can only accept one client connection and it will terminate when that
 * connection is complete. It will simply echo what it receives from the client until it receives
 * the string "/quit" from the client or when the client otherwise severs the connection.
 */
package edu.mnsu.braden.clientserver.echo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.io.DataInputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



/**
 * @author bradenf
 *
 */
public class EchoServer1 {

	public static final int SERVERPORT = 5644;
	/**
	 * Driver for the EchoServer Engine
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		BufferedReader read = null;
		DataOutputStream write = null;
		
		// Attempt to open a server socket for inbound connections
		try {
			serverSocket = new ServerSocket(SERVERPORT);
		} catch (IOException e) {
			System.err.println("Failed to open server socket");
			e.printStackTrace();
			System.exit(1);
		}
		
		// Wait for a client to connect 
		System.out.println("Waiting ...");
		try {
			// This will block until someone trys to connect
			clientSocket = serverSocket.accept();
			
			// Get our input and output classes
			read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			write = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Client connection from " + clientSocket.getInetAddress());
			
			// This will continue until the clients types "/quit" or otherwise drops the connection

            Scanner writer = new Scanner(System.in);

			while (true) {
//				String line = read.readLine();
//				System.out.println(line);
//				if ("/quit".equalsIgnoreCase(line)) {
//					System.out.println("Closing connection with " + clientSocket.getInetAddress());
//					break;
//				}
				Thread t = new ClientTaker(clientSocket, read, write);
//				System.out.printf("-> ");
//				line = writer.nextLine();
//				line += "\r\n";
//				write.writeBytes(line);
				t.start();
			}
		} catch (SocketException e) {
			System.out.println("Client connection was disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			read.close();
			write.close();
			serverSocket.close();
		}
		
	}

}

//ClientTaker
class ClientTaker extends Thread{
	BufferedReader in = null;
	DataOutputStream out = null;
	Socket socket;

	public ClientTaker(Socket socket, BufferedReader in,DataOutputStream out){
		this.socket = socket;
		this.in = in;
		this.out = out;
//		System.out.println("ClientTaker is working.");
	}

	@Override
	public void run(){
		String toSend;
		String toReceive;
//		System.out.println("run() is running too");

		while (true){
			try{
				Scanner writer = new Scanner(System.in);
				toSend = writer.nextLine();
				toSend += "\r\n";
				out.writeBytes(toSend);
				toReceive = this.in.readLine();

				Date date = new Date();

				DateFormat _date = new SimpleDateFormat("yy/mm/dd");
				DateFormat _time = new SimpleDateFormat("hh:mm:ss");

				if (toReceive.equalsIgnoreCase("exit")){
					socket.close();
					System.out.println("Chat Ended.");
					break;
				}else if(toReceive.equalsIgnoreCase("date")){
					out.writeBytes("Date: " + _date.format(date));
					System.out.println("From Client: "+ toReceive);
					break;
				}else if(toReceive.equalsIgnoreCase("time")){
					out.writeBytes("Time: "+ _time.format(date));
					System.out.println("From Client: "+ toReceive);
					break;
				}else{
					out.writeBytes(toSend);
				}
			}catch(IOException e){
				System.out.println("Somethin' went wrong");
			}
		}
	}

}
