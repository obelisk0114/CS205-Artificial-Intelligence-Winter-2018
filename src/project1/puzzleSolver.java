package project1;

import java.util.Scanner;

public class puzzleSolver {

	public static void main(String[] args) {
		/**     For a puzzle generator
		//puzzleGenerator puzzle = new puzzleGenerator(7, 3);
		puzzleGenerator puzzle = new puzzleGenerator(8);
		puzzle.generate();
		*/
		
		puzzle solver;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("This is a n-puzzle solver.\n"
				+ "Choose 1 for a square puzzle.\n"
				+ "Choose 2 for a rectangular one.\nChoose 0 for a default one.");
		int xLength = -1;
		int yLength = -1;
		int userChoice = keyboard.nextInt();
		switch(userChoice){
		case 0:
			solver = new puzzle();
			break;
		case 1:
			System.out.println("Enter the width.");
			xLength = keyboard.nextInt();
			yLength = xLength;
			int total = xLength * xLength - 1;
			solver = new puzzle(total);
			solver.setBoard(inputPuzzle(keyboard, xLength, yLength));
			break;
		case 2:
			System.out.println("Enter the length (x).");
			xLength = keyboard.nextInt();
			System.out.println("Enter the width (y).");
			yLength = keyboard.nextInt();
			solver = new puzzle(xLength, yLength);
			solver.setBoard(inputPuzzle(keyboard, xLength, yLength));
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
		System.out.println(solver.checkSquareSolvability());
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

}
