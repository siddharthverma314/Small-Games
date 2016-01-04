package hacking;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class ParentalControl {
	
	//The sockets
	ServerSocket hack;
	Socket comp;
	Socket router;
	DataInputStream from_comp, from_router;
	DataOutputStream to_comp, to_router;
	
	//constants
	final String MAC_ID = "0C-84-DC-27-41-61";
	final int PORT = 80;
	final String ROUTER = "192.168.1.1";
	
	int startServer(){
		
		try {
			hack = new ServerSocket(PORT);
			comp = hack.accept();
			router = new Socket(ROUTER, PORT);
			return 0;
		} catch (IOException e) {
			return -1;
		}
		
	}
	
	public static void main(String args[]){
		
		new ParentalControl().startServer();
		
	}
	
}