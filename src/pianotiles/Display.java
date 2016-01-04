package pianotiles;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Display implements ActionListener{
	
	//the constants
	final int SCREEN_WIDTH = 220;
	final int SCREEN_HEIGHT = 360;
	final int BUTTON_WIDTH = 50;
	final int BUTTON_HEIGHT = 80;
	final int THREAD_DELAY = 0;
	
	JFrame frame;
	JButton[] btiles;
	JPanel panel;
	int[] tiles;
	MakeTiles mt;
	int count = -1;
	int w, h;
	
	void createDisplay(){
		
		//make tiles
		mt = new MakeTiles();
		mt.makeTiles();
		tiles = mt.getTiles();
		
		//create frame
		frame = new JFrame("Piano Tiles!");
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//create buttons
		btiles = new JButton[mt.TILES_PER_ROW * mt.TILES_TO_WIN];
		
		for(int i = 0; i < btiles.length; i++){
			btiles[i] = new JButton();
			if (tiles[i] == 1){
				btiles[i].setIcon(new ImageIcon("./src/pianotiles/Black.jpg"));
			} else{
				btiles[i].setIcon(new ImageIcon("./src/pianotiles/White.jpg"));
			}
			//add action listener
			btiles[i].addActionListener(this);
		}
		
		//create panel
		panel = new JPanel();
		w = BUTTON_WIDTH * mt.TILES_PER_ROW;
		h = BUTTON_HEIGHT * mt.TILES_TO_WIN;
		panel.setVisible(true);
		setB();
		GridLayout gl = new GridLayout(mt.TILES_TO_WIN , mt.TILES_PER_ROW);
		panel.setLayout(gl);
		
		for (int i = mt.TILES_PER_ROW * mt.TILES_TO_WIN - 1; i >= 0; i--){
			panel.add(btiles[i]);
		}
		
		frame.add(panel);
		
		//update the frame
		SwingUtilities.updateComponentTreeUI(frame);
		
	}
	
	public static void main(String args[]){
		
		Display d = new Display();
		d.createDisplay();
		
	}

	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i < btiles.length; i++){
			
			if(e.getSource() == btiles[i]){
				
				if(tiles[i] == 1 && i >= (count + 1) * mt.TILES_PER_ROW && i <= (count + 2) * mt.TILES_PER_ROW){
					
					btiles[i].setIcon(new ImageIcon("./src/pianotiles/White.jpg"));
					smoothB();
					
				}else{
					
					JOptionPane.showMessageDialog(frame, "You Lost", "Aww", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
					
				}
				
			}
			
		}
		
	}
	
	void setB(){
		
		int a, b;
		a = 0;
		b = h * -1 + BUTTON_HEIGHT * 4 + BUTTON_HEIGHT * count ;
		panel.setBounds(a, b, w, h);
		
	}
	void smoothB(){
		
		int a, b, c;
		a = 0;
		b = h * -1 + BUTTON_HEIGHT * 4 + BUTTON_HEIGHT * count ;
		count++;
		c = h * -1 + BUTTON_HEIGHT * 4 + BUTTON_HEIGHT * count ;
		
		for(int i = b; i < c; i++){
			
			panel.setBounds(a, i, w, h);
			try {
				Thread.sleep(THREAD_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
