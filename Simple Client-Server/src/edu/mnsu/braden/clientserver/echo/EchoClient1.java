/**
 * A simple Echo Client that will open a Socket to the Echo Server. Text typed by the user will be
 * echoed back from the server until the user types "/quit"
 */
package edu.mnsu.braden.clientserver.echo;

import edu.mnsu.braden.clientserver.echo.EchoServer1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author bradenf
 *
 */
public class EchoClient1 {

	/**
	 * Main driver for the Echo Client
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws IOException {
		
		Socket clientSocket = null;
		BufferedReader read = null;
		DataOutputStream write = null;
		
		// Attempt to open a socket to the server and get the server's input and output objects
		try {
			clientSocket = new Socket("localhost", EchoServer1.SERVERPORT);
			System.out.println("Connected to echo server at " + clientSocket.getInetAddress());
			read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			write = new DataOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("Could not open host.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Couldn't establish I/O with host.");
			System.exit(2);
		}
		
		System.out.println("Enter text. Use /quit to exit.");
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		try {
			while (true) {
				// Prompt the user for the next line to send
				System.out.print("> ");
				String line = console.nextLine();
				// Send the data to the server
				line += "\r\n";
				write.writeBytes(line);
				if ("/quit\r\n".equalsIgnoreCase(line)) {
					System.out.println("BYE");
					break;
				}
				// Wait for the server's response
				line = read.readLine();
				System.out.println("Server: " + line);
			}
		} catch (SocketException e) {
			System.out.println("Connection lost.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			read.close();
			write.close();
			clientSocket.close();
		}
	}

}
