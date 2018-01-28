package project1;

import java.util.List;
import java.util.ArrayList;

public class puzzleGenerator {
	private String[][] table;
	private int[] space = new int[2];      // the position of "*"
	private int diameter = 80;

	public puzzleGenerator(int n) {
		int length = (int) Math.sqrt(n + 1);
		if (length * length != (n + 1)) {
			System.out.println("Error puzzle.");
			System.exit(1);
		}
		setPuzzleGenerator(length, length);
	}
	
	public puzzleGenerator(int x, int y) {
		setPuzzleGenerator(x, y);
	}
	
	public void setPuzzleGenerator(int x, int y) {
		table = new String[y][x];
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				table[i][j] = Integer.toString(i * x + j + 1);
				if (i == (y - 1) && j == (x - 1)) {
					table[i][j] = "*";
				}
			}
		}

		space[0] = x - 1;
		space[1] = y - 1;
	}

	void generate() {
		int step = stepGenerate();
		for (int i = 0; i < step; i++) {
			move(space[0], space[1]);
		}
		printTable();
	}

	void move(int x1, int y1) {
		int yLength = table.length;
		int xLength = table[0].length;
		boolean leftMove = !(x1 == 0);
		boolean rightMove = !(x1 == (xLength - 1));
		boolean upMove = !(y1 == 0);
		boolean downMove = !(y1 == (yLength - 1));

		List<String> canMove = new ArrayList<String>(4);
		if (leftMove)
			canMove.add("left");
		if (rightMove)
			canMove.add("right");
		if (upMove)
			canMove.add("up");
		if (downMove)
			canMove.add("down");

		int choose = (int) (Math.random() * canMove.size());
		String movement = canMove.get(choose);
		if (movement.equals("left")) {
			swap(space[0], space[1], (space[0] - 1), space[1]);
			space[0] -= 1;
		}
		else if (movement.equals("right")) {
			swap(space[0], space[1], (space[0] + 1), space[1]);
			space[0] += 1;
		}
		else if (movement.equals("up")) {
			swap(space[0], space[1], space[0], (space[1] - 1));
			space[1] -= 1;
		}
		else {
			swap(space[0], space[1], space[0], (space[1] + 1));
			space[1] += 1;
		}
	}

	int stepGenerate() {
		int step = (int) (Math.random() * diameter) + 1;
		return step;
	}

	void setDiameter(int d) {
		diameter = d;
	}

	void printTable() {
		int y = table.length;
		int x = table[0].length;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	String[][] getTable() {
		return table;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int y = table.length;
		int x = table[0].length;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				sb.append(table[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();		
	}

	void swap(int x1, int y1, int x2, int y2) {
		String tmp = table[y1][x1];
		table[y1][x1] = table[y2][x2];
		table[y2][x2] = tmp;
	}

}
