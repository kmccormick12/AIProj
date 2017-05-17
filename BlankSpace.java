package ai.KendallBenjiBot;
/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project Part B
 * Tutor: Matt De Bono 
 */	

public class BlankSpace extends GeneralPiece {
	
	public BlankSpace(Position pos, char i){
		super(pos,i);
	}

	public BlankSpace getClone(){
		return(new BlankSpace(this.pos, this.i));
	}
	
}