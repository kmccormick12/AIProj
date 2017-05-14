package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */

 //piece is the parent class of all game pieces, contains a position object for its location 
 //board is made up of pieces 
public class GeneralPiece {
	protected Position pos;
	protected char i; 
	
	public GeneralPiece getClone(){
		System.out.println("wrong");
		return(new GeneralPiece(this.pos, this.i));
	}
	public GeneralPiece(Position pos, char i){
		this.pos = pos;
		this.i = i;
	}
	
	public GeneralPiece(Position pos){
		this.pos = pos;
	}
	
	
	public Position getPosition(){
		return pos; 
	}
	
	public char getI(){
		return this.i;
	}
	
	
}
