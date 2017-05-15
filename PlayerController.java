package ai.partB;

/**
 *
 * @author Kendall McCormick (880456), Benjamin Taubenblatt (890808)
 * COMP30024: Project 1
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
				if(root.getPiece(i,j) instanceof VerticalPiece && p=='V')
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
	public int evaluate(BoardObj curr_board, char p)
	{
		int evaluate = 0; 
		for(int i =0; i<curr_board.getDimension(); i++)
		{
			for(int j=0; j<curr_board.getDimension(); j++)
			{
				if (p=='V' && curr_board.getPiece(i,j) instanceof VerticalPiece)
				{
					int comp_1 = dimension-j;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=0; n<curr_board.getDimension();n++)
					{
						if(!(curr_board.getPiece(i,n) instanceof BlankSpace)&& !(curr_board.getPiece(n,j) instanceof VerticalPiece)) //ie there is something blocking our piece
						{
							comp_2++; 
						}
					}
					evaluate += comp_1+comp_2; 
				}

				else if (p=='H' && curr_board.getPiece(i,j) instanceof HorizontalPiece)
				{
					int comp_1 = dimension-i;  //how many spaces til it goes off the board 
					//check rest of that column to see how many pieces are blocking this piece 
					int comp_2 = 0; 
					for(int n=0; n<curr_board.getDimension(); n++)
					{
						if(!(curr_board.getPiece(n,j) instanceof BlankSpace)&& !(curr_board.getPiece(n,j) instanceof HorizontalPiece)) //ie there is something blocking our piece
						{
                            //System.out.println("" + n+"," + j);
                            //System.out.println("piece:" + curr_board.getPiece(n,j).getI());
							comp_2++; 
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
        //GeneralPiece curr_pos = super.my_board.getPiece(move.i, move.j); 
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
                {}
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
        //System.out.println("Parent Node Level:" + parent.getLevel());
        //System.out.println("Number moves:" + moves.size());
        //parent.data.pretty_print(); 
        if(!(moves.isEmpty()))
        {
            for(Move m:moves)
            {
                //System.out.println("Level" + parent.getLevel()); 
                BoardObj new_board = update(m, parent.data, opposite_player(p)); 
                //^^SHOULD return a separate board that is updated, should NOT update parent itself 
                //System.out.println("player:" + player);
               //System.out.println("Child Node:"); 
                int eval = evaluate(new_board, player); 
                //System.out.println("Evaluation:" + eval);
                //new_board.pretty_print(); 
                TreeNode<BoardObj> child = new TreeNode<BoardObj>(new_board, eval); 
                parent.addChild(child);                 
                if((child.getLevel())< n)
                {
                   // int new_n = n+1; 

                    ArrayList<Move> new_movs = getPotentialMoves(new_board, opposite_player(p)); 
                    generate_Tree(new_movs, child, opposite_player(p), n);

                }
                else
                {
                    //System.out.println("done"); 
                }
            }
        }
        else
        {
            //System.out.println("game over"); 
        }
       /* System.out.println("new level");
        b.data.pretty_print(); 
        System.out.println("Player:" + p);
        if(n==0)
        {
            return; 
        }
        ArrayList<Move> moves = getPotentialMoves(b.data, p); 
        if(moves.isEmpty())//end branch of the tree 
        {
            return; 
        }
        //b.eval = evaluate(b.data, p);
        for(Move m: moves){
            System.out.println("Which level is this?"+ n); 
            System.out.println(m.toString()); 
            b.data.pretty_print(); 
            BoardObj temp_board = new BoardObj(b.data.getDimension(),b.data.getWorld());
			update(m, temp_board, opposite_player(p));
            System.out.println("\n");
            temp_board.pretty_print();  
			b.addChild(temp_board);
            generate_Tree(b.children.get(0), opposite_player(p), n-1); 
        }*/
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
            //this.our_move = r; 
            return(r.eval); 
        }
        else if(r.getLevel()%2!=0)//maximizing level
        {
            //System.out.println("r level" + r.getLevel());
            //System.out.println("test_maximizing level");
            int v = -1;
            for(TreeNode<BoardObj> c: r.children)
            {
                int v_prime = minimax(c); 
                if(v_prime>v)
                {
                    v = v_prime;
                    //System.out.println("v increased:" + v);
                }
            }
            return(v);   
        }
        else if(r.getLevel()%2==0) //minimizing level
        {
            //System.out.println("r level" + r.getLevel());
            //System.out.println("test_minimizing level");
            int v= Integer.MAX_VALUE; 
            for(TreeNode<BoardObj> c: r.children)
            {
                int v_prime = minimax(c);
                if(v_prime<v)
                {
                    v = v_prime;
                    if(c.getLevel()==1)
                    {
                        this.our_move = c;  
                        //this.our_move.data.pretty_print();
                    }
                    //System.out.println("v decreased:" + v);
                }            
            }
            return(v); 
        }

        return -1; //something went wrong  
     }

    public Move move()
    {
        System.out.println("Controller Board:");
        my_board.pretty_print(); 
		ArrayList<Move> level_one = getPotentialMoves(my_board, player); 
        int eval = evaluate(my_board, player);
        TreeNode<BoardObj> root = new TreeNode<BoardObj>(my_board, eval);
        //System.out.println("first player" + player);
        //System.out.println("First eval"+ eval);
        //root.eval = evaluate(my_board, player); 
        generate_Tree(level_one,root,player,4);
        int value = minimax(root); 
        //System.out.println("Utility:" + value);
        //System.out.println("Level" + our_move.getLevel()); 
        our_move.data.pretty_print(); 

        TreeNode<BoardObj> parent = our_move.parent; 
        int index = parent.children.indexOf(our_move);
      /*  for(int i=0; i<parent.children.size(); i++)
        {
            if(parent.children.get(i)==our_move)
            {
                index = i; 
            }
        }*/
        System.out.println("index" + index);
        Move mv = new Move(1,1,Move.Direction.UP);         
        if(index>=0)
        {
           mv = level_one.get(index); 
        }
        else
        {
            System.out.println("idk...");
        }
        
        //TreeNode<BoardObj> current = root; 

       /* int i=0; 
        //if there is a layer with no children 
        while(i<5 && !(current.children.isEmpty()))
        {
            for(TreeNode<BoardObj> c : current.children){
                if(c.getLevel()%2 == 0){
                    generate_Tree(c,player);
                } else {
                    generate_Tree(c,opponent);
                }
            }
            current = c; 
            i++; 
        }*/
        System.out.println(player); 
        this.my_board = update(mv, my_board, opponent); 
        //System.out.println("Player:" + player); 
        //System.out.println("" + mv.i +  "," + mv.j + "," + mv.d); 
        if(level_one.isEmpty())
        {
            return(null); 
        }
       // return(level_one.get(0)); 
       System.out.println("Move:" + mv.toString()); 
       return(mv); 
    }   
	
	/*public String[][] create_board_representation(String board)//create a board representation from String 
	{
		String [][] arr_board;
		String board_nospace = board.replaceAll("\\s+",""); //delete all white space
		String lines[] = board_nospace.split("\\r?\\n"); //split into array based on skipline characters
		for(int i=0; i<lines.length; i++)
		{
			for(int j=0; i<lines[i].length();i++)
			{
				arr_board[i][j] = lines[i].charAt(j); 
			}
		} 
		return arr_board;
	}*/
	
	
}

