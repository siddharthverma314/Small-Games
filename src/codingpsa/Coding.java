package codingpsa;

import java.util.Scanner;

public class Coding {
	
	public static void main(String args[]){
		
		Scanner s = new Scanner(System.in);
		
		while (true) {
			String word = s.next();
			
			//processing
			for (int i = 0; i < word.length(); i++) {

				System.out.print(word.charAt(i) - 'a');
				System.out.print(" ");

			}
			
			System.out.println();
			
		}
		
		//s.close();
		
	}

}
