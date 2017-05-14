package ai.partB;

public class BlankSpace extends GeneralPiece {
	
/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */	
	public BlankSpace(Position pos, char i){
		super(pos,i);
	}

	public BlankSpace getClone(){
		return(new BlankSpace(this.pos, this.i));
	}
	
}