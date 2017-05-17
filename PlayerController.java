package ai.KendallBenjiBot;

/**
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project Part B
 * Tutor: Matt De Bono 
 */ 

import aiproj.slider.Move;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List; 

 //piece is the parent class of all game pieces, contains a position object for its location 
 //board is made up of pieces 
public class PlayerController {
	protected BoardObj my_board; //it doesn't need to see the actual board representation 
	protected int dimension;
	protected char player; 
    protected char opponent;
    public TreeNode<BoardObj> our_move; //just randomly initialize 


	public PlayerController(int dimension, BoardObj board, char player){
		this.dimension = dimension;
		this.my_board = board;
		this.player = player; 
        if(player=='H')
        {
            this.opponent='V';
        }
        else
        {
            this.opponent='H';
        }
	}

	public ArrayList<Move> getPotentialMoves(BoardObj root, char p)
	{
		ArrayList<Move> moves = new ArrayList<Move>(); 
		for(int i=0; i<dimension; i++)
		{
			for(int j=0; j<dimension; j++)
			{
				if(root.getPiece(i,j) instanceof VerticalPiece && p == 'V')
				{
					VerticalPiece piece = (VerticalPiece) root.getPiece(i,j);
					Move[] hyp_movs = piece.hypotheticalMovesV(); 
					for(Move move: hyp_movs)
					{
						Position pos = new Position(0,0);  //java is so annoying 
						if(move.d == Move.Direction.UP)
						{
							pos = new Position(move.i, move.j+1); 
						}
						else if (move.d == Move.Direction.DOWN)
						{
							pos = new Position(move.i, move.j-1); 
						}
						else if(move.d== Move.Direction.LEFT)
						{
							pos = new Position(move.i-1, move.j);
						}
						else if(move.d == Move.Direction.RIGHT)
						{
							pos = new Position(move.i+1, move.j); 
						}
				
						if(root.checkVerticalSpot(pos))
						{
							moves.add(move); 
						}
					}
				}

                else if(root.getPiece(i,j) instanceof HorizontalPiece && p=='H')
                {
					HorizontalPiece piece = (HorizontalPiece) root.getPiece(i,j);
					Move[] hyp_movs = piece.hypotheticalMovesH(); 
					for(Move move: hyp_movs)
					{
						Position pos = new Position(0,0);  //initialize Position
						if(move.d == Move.Direction.UP)
						{
							pos = new Position(move.i, move.j+1); 
						}
						else if (move.d == Move.Direction.DOWN)
						{
							pos = new Position(move.i, move.j-1); 
						}
						else if(move.d== Move.Direction.LEFT)
						{
							pos = new Position(move.i-1, move.j);
						}
						else if(move.d == Move.Direction.RIGHT)
						{
							pos = new Position(move.i+1, move.j); 
						}
						if(root.checkHorizontalSpot(pos))
						{
							moves.add(move); 
						}
					}

                }
			}
		}
		return(moves);
	}

	//our evaluation function is to minimize the sum over our pieces of (the number of moves to go off the board + number of blocked spaces)
	//these 2 components to our featuers will likely need different weights, tbd later 
    public int playerCount(BoardObj bb, char pp){
        int numb = 0; 
        for(int i = 0; i<bb.getDimension(); i++)
        {
            for(int j=0; j<bb.getDimension(); j++)
            {
                if (pp=='V' && bb.getPiece(i,j) instanceof VerticalPiece)
                {
                    numb++; 
                }
                else if(pp=='H' && bb.getPiece(i,j) instanceof HorizontalPiece)
                {
                    numb++;
                }
            }
        }
        return numb;
    }

