package it.sapienza.arduino;

import java.io.*;
import java.net.*;

public class Server extends Thread {

	private ServerSocket Server;
	private Controller sensor;

	public static void main(String argv[]) throws Exception {
		Server s = new Server();
		s.start();
	}

	public Server() throws Exception {
		Server = new ServerSocket(4000);
		System.out.println("Il Server è in attesa sulla porta 4000.");

		sensor = new Controller("COM8"); // arduino port
	}

	public void run() {
		while (true) {
			try {
				System.out.println("In attesa di Connessione.");
				Socket client = Server.accept();
				System.out.println("Connessione accettata da: " + client.getInetAddress());
				Connect c = new Connect(client, sensor);
				c.start();
			} catch (Exception e) {
			}
		}
	}
}

class Connect extends Thread {
	
	private Socket client = null;
	BufferedReader in = null;
	PrintStream out = null;
	private Controller sensor; // Controller arduino.

	public Connect() {
	}

	public Connect(Socket clientSocket, Controller sensor) {
		client = clientSocket;
		this.sensor = sensor;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream(), true);
		} catch (Exception e1) {
			try {
				client.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return;
		}
	}

	// Thread sempre in esecuzione. Dipende dai parametri che mando.
	public void run() {
		try {

			String input = in.readLine();
			System.out.println(input);
			
			if (input.equalsIgnoreCase("a")) {
				//input = in.readLine();
				sensor.changeState(input);
				//out.println(sensor.getOutputValue());
				// out.println("led settato correttamente");
				//out.flush();
			} else if (input.equals("Presenze")) {
				//input = in.readLine();
				String attendences = sensor.returnAttendences();
				System.out.println(attendences);
				out.println(attendences);
				out.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}