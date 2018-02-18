package project1;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;

public class puzzleSolver {

	public static void main(String[] args) {
		/**     For a puzzle generator     **/
		/*
		//puzzleGenerator puzzle = new puzzleGenerator(7, 3);
		puzzleGenerator puzzle = new puzzleGenerator(8);
		//puzzle.setDiameter(100);
		puzzle.generate();
		*/
		
		puzzle puzzleBD;
		puzzleComparator heuristic;
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
			throw new RuntimeException("Unacceptable input");
		}
		keyboard.close();
		
		if (xLength == yLength && !puzzleBD.checkSquareSolvability()) {			
			System.out.println("Unsolvable square puzzle.");
			System.exit(0);
		}
		search(puzzleBD, heuristic);
	}
	
	static boolean search(puzzle init, puzzleComparator cmp) {
		long startTime = System.currentTimeMillis();

		int maxNodes = 0;
		int totalNodes = 0;
		PriorityQueue<puzzle> nodes = new PriorityQueue<puzzle>(1, cmp);
		boolean ini = checkNode(nodes, init);
		int solutionDepth = 0;
		while (!nodes.isEmpty()) {
			maxNodes = nodes.size() > maxNodes? nodes.size() : maxNodes;
			puzzle currentState = nodes.poll();
			System.out.printf("The best state to expand with a g(n) = %d and "
					+ "h(n) = %d is... %n", currentState.getDepth(), 
					cmp.heuristic(currentState));
			System.out.println(currentState + "Expanding...\n");
			totalNodes++;
			
			boolean next;
			char predecessor = currentState.getpredecessor();
			switch(predecessor) {
			case 'U':
				next = checkNode(nodes, currentState.moveSpaceUp())
						|| checkNode(nodes, currentState.moveSpaceLeft())
						|| checkNode(nodes, currentState.moveSpaceRight());
				break;
			case 'D':
				next = checkNode(nodes, currentState.moveSpaceDown())
						|| checkNode(nodes, currentState.moveSpaceLeft())
						|| checkNode(nodes, currentState.moveSpaceRight());
				break;
			case 'L':
				next = checkNode(nodes, currentState.moveSpaceDown()) 
						|| checkNode(nodes, currentState.moveSpaceUp())
						|| checkNode(nodes, currentState.moveSpaceLeft());
				break;
			case 'R':
				next = checkNode(nodes, currentState.moveSpaceDown()) 
						|| checkNode(nodes, currentState.moveSpaceUp())
						|| checkNode(nodes, currentState.moveSpaceRight());
				break;
			default:
				next = checkNode(nodes, currentState.moveSpaceDown()) 
						|| checkNode(nodes, currentState.moveSpaceUp())
						|| checkNode(nodes, currentState.moveSpaceLeft())
						|| checkNode(nodes, currentState.moveSpaceRight());
			}
			if (next) {
				long endTime = System.currentTimeMillis();
				System.out.printf("To solve this problem the search algorithm "
						+ "expanded a total of %d nodes. %n", totalNodes);
				System.out.printf("The maximum number of nodes in the queue at any "
						+ "one time was %d. %n", maxNodes);
				System.out.printf("The depth of the goal node was %d%n", 
						currentState.getDepth() + 1);
				System.out.println("That took " + (endTime - startTime) + 
						" milliseconds");
				return true;
			}
			solutionDepth = currentState.getDepth();
			
//			System.out.println("In the nodes...");
//			for (puzzle u : nodes) {
//				System.out.println(u.toString());
//			}
//			break;
		}
		
		long endTime = System.currentTimeMillis();
		System.out.printf("Expanded a total of %d nodes. %n", totalNodes);
		System.out.printf("The maximum number of nodes in the queue at any "
				+ "one time was %d. %n", maxNodes);
		System.out.printf("The depth of the output node was %d%n", solutionDepth);
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		
		// Unsolvable
		if (!ini) {
			System.out.println("Unsolvable puzzle !!!");
			return false;
		}
		else {
			return true;
		}
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

interface puzzleComparator extends Comparator<puzzle> {
	int heuristic(puzzle b);
}

class UniformCostComparator implements puzzleComparator {
	@Override
	public int compare(puzzle o1, puzzle o2) {
		return o1.getDepth() - o2.getDepth();
	}

	public int heuristic(puzzle b) {
		return 0;
	}
}

class MisplacedTile implements puzzleComparator {
	@Override
	public int compare(puzzle o1, puzzle o2) {
		return (o1.getDepth() + o1.misplacedTile()) 
				- (o2.getDepth() + o2.misplacedTile());
	}

	public int heuristic(puzzle b) {
		return b.misplacedTile();
	}
}

class ManhattenDistance implements puzzleComparator {
	@Override
	public int compare(puzzle o1, puzzle o2) {
		return (o1.getDepth() + o1.manhattenDistance()) 
				- (o2.getDepth() + o2.manhattenDistance());
	}

	public int heuristic(puzzle b) {
		return b.manhattenDistance();
	}
}
