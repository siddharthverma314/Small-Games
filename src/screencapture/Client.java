package screencapture;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.Socket;
import java.util.zip.InflaterInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Client implements Runnable{
	
	BufferedImage screen;
	JWindow display;
	Socket server;
	InflaterInputStream picin;
	BufferedImage image;
	
	void initDisplay(){
		
		display = new JWindow();
		display.setVisible(true);
		display.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			System.out.println("Enter port number: ");
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			int port = Integer.parseInt(read.readLine());
			System.out.println("Enter ip address of server: ");
			String ip = read.readLine();
			server = new Socket(InetAddress.getByName(ip), port);
			picin = new InflaterInputStream(server.getInputStream());
			(new Thread(this)).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		
		DataInputStream buffer; 
		
		while (true) {
			
			buffer = new DataInputStream(picin);
			try {
				image = ImageIO.read(buffer);
				display.getGraphics().drawImage(image, 0, 0, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
		
	}
	
	public static void main(String args[]){
		
		new Client().initDisplay();
		
	}
	
}
