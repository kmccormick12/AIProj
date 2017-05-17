package ai.KendallBenjiBot;

/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project Part B
 * Tutor: Matt De Bono 
 */	

public class Block extends GeneralPiece{

 	// This class inherits from General Piece and is only for type checking. It is a block space. 
	public Block(Position pos, char i){
		super(pos,i);
	}
	public Block getClone(){
		return(new Block(this.pos, this.i));
	}	
	
}

