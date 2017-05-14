package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
 * Tutor: Matt De Bono 
 */

 //contains an x and y coordinate 
public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y; 
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
