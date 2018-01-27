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

		table = new String[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				table[i][j] = Integer.toString(i * length + j + 1);
				if (i == (length - 1) && j == (length - 1)) {
					table[i][j] = "*";
				}
			}
		}

		space[0] = length - 1;      // x
		space[1] = length - 1;      // y
	}

	void generate(int n) {
		int step = stepGenerate();
		for (int i = 0; i < step; i++) {
			move(space[0], space[1]);
		}
		printTable();
	}

	void move(int x1, int y1) {
		int n = table.length;
		boolean leftMove = !(x1 == 0);
		boolean rightMove = !(x1 == (n - 1));
		boolean upMove = !(y1 == 0);
		boolean downMove = !(y1 == (n - 1));

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
		int n = table.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}

	void swap(int x1, int y1, int x2, int y2) {
		String tmp = table[y1][x1];
		table[y1][x1] = table[y2][x2];
		table[y2][x2] = tmp;
	}

}
