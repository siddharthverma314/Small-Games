package pianotiles;
import java.util.Random;

public class MakeTiles {

	//the constants
	int TILES_TO_WIN = 100;
	int TILES_PER_ROW = 6;
	
	//the variables
	int[] tiles;
	
	void makeTiles(int t_to_win){
		
		TILES_TO_WIN = t_to_win;
		makeTiles();
		
	}
	
	void makeTiles(int t_to_win, int t_per_row){
		
		TILES_TO_WIN = t_to_win;
		TILES_PER_ROW = t_per_row;
		makeTiles();
		
	}
	
	void makeTiles(){
		
		//initialize the tiles
		tiles = new int[TILES_TO_WIN * TILES_PER_ROW];
		
		//randomly generate the tiles
		Random r = new Random();
		int rand;
		int set;
		for(int i = 0; i < TILES_TO_WIN ; i++){
			rand = r.nextInt(TILES_PER_ROW);
			for (int j = 0; j < TILES_PER_ROW; j++){
				
				if (j == rand)
					set = 1;
				else
					set = 0;
				tiles[i * TILES_PER_ROW + j] = set;
				
			}
		}
	}
	
	int[] getTiles(){
		return tiles;
	}
	
	void printTiles(){
		
		for (int i = 0; i < tiles.length; i++){
			
			System.out.print(tiles[i] + " ");
			if( (i + 1) % TILES_PER_ROW == 0)
				System.out.println();
			
		}
		
	}
	
	public static void main(String[] args){
		
		MakeTiles m = new MakeTiles();
		m.makeTiles();
		m.printTiles();
		
	}
	
}
