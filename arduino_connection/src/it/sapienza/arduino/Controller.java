package it.sapienza.arduino;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;

public class Controller {

	private SerialPort sp;
	private PrintWriter output;
	private InputStream input;
	
	private Lock lock = new ReentrantLock();


	private float outputValue;

	String line;

	public Controller(String port) {
		sp = SerialPort.getCommPort(port);
		sp.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		sp.openPort();
		output = new PrintWriter(sp.getOutputStream());

		input = sp.getInputStream();
	}

	public void changeState(String state) {
		System.out.println(state);

		output.write(state);
		output.flush();

	}

	public String returnAttendences() throws IOException {
		
		/*Thread t = new Thread() {
			public void run() {
				lock.lock();
				Scanner scanner = new Scanner(input);
				while (scanner.hasNextLine()) {
										
					line = scanner.nextLine();
					System.out.println(line);
					//outputValue = Float.parseFloat(line);
					//System.out.println(line);
				}
				scanner.close();
				lock.unlock();
			};
		};
		t.start();*/
		
		/*Thread t = new Thread() {
			public void run() {
				lock.lock();
				Scanner scanner = new Scanner(input);
				while (scanner.hasNextLine()) {
										
					line = scanner.nextLine();
					
					//outputValue = Float.parseFloat(line);
					//System.out.println(line);
				}
				scanner.close();
				lock.unlock();
			};
		};
		t.start();*/

		/*
		 * try { System.out.println( ( (char) input.read() ) ); } catch (IOException e)
		 * { e.printStackTrace(); }
		 */
		/*
		 * int ch; StringBuilder sb = new StringBuilder(); while((ch = input.read()) !=
		 * -1) { System.out.print("ch"+(char)ch); sb.append((char)ch); } //reset();
		 * return sb.toString();
		 */

		/*
		 * ByteArrayOutputStream result = new ByteArrayOutputStream(); byte[] buffer =
		 * new byte[1024]; int length; while ((length = input.read(buffer)) != -1) {
		 * result.write(buffer, 0, length); } // StandardCharsets.UTF_8.name() > JDK 7
		 * return result.toString("UTF-8");
		 */

		/*
		 * for (int j=0; ;j++) { try { System.out.println("ciao"); System.out.print( (
		 * (char) input.read() ) ); System.out.println("fine"); } catch (IOException e1)
		 * { e1.printStackTrace(); } return 'a'; }
		 */

		/*
		 * for (int j=0; ;j++) { try { System.out.println( ( (char) input.read() ) );
		 * /*System.out.println("CIAOOO");
		 * System.out.print("StringAttendences "+string_attendences); return (Character)
		 * null; } catch (IOException e1) { e1.printStackTrace(); } }
		 */

		/*ArrayList<String> test = new ArrayList<>();
		String final_string = "";
		int result = input.read();
		System.out.println(result);
		
		long millis = System.currentTimeMillis();
		
		boolean trovato = false;
		
		while (result != -1) {
			
			try {

				System.out.println("SONO NEL try");
				// System.out.println((char) input.read());
				char c = (char) result;
				// System.out.print(c);
				test.add(String.valueOf(c));
				
				result = input.read();
	
				
			} catch (IOException e1) {
				System.out.println("Eccezione");
				e1.printStackTrace();
			}
			
		}
		
		System.out.println("USCITO DAL TRY");
		
		System.out.println(test.size());
		
		for (String s : test) {
			System.out.print(s+ ' ');
			final_string += s;
			
		}
		System.out.println(final_string);*/
		
		/*long millis = System.currentTimeMillis();
		long j = 0,actual;
		 for (j=0; j<2000; j++) {
				try {
					char code = ( (char) input.read() );
					System.out.print(code);
					actual = System.currentTimeMillis();
					j = actual-millis;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		 }
		 System.out.println("ciao");*/
		String final_string = "";
		
		char test = (char) input.read();
		while( test != '*') {
			final_string += String.valueOf(test);
			test = (char) input.read();
		}
		return final_string;
	}

	public float getOutputValue() {
		return outputValue;
	}

	public String getLine() {
		return line;
	}

}
