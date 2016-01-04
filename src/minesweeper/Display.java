package minesweeper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class Display extends JFrame{
	
	/**Made by Siddharth Verma...
	 * Minesweeper!!!!!!
	 */
	private static final long serialVersionUID = 4177340538459330853L;
	
	int[] bombs;
	int noofbombs;
	JButton[] buttons;
	int MAX_X;
	final int BUTTON_SIZE = 20;
	public boolean flag_state = false;
	JPanel panel;
	JPanel infobar;
	JLabel status;
	boolean[] bs, flags;
	boolean done = false;
	
	boolean run(int width, int height, int no_of_bombs){
		
		buttons = new JButton[width * height];
		bombs = new int[width * height];
		bs = new boolean[width * height];
		flags = new boolean[width * height];
		MAX_X = height;
		noofbombs = no_of_bombs;
		
		setValues(width, height);
		initScreen(width, height);
		printArray(width);
		
		//refresh window
		SwingUtilities.updateComponentTreeUI(this);
		
		return true;
		
	}
	
	int pos(int x, int y, int max_x){
		
		return y * max_x + x;
		
	}
	
	int pos(int x, int y){
		
		return y * MAX_X + x;
		
	}
	
	int[] get_pos(int pos){
		
		int ret[] = new int[2];
		ret[0] = pos % MAX_X;
		ret[1] = (pos - ret[0]) / MAX_X;
		return ret;
		
	}
	
	int[] get_pos(int pos, int width){
		
		int ret[] = new int[2];
		ret[0] = pos % width;
		ret[1] = (pos - ret[0]) / width;
		return ret;
		
	}
	
	private void setValues(int w, int h){
		
		//set bombs
		Random r = new Random();
		for(int i = 0; i < bombs.length; i++) bombs[i] = 0;
		for(int i = 0; i < noofbombs; i++){
			
			int pos = r.nextInt(bombs.length);
			if(bombs[pos] != 0) continue;
			bombs[pos] = -1;
			bs[pos] = true;
			
		}
		
		//check each square. if it is a bomb, add 1 to all squares around, except -1(bomb)
		for(int i = 0; i < bombs.length; i++){
			
			//if it is a bomb
			if (bombs[i] == -1) {
				
				//check and add 1 to all squares around it except center
				int bomb[] = get_pos(i);
				System.out.println(bomb[0] + " " + bomb[1]);
				for(int x = -1; x <= 1; x++){
					for(int y = -1; y <= 1; y++){
						if(bomb[0] + x < 0 || bomb[0] + x > w - 1);
						else{
							
							if(!(x == 0 & y == 0)){
								
								//add 1 to the square, if it isn't bomb
								try {
									if(bombs[pos(bomb[0] + x, bomb[1] + y)] != -1)
										bombs[pos(bomb[0] + x, bomb[1] + y)]++;
								} catch (Exception e) {}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private void printArray(int width){
		
		for(int i = 0; i < bombs.length; i++){

			if (bombs[i] == -1) System.out.print("-1");
			else System.out.print(" " + bombs[i]);
			if((i + 1) % width == 0) System.out.println();
			
		}
		
	}
	
	private void initScreen(int width, int height) {

		// init window
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Minesweeper");
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		// draw buttons
		panel = new JPanel();
		panel.setSize(width * BUTTON_SIZE, height * BUTTON_SIZE);
		panel.setLayout(new GridLayout(width, height));
		for (int i = 0; i < buttons.length; i++) {

			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
			buttons[i].addActionListener(new Handler());
			panel.add(buttons[i]);

		}
		
		this.add(panel, BorderLayout.CENTER);
		
		//infobar
		infobar = new JPanel();
		infobar.setSize(panel.getWidth(), 30);
		JButton flag = new JButton("flag");
		flag.addActionListener(new Info());
		infobar.add(flag);
		status = new JLabel("flag = " + flag_state);
		infobar.add(status);
		this.add(infobar, BorderLayout.SOUTH);
		
		this.pack();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}

	}
	
	class Handler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			for(int i = 0; i < buttons.length; i++){
				
				if (e.getSource() == buttons[i]) {
					
					if (flag_state) {
						if(flags[i] == true){
							buttons[i].setIcon(null);
							flags[i] = false;
						}
						else {
							ImageIcon flag = new ImageIcon("src/minesweeper/flag.jpg");
							buttons[i].setIcon(flag);
							flags[i] = true;
						}
						
					}else{
						
						buttons[i].setIcon(new ImageIcon("src/minesweeper/"+ bombs[i] + ".jpg"));
						if (bombs[i] == -1) {
							JOptionPane.showMessageDialog(Display.this, "You Lost...", "aww....", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
						
					}
					
				}
				
			}
			
			//check if game is over
			System.out.println("checking...");
			boolean won = true;
			for(int i = 0; i < flags.length; i++){
				if(flags[i] != bs[i]) {
					won = false;
					break;
				}
			}
			if(won == true){
				JOptionPane.showMessageDialog(Display.this, "You Have Won!!!", "Yay!", JOptionPane.INFORMATION_MESSAGE);
				done = true;
			}
			System.out.println("checked");
			
		}
		
	}
	
	class Info implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			flag_state = !flag_state;
			status.setText("flag = " + flag_state);
			
		}
		
	}
	
	public static void main(String args[]){
		
		new Display().run(10, 10, 10);
		//int[] temp = new Display().get_pos(new Display().pos(10, 15, 13), 13);
		//System.out.println(temp[0] + " " + temp[1]);
	}

	
}
