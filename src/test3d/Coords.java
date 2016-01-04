package test3d;

public class Coords {
	
	/* The coordinates
	 * int[x][y][z][r/g/b/a] -
	 * r/g/b/a is - 
	 * when [] == 0 it is r
	 * when [] == 1 it is g
	 * when [] == 2 it is b
	 * when [] == 3 it is alpha
	 */
	int [][][][] c;
	/*
	 * The origin
	 * int[] = {x, y, z}
	 */
	int origin[];
	
	Coords(){
		
		c = new int[100][100][100][4];
		origin = new int[]{50, 50, 50};
		
	}
	
	void drawPoint(int x, int y, int z, int r, int g, int b, int a){
		
		 
		
	}
	
}
