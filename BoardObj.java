package ai.KendallBenjiBot;


/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project Part B
 * Tutor: Matt De Bono 
 */	

 //This class sets up a board of a certain dimension and keeps track of all pieces in a GeneralPiece array. 
public class BoardObj {
	private int dimension;
	private GeneralPiece[][] myWorld; 

	
	public BoardObj(int dimension, GeneralPiece[][] myWorld)
	{
		this.dimension = dimension; 
		this.myWorld = myWorld; 
	}

	public void setBoard(GeneralPiece[][] board)
	{
		if(board.length != this.dimension || board[0].length!= this.dimension)
		{
			System.out.println("incorrect board dimensions"); 
		}
		else
		{
			this.myWorld = board; 
		}
	}

	public void pretty_print()
	{
		for(int j=dimension-1; j>=0; j--)
		{
			for(int i=0; i<dimension; i++)
			{
				System.out.print(myWorld[i][j].getI()); 
			}
			System.out.println("\n"); 
		}
	}

	
	public void setPiece(int i, int j, GeneralPiece g)
	{
		myWorld[i][j] = g; 
	}

	public GeneralPiece getPiece(int i, int j)
	{
		return myWorld[i][j]; 
	}

	public void setDimension(int vl)
	{
		this.dimension = vl;
	}
	
	public int getDimension()
	{
		return dimension; 
	}
	
	//This method returns a deep clone of our GeneralPiece[][]
	public GeneralPiece[][] getWorld()
	{
		//we clone this for well-formed code 
		GeneralPiece[][] clonie = new GeneralPiece[dimension][dimension]; 
		for(int i=0; i<dimension; i++)
		{
			for(int j=0; j<dimension; j++)
			{
				clonie[i][j] = this.getPiece(i,j).getClone();  
			}
		}

		return clonie; 
	}

	/*
	this method checks all potential horizontal spots generated by the piece class 
	to make sure that they are valid moves (ie still on the board and not blocked)
	returns true if spot at a specific position is both in the board and empty
	*/
	public boolean checkHorizontalSpot(Position ps)
	{
		int tempX = ps.getX();
		int tempY = ps.getY();
		
		//the following if statements check that the spot to move is a valid board spot 
		if(tempY >= dimension){ 
			return false; 
		}else if(tempX==dimension){ //if the spot is 1 spot off the board, still valid 
			return true; 
		}
		else if(tempX < 0 || tempY < 0){
			return false;
		}else if(myWorld[tempX][tempY] instanceof BlankSpace){//if the spot is a blank space character
			return true;
		}else{
			return false; 
		}
	}

	//this is the same as checkHorizontal, except for pieces of type Vertical 
	public boolean checkVerticalSpot(Position ps)
	{		
		int tempX = ps.getX();
		int tempY = ps.getY();

		if(tempX >= dimension){
			return false;
		}else if(tempY == dimension){
			return true; 
		}else if(tempX < 0 || tempY < 0){
			return false; 
		}else if(myWorld[tempX][tempY] instanceof BlankSpace){
			return true;
		}else{
			return false; 
		}
	}
	
}
