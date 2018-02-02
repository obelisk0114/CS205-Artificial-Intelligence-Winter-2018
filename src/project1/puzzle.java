package project1;

public class puzzle {
	private int[][] board;
	private int[] blankIndex = new int[2];
	private int depth;
	
	public puzzle() {
		board = new int[][] { { 5, 1, 7, 3 }, { 9, 2, 11, 4 }, 
			{ 13, 6, 15, 8 }, { 0, 10, 14, 12 } };
		blankIndex[0] = 3;    // y
		blankIndex[1] = 0;    // x
		depth = 0;
	}
	
	public puzzle(int total) {
		int length = (int) Math.sqrt(total + 1);
		if((total + 1) != length * length) {
			throw new RuntimeException("It is not a square.");
		}
		board = new int[length][length];
	}
	
	public puzzle(int x, int y) {
		board = new int[y][x];
	}
	
	public puzzle(puzzle aPuzzleSolver) {
		if (aPuzzleSolver == null) {     // Not a real puzzleSolver.
			System.out.println("Fatal Error.");
			System.exit(1);
		}

		board = aPuzzleSolver.board;
		blankIndex = aPuzzleSolver.blankIndex;
		depth = aPuzzleSolver.depth;
	}
	
	public void setBoard(int[][] values) {
		OuterLoop:
		for(int i = 0; i < values.length; i++){
			for (int j = 0; j < values[i].length; j++) {
				if(values[i][j]==0){
					blankIndex[0] = i;    // y
					blankIndex[1] = j;    // x
					break OuterLoop;
				}
			}
		}
		board = values;
		depth = 0;
	}
	
	boolean isSolved() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != (i * board[0].length + j + 1) && 
						!(i == (board.length - 1) && j == (board[0].length - 1))) {
					return false;
				}
			}
		}
		return true;
	}
	
	// movement functions
	puzzle moveSpaceUp(){
		puzzle clone = this.clone();
		if(blankIndex[0] == 0){
			return null;
		}
		clone.swap(blankIndex[1], blankIndex[0], blankIndex[1], blankIndex[0] - 1);
		clone.blankIndex[0] -= 1;
		clone.depth++;
		return clone;
	}
	
	puzzle moveSpaceDown(){
		puzzle clone = this.clone();
		if(blankIndex[0] == board.length - 1){
			return null;
		}
		clone.swap(blankIndex[1], blankIndex[0], blankIndex[1], blankIndex[0] + 1);
		clone.blankIndex[0] += 1;
		clone.depth++;
		return clone;
	}
	
	puzzle moveSpaceLeft(){
		puzzle clone = this.clone();
		if(blankIndex[1] == 0){
			return null;
		}
		clone.swap(blankIndex[1], blankIndex[0], blankIndex[1] - 1, blankIndex[0]);
		clone.blankIndex[1] -= 1;
		clone.depth++;
		return clone;
	}
	
	puzzle moveSpaceRight(){
		puzzle clone = this.clone();
		if(blankIndex[1] == board[0].length - 1){
			return null;
		}
		clone.swap(blankIndex[1], blankIndex[0], blankIndex[1] + 1, blankIndex[0]);
		clone.blankIndex[1] += 1;
		clone.depth++;
		return clone;
	}
	
	// heuristics
	int misplacedTile() {
		int misplaced = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != i * board[0].length + j + 1 && board[i][j] != 0) {
					misplaced++;
				}
			}
		}
		return misplaced;
	}
	
	int manhattenDistance() {
		int distance = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != 0) {
					int x = (board[i][j] - 1) % board[0].length;
					int y = (board[i][j] - 1) / board[0].length;
					distance = distance + Math.abs(x - j) + Math.abs(y - i);
				}
			}
		}
		return distance;
	}
	
	boolean checkSquareSolvability() {
		int inversion = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != 0) {
					//int tmp = inversion;
					int cur = i * board[0].length + j;
					while (cur < board.length * board[0].length) {
						int i1 = cur / board[0].length;
						int j1 = cur % board[0].length;
						if (board[i1][j1] < board[i][j] && board[i1][j1] != 0) {
							inversion++;
						}
						cur++;
					}
					//System.out.println("Inversion in " + board[i][j] + " = " + (inversion - tmp));
				}
			}
		}
		
		//System.out.println("Inversion = " + inversion);
		
		// If the grid width is odd, then the number of inversions in a solvable 
		// situation is even.
		if ((board.length & 1) == 1) {
			if ((inversion & 1) == 0)
				return true;
			else
				return false;
		}
		else {
			int reverseBlankRow = board.length - blankIndex[0];
			
			// If the grid width is even, and the blank is on an even row counting 
			// from the bottom (second-last, fourth-last etc), then the number of 
			// inversions in a solvable situation is odd.
			if ((reverseBlankRow & 1) == 0) {
				if ((inversion & 1) == 1)
					return true;
				else
					return false;
			}
			// If the grid width is even, and the blank is on an odd row counting 
			// from the bottom (last, third-last, fifth-last etc) then the number of 
			// inversions in a solvable situation is even.
			else {
				if ((inversion & 1) == 0)
					return true;
				else
					return false;
			}			
		}
	}
	
	void swap(int x1, int y1, int x2, int y2) {
		int tmp = board[y1][x1];
		board[y1][x1] = board[y2][x2];
		board[y2][x2] = tmp;
	}
	
	@Override
	protected puzzle clone() {
		try {
			puzzle copy = (puzzle) super.clone();
			copy.board = board.clone();
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}
	
	public boolean equals(puzzle otherPuzzleSolver) {
		if (otherPuzzleSolver == null)
			return false;
		else {
			return (this.toString().equals(otherPuzzleSolver.toString()));
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int y = board.length;
		int x = board[0].length;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				sb.append(board[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
