package rushhour;

public class Node<T> {
	
	private String name;

	private char[][] board = new char[6][6];
	
	Node<T> parent;
	
	public Node<T> getParent() {
		return parent;
	}
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	public Node(String name, char[][] board) {
		this.name = name;
		this.board = board;
	}
	public Node()
	{
		this.name = name;
		this.board = board;
	}
	public void addData(char[][] board)
	{
		this.board = board;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void print_board()
	{
		for(int i = 0 ; i<6;i++)
		{	for(int j = 0; j<6;j++)
				System.out.print(board[i][j]);
		System.out.println();
		}
	}
	public char[][] getdata() {
		return board;
	}

}


