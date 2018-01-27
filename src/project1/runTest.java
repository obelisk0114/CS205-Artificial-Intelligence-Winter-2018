package project1;

public class runTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		puzzleGenerator puzzle = new puzzleGenerator(8);
		int steps = puzzle.stepGenerate();
		puzzle.generate(steps);

	}

}
