package ai.partB;

import aiproj.slider.Move;
import java.util.Scanner; 

public class MyPlayer implements aiproj.slider.SliderPlayer {
	private BoardObj myBoard; 
	private PlayerController p; 
	private char my_player; 
	//private char playerType;

	public MyPlayer(){
	}
	
	public static String extractBadChar(String s){
		String newS = "";
		for(int j=0; j<s.length(); j++){
			if((int)s.charAt(j) == 32){ //if the character is a white space
				continue;
			}else{
				newS = newS + s.charAt(j);
			}
		}	
		return newS;
	}

	public void init(int dimension, String board, char player){
		int counter = 0;
		GeneralPiece[][] myWorld = new GeneralPiece[dimension][dimension];
		Scanner sc = new Scanner(board);
		int y = dimension-1;
		while(counter < dimension){
			String temp = extractBadChar(sc.nextLine()); //get first line of input to make game board 
			for(int x=0; x<temp.length(); x++){
				// Horizontal Player
				if(temp.charAt(x) == 'H'){
					Position p = new Position(x,y);
					HorizontalPiece h = new HorizontalPiece(p,'H');
					System.out.println("Horizontal" + x + "," + y);
					myWorld[x][y] = h;
				// Blank Space	
				}else if(temp.charAt(x) == '+'){
					Position p = new Position(x,y);
					BlankSpace bs = new BlankSpace(p,'+');
					myWorld[x][y] = bs;
				// Block Character
				}else if(temp.charAt(x) == 'B'){
					Position p = new Position(x,y);
					Block bl = new Block(p,'B');
					myWorld[x][y] = bl;
				// Vertical Player 
				}else if(temp.charAt(x) == 'V'){
					Position p = new Position(x,y);
					System.out.println("Vertical:" + x + "," + y);
					VerticalPiece v = new VerticalPiece(p,'V');
					myWorld[x][y] = v;
				}else if(temp.charAt(y) == '\n'){
					break;
				}else if(counter > dimension){
					break;
				}
			
			}
			y--;
			counter++;
		}
		sc.close();
		/*for (int row = 0; row < dimension; row++) {
	        for (int column = 0; column < myWorld[row].length; column++) {
	            System.out.print(myWorld[row][column].getI() + " ");
	        }
	        System.out.println();
	    }
        System.out.println(); */
		my_player = player; 
        myBoard = new BoardObj(dimension, myWorld); //create our board object 
        //this.playerType = player; //initiate type  {
		this.p = new PlayerController(dimension, myBoard, my_player); //pass the horizontal player reference to the board 

	}
	
	public void update(Move move){
		if(move==null)
		{
		}
		else
		{
			System.out.println(move.toString());
			this.myBoard = this.p.update(move, myBoard, my_player);
			p.setBoard(myBoard); 
		}
	}
	
	public Move move(){
		Move m = this.p.move();
		this.myBoard = this.p.getBoard(); //update board
		return (m); 
	}
	
}

