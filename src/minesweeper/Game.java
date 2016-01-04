package minesweeper;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game {
	
	void make(){
		
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("MineSweeper");
		
		JPanel top = new JPanel();
		JPanel down = new JPanel();
		JButton start = new JButton("Start!!");
		final JTextField rows = new JTextField("enter the number of rows");
		final JTextField columns = new JTextField("enter the number of columns");
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
				
			}
		});
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				Display d = new Display();
				int r = Integer.parseInt(columns.getText());
				int c = Integer.parseInt(rows.getText());
				d.run(c, r, (int)(r * c / 7));
				
			}
			
		});
		top.add(rows);
		top.add(columns);
		down.add(exit);
		down.add(start);
		frame.setLayout(new BorderLayout());
		frame.add(top, BorderLayout.CENTER);
		frame.add(down, BorderLayout.SOUTH);
		frame.pack();

		
	}
	
	public static void main(String[] args) {
		
		Game g = new Game();
		g.make();
		
	}
	
}