	public int evaluate(BoardObj curr_board, char p)
	{
		int evaluate = 0; 
        if(p == 'V'){
            int temp1 = playerCount(curr_board,p);
            evaluate+=temp1;
        }
        else if(p == 'H'){
            int temp2 = playerCount(curr_board,p);
            evaluate+=temp2;
        }

		for(int i = 0; i<curr_board.getDimension(); i++)
		{
			for(int j=0; j<curr_board.getDimension(); j++)
			{
				if (p=='V' && curr_board.getPiece(i,j) instanceof VerticalPiece)
				{
					int comp_1 = (curr_board.getDimension()-j)+20;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=j; n<curr_board.getDimension();n++)
					{
						if(!(curr_board.getPiece(i,n) instanceof BlankSpace)&& !(curr_board.getPiece(i,n) instanceof VerticalPiece)) //ie there is something blocking our piece
						{
							comp_2+=1; 
						}
					}

					evaluate += comp_1+comp_2; 
				}

				else if (p=='H' && curr_board.getPiece(i,j) instanceof HorizontalPiece)
				{   
                
					int comp_1 = (curr_board.getDimension()-i)+20;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=i; n<curr_board.getDimension(); n++)
					{
						if(!(curr_board.getPiece(n,j) instanceof BlankSpace)&& !(curr_board.getPiece(n,j) instanceof HorizontalPiece)) //ie there is something blocking our piece
						{
                            
							comp_2+=1; 
						}
					}
					evaluate += comp_1+comp_2; 

				}
			}
		}
		return(evaluate); 
	}

    public BoardObj getBoard()
    {
        return this.my_board; 
    }

    public void setBoard(BoardObj b)
    {
        my_board = b; 
    }

	public BoardObj update(Move move, BoardObj board, char p)
    {
        BoardObj new_board = new BoardObj(board.getDimension(), board.getWorld());
		if(p=='V')
		{
        	if(move.d == Move.Direction.UP)
        	{
            	new_board.setPiece(move.i, move.j+1, new HorizontalPiece(new Position(move.i, move.j+1),'H'));
        	}
        	else if(move.d==Move.Direction.DOWN)
        	{
            	new_board.setPiece(move.i, move.j-1, new HorizontalPiece(new Position(move.i, move.j-1),'H'));
       		}
        	else if(move.d==Move.Direction.LEFT)
        	{
            	new_board.setPiece(move.i-1, move.j, new HorizontalPiece(new Position(move.i-1, move.j),'H'));
        	}
        	else if(move.d==Move.Direction.RIGHT)
        	{
                if(move.i+1>=dimension) //if their player went off the board 
                {}
                else
                {
            	    new_board.setPiece(move.i+1, move.j, new HorizontalPiece(new Position(move.i+1, move.j),'H'));
                }
        	}

			new_board.setPiece(move.i, move.j, new BlankSpace(new Position(move.i, move.j), '+'));		
    	}
		else if(p=='H')
		{
			if(move.d == Move.Direction.UP)
        	{
                if(move.j+1 >= dimension)  //if their player went off the board 
                {

                }
                else
                {
            	    new_board.setPiece(move.i, move.j+1, new VerticalPiece(new Position(move.i, move.j+1),'V'));
                }
        	}
        	else if(move.d==Move.Direction.DOWN)
        	{
            	new_board.setPiece(move.i, move.j-1, new VerticalPiece(new Position(move.i, move.j-1),'V'));
       		}
        	else if(move.d==Move.Direction.LEFT)
        	{
            	new_board.setPiece(move.i-1, move.j, new VerticalPiece(new Position(move.i-1, move.j),'V'));
        	}
        	else if(move.d==Move.Direction.RIGHT)
        	{
            	new_board.setPiece(move.i+1, move.j, new VerticalPiece(new Position(move.i+1, move.j),'V'));
        	}

			new_board.setPiece(move.i, move.j, new BlankSpace(new Position(move.i, move.j), '+'));
		}
		else
		{
			System.out.println("player symbol error");
		}

        return(new_board); 
	}

    public void generate_Tree(ArrayList<Move> moves, TreeNode<BoardObj> parent, char p, int n)
    {
        
        if(!(moves.isEmpty()))
        {
            for(Move m:moves)
            {
                BoardObj new_board = update(m, parent.data, opposite_player(p)); 
                //^^SHOULD return a separate board that is updated, should NOT update parent itself 
                int eval = evaluate(new_board, player); 
                
                TreeNode<BoardObj> child = new TreeNode<BoardObj>(new_board, eval); 
                parent.addChild(child);                 
                if((child.getLevel())< n)
                {
                    ArrayList<Move> new_movs = getPotentialMoves(new_board, opposite_player(p)); 
                    generate_Tree(new_movs, child, opposite_player(p), n);

                }
                
            }
        }
        
    }

    public char opposite_player(char p)
    {
        if(p=='H')
        {
            return('V');
        }
        else
        {
            return('H'); 
        }
    }
    public int minimax(TreeNode<BoardObj> r)
    {
        if(r.children.isEmpty())//leaf nodes
        {
            return(r.eval); 
        }
        else if(r.getLevel()%2!=0)//maximizing level as we are trying to minimize our function 
        {
            int v = -1;
            int beta = -1; //beta for pruning
            for(TreeNode<BoardObj> c: r.children)
            {
                int v_prime = minimax(c); 
                if(v_prime > beta){ //alpha-beta pruning
                    beta = v_prime;
                    v = v_prime; 
                    break;
                }
                if(v_prime > v)
                {
                    beta = v_prime;
                    v = v_prime;
                }
            }
            return(v);   
        }
        else if(r.getLevel()%2==0) //minimizing level
        {
            int v = Integer.MAX_VALUE; 
            for(TreeNode<BoardObj> c: r.children)
            {
                int v_prime = minimax(c);
                if(v_prime <= v)
                {
                    v = v_prime;
                    if(c.getLevel()==1)
                    {
                        this.our_move = c;  
                    }
                }            
            }
            return(v); 
        }

        return -1; //something went wrong  
     }

    public Move move()
    {
		ArrayList<Move> level_one = getPotentialMoves(my_board, player); 
        int eval = evaluate(my_board, player);
        TreeNode<BoardObj> root = new TreeNode<BoardObj>(my_board, eval);
        generate_Tree(level_one,root,player,5);
        int value = minimax(root); 

        TreeNode<BoardObj> parent = our_move.parent; 
        int index = parent.children.indexOf(our_move);
      
        Move mv = new Move(1,1,Move.Direction.UP);   
        if(!level_one.isEmpty()){    
            if(index>=0)
            {
              
               mv = level_one.get(index); 
            }
        }
        
        System.out.println(player); 
        this.my_board = update(mv, my_board, opponent); 
        
        if(level_one.isEmpty())
        {
            return(null); 
        }
       System.out.println("Move:" + mv.toString()); 
       return(mv); 
    }   	
	
}

