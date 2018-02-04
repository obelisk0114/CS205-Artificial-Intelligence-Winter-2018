package project1;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;

public class puzzleSolver {

	public static void main(String[] args) {
		/**     For a puzzle generator
		//puzzleGenerator puzzle = new puzzleGenerator(7, 3);
		puzzleGenerator puzzle = new puzzleGenerator(8);
		puzzle.generate();
		*/
		
		puzzle puzzleBD;
		BoardComparator heuristic;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("This is a n-puzzle solver.\n"
				+ "Choose 1 for a square puzzle.\n"
				+ "Choose 2 for a rectangular one.\nChoose 0 for a default one.");
		int xLength = -1;
		int yLength = -1;
		int userChoice = keyboard.nextInt();
		switch(userChoice){
		case 0:
			puzzleBD = new puzzle();
			break;
		case 1:
			System.out.println("Enter the width.");
			xLength = keyboard.nextInt();
			yLength = xLength;
			int total = xLength * xLength - 1;
			puzzleBD = new puzzle(total);
			puzzleBD.setBoard(inputPuzzle(keyboard, xLength, yLength));
			break;
		case 2:
			System.out.println("Enter the number of columns (x).");
			xLength = keyboard.nextInt();
			System.out.println("Enter the number of rows (y).");
			yLength = keyboard.nextInt();
			puzzleBD = new puzzle(xLength, yLength);
			puzzleBD.setBoard(inputPuzzle(keyboard, xLength, yLength));
			break;
		default:
			keyboard.close();
			throw new RuntimeException("Unacceptable input");
		}
		
		System.out.println("\nEnter your choice of algorithm");
		System.out.println("1.	Uniform Cost Search");
		System.out.println("2.	A* with the Misplaced Tile heuristic.");
		System.out.println("3.	A* with the Manhattan distance heuristic.");
		userChoice = keyboard.nextInt();
		switch(userChoice){
		case 1:
			heuristic = new UniformCostComparator();
			break;
		case 2:
			heuristic = new MisplacedTile();
			break;
		case 3:
			heuristic = new ManhattenDistance();
			break;
		default:
			keyboard.close();
			throw new RuntimeException("Unacceptable input");
		}
		keyboard.close();
		
		if (xLength == yLength && !puzzleBD.checkSquareSolvability()) {			
			System.out.println("Unsolvable square puzzle.");
		}
		search(puzzleBD, heuristic);
	}
	
	static boolean search(puzzle init, BoardComparator cmp) {
		long startTime = System.currentTimeMillis();

		int maxNodes = 0;
		int totalNodes = 0;
		PriorityQueue<puzzle> nodes = new PriorityQueue<puzzle>(1, cmp);
		checkNode(nodes, init);
		while (!nodes.isEmpty()) {
			;
		}
		return false;
	}
	
	static int[][] inputPuzzle(Scanner keyboard, int xLength, int yLength) {
		int[][] input = new int[yLength][xLength];
		System.out.println("Enter your puzzle, use a zero to represent the blank");
		for (int i = 0; i < yLength; i++) {
			System.out.printf("Enter the %dth row, use space or tabs between numbers    ", i + 1);
			for (int j = 0; j < xLength; j++) {
				input[i][j] = keyboard.nextInt();
			}
		}
		return input;
	}
	
	static boolean checkNode(PriorityQueue<puzzle> nodes, puzzle b) {
		if (b != null) {
			if (b.isSolved()) {
				System.out.println("Goal!!");
				System.out.print(b);
				return true;
			}
			nodes.add(b);
		}
		return false;
	}
	
}

interface BoardComparator extends Comparator<puzzle> {
	int heuristic(puzzle b);
}

class UniformCostComparator implements BoardComparator {
	@Override
	public int compare(puzzle o1, puzzle o2) {
		return o1.getDepth() - o2.getDepth();
	}

	public int heuristic(puzzle b) {
		return 0;
	}
}

class MisplacedTile implements BoardComparator {
	@Override
	public int compare(puzzle o1, puzzle o2) {
		return (o1.getDepth() + o1.misplacedTile()) 
				- (o2.getDepth() + o2.misplacedTile());
	}

	public int heuristic(puzzle b) {
		return b.misplacedTile();
	}
}

class ManhattenDistance implements BoardComparator {
	@Override
	public int compare(puzzle o1, puzzle o2) {
		return (o1.getDepth() + o1.manhattenDistance()) 
				- (o2.getDepth() + o2.manhattenDistance());
	}

	public int heuristic(puzzle b) {
		return b.manhattenDistance();
	}
}
