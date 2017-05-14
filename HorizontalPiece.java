package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */

import aiproj.slider.Move;

//subclass of piece that accounts for horizontal players 
//the benefit to subclassing is we can use instanceOf to verify the type before we apply move 

public class HorizontalPiece extends GeneralPiece{
	
	public HorizontalPiece(Position pos, char i){
		super(pos,i);		 
	}
	
	public HorizontalPiece getClone(){
		return(new HorizontalPiece(this.pos, this.i));
	}	
	//returns an array of the 3 hypothetically possible moves for any horizontal piece
	//these potential moves are later validated by the Board class 
	public Move[] hypotheticalMovesH(){

        Move mUp = new Move(super.pos.getX(), super.pos.getY(), Move.Direction.UP);
        Move mDown = new Move(super.pos.getX(), super.pos.getY(), Move.Direction.DOWN);
        Move mRight = new Move(super.pos.getX(), super.pos.getY(), Move.Direction.RIGHT);
		
        Move[] mArray = {mUp, mDown, mRight}; 
		return mArray; 
	}
}
