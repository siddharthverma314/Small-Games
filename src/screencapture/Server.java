package screencapture;
import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.DeflaterOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Server implements Runnable{
	
	//variables
	JFrame display;
	ServerSocket server;
	Socket client;
	JButton selectScreenSize;
	Robot robot;
	Rectangle screenToShare;
	JWindow select;
	DeflaterOutputStream sendpic;
	final int buffertime = 10;
	Thread clientsendpic;
	
	void initDisplay(){
		
		display = new JFrame("ScreenShare");
		selectScreenSize = new JButton("Select Screen");
		
		//make display
		display.setVisible(true);
		display.setLayout(new FlowLayout());
		display.add(selectScreenSize);
		display.pack();
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set listeners
		selectScreenSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectScreen();
			}
		});
		display.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				stopServer();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	void selectScreen(){
		
		display.setVisible(false);
		select = new JWindow();
		select.setContentPane(new TranslucentPane());
		select.setBackground(new Color(0, 0, 0, 0));
		select.setVisible(true);
		select.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		select.setAlwaysOnTop(false);
		final int pixels[] = new int[4];
		select.addMouseListener(new MouseListener() {
			
			boolean first = true;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				System.out.println("Mouse Released");
				
				if(first){
					
					pixels[0] = e.getXOnScreen();
					pixels[1] = e.getXOnScreen();
					first = false;
					
				}else{
					
					pixels[2] = e.getXOnScreen();
					pixels[3] = e.getXOnScreen();
					
					System.out.println("Done");
					
					screenToShare = new Rectangle(pixels[0], pixels[1], pixels[2] - pixels[0], pixels[3] - pixels[1]);
					startServer();
					
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	void startServer(){
		
		select.dispose();
		display.setVisible(true);
		
		try {
			int port = Integer.parseInt(JOptionPane.showInputDialog("Enter a port for the Server to run on. "));
			server = new ServerSocket(port);
			JOptionPane.showMessageDialog(display, "Server now running. Please connect client at " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
			client = server.accept();
			sendpic = new DeflaterOutputStream(client.getOutputStream());
			clientsendpic = new Thread(this);
			System.out.println("Starting...");
			clientsendpic.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void stopServer(){
		
		try {
			server.close();
			clientsendpic.interrupt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		
		Server server = new Server();
		server.initDisplay();
		
	}
	
	public class TranslucentPane extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 4016025928924099093L;

		public TranslucentPane() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.85f));
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());

        }

    }

	
	public void run() {
		
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true){
			
			BufferedImage img = robot.createScreenCapture(screenToShare);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(img, "jpg", baos);
				byte[] bytes = baos.toByteArray();
				sendpic.write(bytes);
				System.out.println("sent");
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
		
}