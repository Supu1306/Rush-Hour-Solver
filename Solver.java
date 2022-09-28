package rushhour;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Solver
{

	static int ans_count = 0;
	static Stack<String> answer = new Stack<String>();
	static int rightcount=0;
	static int leftcount=0;
	static int downcount=0;
	static int upcount=0;
	static int count = 0;

	static int found = 0;
	static Node<Object> find_parents = new Node<Object>();
	static ArrayList<Node> nodes_list = new ArrayList<Node>();
	static ArrayList<Integer> up_move =new ArrayList<Integer>();
	static ArrayList<Integer> down_move =new ArrayList<Integer>();
	static ArrayList<Integer> right_move =new ArrayList<Integer>();
	static ArrayList<Integer> left_move =new ArrayList<Integer>();
	
	static Queue<Node> next_board = new LinkedList<Node>();
	
	private static ArrayList <Point> car_holder = new ArrayList<>();
	
	private static char[][] board = new char[6][6];
	
	private static char[] ch1 = new char [6];
	private static char[] ch2 = new char [6];
	private static char[] ch3 = new char [6];
	private static char[] ch4 = new char [6];
	private static char[] ch5 = new char [6];
	private static char[] ch6 = new char [6];
	
	private static String s1;
	private static String s2;
	private static String s3;
	private static String s4;
	private static String s5;
	private static String s6;
	
	//compares two boards returns true if the boards are not equal.
	public static boolean compare_boards(char[][] board1, char[][] board2)             
	{
		for(int i = 0; i< 6;i++)
		{
			for(int j = 0; j<6;j++)
			{
				if(board1[i][j] != board2[i][j])
					return true;
			}
		}
		return false;
	}
	// checks that the new board made is already in the nodes_list (list to store all the boards(nodes) in the program) or not
	public static boolean same_board_check(Node<?> check)
	{
		for(int i = 0; i<nodes_list.size();i++)
		{
			char[][] temp = nodes_list.get(i).getdata();
			if(!compare_boards(temp,check.getdata()))
				return true;
		}
		return false;
	}
	//checks if car X has reached the exit point or not
	private static boolean check_board(Node<Object> v1)
	{
		ArrayList <Point> temp_car_holder = new ArrayList<>();
			char[][] v1_board = new char[6][6];
			v1_board = v1.getdata();
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<6;j++)
				{
					if(v1_board[i][j]=='X') 
					{
						temp_car_holder.add(new Point(i,j));
					}
				}
			}
			Point p = temp_car_holder.get(temp_car_holder. size()-1);
			if(p.y==5)
				return true;
			
			return false;
	}
	//adds new nodes to the tree, it takes a Node as its input and creates its children. 
	private static void add_node(Node<Object> node)
	{	
			car_holder.clear();
			ArrayList<Character> cars_names = new ArrayList<Character>();
			cars_names = find_cars(node.getdata());
			ArrayList <Point> car_holder = new ArrayList<>();
			Iterator<Character> it = cars_names.iterator();
			while(it.hasNext()) 
			{
				car_holder.clear();
				char temp8 = it.next();
				car_holder = find_specific_car(temp8,node.getdata());
				find_moves_for_each_car(car_holder,temp8,node.getdata());
				
				if(!up_move.isEmpty())
		    	{	
		    		Point temp;
		    		for(int j = 0; j < up_move.size();j++)
		    		{
		    			char[][] output = new char[6][6];
						for(int i1=0; i1< 6; i1++)
							for(int j1=0;j1<6;j1++)
								output[i1][j1]=node.getdata()[i1][j1];
						for(int i1 = 0 ;i1<car_holder.size();i1++)
						{
							temp = car_holder.get(i1);
							output[temp.x-(up_move.get(j))][temp.y]=output[temp.x][temp.y];
							output[temp.x][temp.y]='.';
							
						}
						String vertex_name = temp8+"U"+up_move.get(j);
						Node<Object> v1 = new Node<Object>(vertex_name,output);
						
						if(!same_board_check(v1))
						{
							next_board.add(v1);
							nodes_list.add(v1);
							v1.setParent(node);
							if(check_board(v1))
							{
								found = 1;
								find_parents = v1;
								return;
							}
						}
						upcount--;
						if(upcount == 0)
						{
							up_move.clear();
						}
						car_holder.clear();
						find_specific_car(temp8,node.getdata());
		    		}
		    	}
				if(!down_move.isEmpty())
	        	{
	        		Point temp1;
	        		for(int j = 0; j < down_move.size();j++)
	        		{
	        			char[][] output1 = new char[6][6];
	    				for(int i1=0; i1< 6; i1++)
	    					for(int j1=0;j1<6;j1++)
	    						output1[i1][j1]=node.getdata()[i1][j1];
	    				for(int i1 = car_holder.size()-1 ;i1>=0;i1--)
	    				{
	    					temp1 = car_holder.get(i1);
	    					output1[temp1.x+(down_move.get(j))][temp1.y]=output1[temp1.x][temp1.y];
	    					output1[temp1.x][temp1.y]='.';
	    					
	    				}
	    				String vertex_name = temp8+"D"+down_move.get(j);
	    				Node<Object> v1 = new Node<Object>(vertex_name,output1);
	    				
	    				if(!same_board_check(v1))
	    				{
	    					next_board.add(v1);
	    					nodes_list.add(v1);
		    				v1.setParent(node);
		    				if(check_board(v1))
		    				{
		    					found = 1;
		    					find_parents = v1;
		    					return;
		    				}
	    				}
	    				downcount--;
	    				
	    				if(downcount == 0)
	    				{
	    					down_move.clear();
	    				}
	    				car_holder.clear();
	    				find_specific_car(temp8,node.getdata());
	    				
	           		}
	        	}
	    	if(!left_move.isEmpty())
	    	{
	    		Point temp;
	    		for(int j = 0; j< left_move.size();j++)
	    		{
	    			char[][] output1 = new char[6][6];
					for(int i1=0; i1< 6; i1++)
						for(int j1=0;j1<6;j1++)
							output1[i1][j1]=node.getdata()[i1][j1];
					for(int i1 = 0 ;i1<car_holder.size();i1++)
					{
						temp=car_holder.get(i1);
						output1[temp.x][temp.y-(left_move.get(j))]=output1[temp.x][temp.y];
						output1[temp.x][temp.y]='.';
					}
					String vertex_name = temp8+"L"+left_move.get(j);
					Node<Object> v1 = new Node<Object>(vertex_name,output1);
					
					if(!same_board_check(v1))
					{
						next_board.add(v1);
						nodes_list.add(v1);
						v1.setParent(node);
						if(check_board(v1))
						{
							found = 1;
							find_parents = v1;
							return;
						}
					}
					leftcount--;
					if(leftcount == 0)
					{
						left_move.clear();
					}
					car_holder.clear();
					find_specific_car(temp8,node.getdata());
	    		}
	    	}
	    	if(!right_move.isEmpty())
	    	{
	    		Point temp;
	    		for(int j = 0; j < right_move.size();j++)
	    		{
	    			char[][] output1 = new char[6][6];
					for(int i1=0; i1< 6; i1++)
						for(int j1=0;j1<6;j1++)
							output1[i1][j1]=node.getdata()[i1][j1];
					for(int i1 = car_holder.size()-1 ;i1>=0;i1--)
					{
						temp =car_holder.get(i1);
						output1[temp.x][temp.y+(right_move.get(j))]=output1[temp.x][temp.y];
						output1[temp.x][temp.y]='.';
					}
					String vertex_name = temp8+"R"+right_move.get(j);
					Node<Object> v1 = new Node<Object>(vertex_name,output1);
					if(!same_board_check(v1))
					{
						next_board.add(v1);
						nodes_list.add(v1);
						v1.setParent(node);
						if(check_board(v1))
						{
							next_board.add(v1);
							found = 1;
							find_parents = v1;
							return;
						}
					}
					rightcount--;
					if(rightcount == 0)
					{
						right_move.clear();
					}
					car_holder.clear();
					car_holder = find_specific_car(temp8,node.getdata());
	    		}
	    	}
		}
			cars_names.clear();
		up_move.clear();
		down_move.clear();
		left_move.clear();
		right_move.clear();
		upcount=0;
		leftcount=0;
		downcount=0;
		rightcount=0;
	}
	//finds the path from the original board (root) to this node by finding the parents of the nodes
	private static void find_answer(Node<Object> find_parent)
	{
		if(find_parent == null)
			return;
		else
		{
			Node<Object> sample = find_parent;
			//System.out.println(sample.getName());
			answer.push(sample.getName());
			while(sample.getParent()!=null)
			{
				//System.out.println(sample.getParent().getName());
				//sample.getParent().print_board();
				answer.push(sample.getParent().getName());
				//System.out.println();
				sample = sample.getParent();
				
			}
		}
		answer.pop();
	}
	//The original board given by the user is read as 6 strings.
	//This function converts those 6 strings into 6 arrays of char type and makes a 2D char array out of them called board.
    private static void convert()
    {
    	for(int i=0; i<6;i++)
    		ch1[i] =s1.charAt(i);
    	
    	for(int i=0; i<6;i++)
    		ch2[i] =s2.charAt(i);
    	
    	for(int i=0; i<6;i++)
    		ch3[i] =s3.charAt(i);
    	
    	for(int i=0; i<6;i++)
    		ch4[i] =s4.charAt(i);
    
    	for(int i=0; i<6;i++)
    		ch5[i] =s5.charAt(i);
    	
    	for(int i=0; i<6;i++)
    		ch6[i] =s6.charAt(i);
    	
    	for(int i=0;i<6;i++)
    		board[0][i]=ch1[i];
    	
    	for(int i=0;i<6;i++)
    		board[0][i]=ch1[i];
    	
    	for(int i=0;i<6;i++)
    		board[1][i]=ch2[i];
    	
    	for(int i=0;i<6;i++)
    		board[2][i]=ch3[i];
    	
    	for(int i=0;i<6;i++)
    		board[3][i]=ch4[i];
    	
    	for(int i=0;i<6;i++)
    		board[4][i]=ch5[i];
    	
    	for(int i=0;i<6;i++)
    		board[5][i]=ch6[i];
    	
    
    }
    // takes a board as an input and finds different cars (like A, B, C) on it.  
	private static ArrayList<Character> find_cars(char[][] board)
	{
		ArrayList<Character> cars_name = new ArrayList<Character>();
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<6;j++)
			{
				if(board[i][j]>='A'&&board[i][j]<='Z') 
				{
					if(!cars_name.contains(board[i][j]))
					cars_name.add(board[i][j]);
				}
			}
		
		}
		return cars_name;	
	}
	//finds the position of the car on the board as 2D points on it and stores it in an arraylist of Points called car_holder
	private static ArrayList<Point> find_specific_car(char a, char[][] board)
	{
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<6;j++)
			{
				if(board[i][j]==a) 
				{
					car_holder.add(new Point(i,j));
				}
			}
		
		}
		return car_holder;	
	}
	//finds all the moves for the car and stores them in arraylists of integers 
	private static void find_moves_for_each_car(ArrayList <Point> car_holder,char x,char[][] board)
	{
		boolean check=false;
		Point p1;
		Point p2;
		p1=car_holder.get(0);
		p2=car_holder.get(1);
		Point tempright = car_holder.get(car_holder.size()-1);
		Point templeft = car_holder.get(0);
		Point tempdown = car_holder.get(car_holder.size()-1);
		Point tempup = car_holder.get(0);
			if(p1.x==p2.x)
				check=true;
			if(check==true) 
			{
				for(int i = 1; i<6; i++ )
				{
						if(tempright.y+i>=6)
						{
							break;
						}
						if(board[tempright.x][tempright.y+i]!='.')
						{	
							break;
						}
						if(board[tempright.x][tempright.y+i]=='.')
						{	
							rightcount++;
							right_move.add(rightcount);
						}
				}
				for(int i = 1; i<6; i++ )
				{
						if(templeft.y-i<0)
							break;
						if(board[templeft.x][templeft.y-i]!='.')
							break;
						if(board[templeft.x][templeft.y-i]=='.')
						{	
							leftcount++;
							left_move.add(leftcount);
						}	
				}
				}
			else
			{
				for(int i = 1; i<6; i++ )
				{
						if(tempdown.x+i>=6)
						{
							break;
						}
						if(board[tempdown.x+i][tempdown.y]!='.')
						{	
							break;
						}
						if(board[tempdown.x+i][tempdown.y]=='.')
						{	
							downcount++;
							down_move.add(downcount);
						}
				}
				for(int i = 1; i<6; i++ )
				{
						if(tempup.x-i<0)
							break;
						if(board[tempup.x-i][tempup.y]!='.')
							break;
						if(board[tempup.x-i][tempup.y]=='.')
						{
							upcount++;
							up_move.add(upcount);
						}
				}
			}
	}
    public static void solveFromFile(String input, String output) throws FileNotFoundException {
    	File inputfile = new File(input);
    	Scanner reader = new Scanner(inputfile);
		s1 = reader.nextLine();
		s2 = reader.nextLine();
		s3 = reader.nextLine();
		s4 = reader.nextLine();
		s5 = reader.nextLine();
		s6 = reader.nextLine();
		
		reader.close();
		convert();
		
		Node<Object> v = new Node<Object>("original", board);
		if(check_board(v))
		{
			return;
		}
		next_board.add(v);
		nodes_list.add(v);
		while(found<1)
		{
			add_node(next_board.poll());
		}
		find_answer(find_parents);	
		
            File outputfile = new File(output);

            PrintWriter outputwriter = new PrintWriter(outputfile);
            while(answer.size()!=0)
            {
            	outputwriter.print(answer.pop() + "\n");
            }
            outputwriter.close();
            //System.out.println("task completed");
    }
    /*
    public static void main(String[] args) throws FileNotFoundException {
		solveFromFile("C:\\Users\\supre\\Desktop\\java work\\assignment3\\D34.txt","C:\\Users\\supre\\Desktop\\java work\\assignment3\\A00.sol");
	}
	*/
}