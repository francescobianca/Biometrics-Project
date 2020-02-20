package it.sapienza.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class Prova {
	
	private static PrintWriter output;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		/*SerialPort[] portNames = SerialPort.getCommPorts();
		ArrayList<String> portList = new ArrayList<>();  
		for(int i = 0; i < portNames.length; i++)
			portList.add(portNames[i].getSystemPortName());*/
		
		SerialPort sp = SerialPort.getCommPort("COM8"); // device name TODO: must be changed
		//sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
		sp.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		
		if (sp.openPort()) {
			System.out.println("Port is open :)");
		} else {
			System.out.println("Failed to open port :(");
			return;
		}		
		/*for (Integer i = 0; i < 5; ++i) {			
			sp.getOutputStream().write(i.byteValue());
			sp.getOutputStream().flush();
			System.out.println("Sent number: " + i);
			Thread.sleep(1000);
		}*/		
		
		String line = "";
		
		output = new PrintWriter(sp.getOutputStream());

	
			
		
		
		
		/*output.write("9");
		output.flush();*/
		
		InputStream in = sp.getInputStream();
		
		
		 for (int j=0; ;j++) {
				try {
					char code = ( (char) in.read() );
					System.out.println(code);
				
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
		/*Thread.sleep(2000);
		
		if (sp.closePort()) {
			System.out.println("Port is closed :)");
		} else {
			System.out.println("Failed to close port :(");
			return;
		}*/
		

	}

	
}

