package project2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MainRunner {
	// CS205_BIGtestdata__20.txt
	static String FILE_NAME = "205_proj2_data/CS205_SMALLtestdata__41.txt";
	
	public static void main(String[] args){

		Tester mainTester = new Tester();
		Scanner keyboard = new Scanner(System.in);

		try {
			switch (args.length) {
			case 0:
				break;
			case 1:
				FILE_NAME = args[0];
				break;
			default:
				System.out.println("\n    Invalid input parameters!"
						+ "\n    Usage: java -jar nn.jar         "
						+ "# FILE_NAME = 205_proj2_data/CS205_BIGtestdata__20.txt"
						+ "\n    Usage: java -jar nn.jar <s>     # data = s\n");
				System.exit(1);
			}

			Scanner sc = new Scanner(new File(FILE_NAME));
			while (sc.hasNextLine()) {
				Scanner lineSc = new Scanner(sc.nextLine());
				int type = (int) lineSc.nextDouble();
				List<Double> attributes = new ArrayList<Double>();
				while (lineSc.hasNextDouble()) {
					attributes.add(lineSc.nextDouble());
				}
				lineSc.close();
				mainTester.add(new DataPoint(type, attributes));
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainTester.normalize();
		
		long startTime = System.currentTimeMillis();
		mainTester.testAccurancy(mainTester.forwardSearchBest());
		long endTime = System.currentTimeMillis();
		System.out.println("forwardSearch =  " + (endTime - startTime) + " milliseconds\n");
		
		startTime = System.currentTimeMillis();
		mainTester.testAccurancy(mainTester.backwardSearchBest());
		endTime = System.currentTimeMillis();
		System.out.println("backwardSearch = " + (endTime - startTime) + " milliseconds\n");

		int DIVISIONS;
		double PERCENTAGE;
		try {
			System.out.println("Input the DIVISIONS. Default: 40");
			DIVISIONS = keyboard.nextInt();
			System.out.println("Input the PERCENTAGE. Default: 0.7");
			PERCENTAGE = keyboard.nextDouble();
		} catch (NumberFormatException ne) {
			System.out.println("Input format are wrong. Use the default one.\n "
					+ "DIVISIONS = 40. PERCENTAGE = 0.7");
			DIVISIONS = 40;
			PERCENTAGE = 0.7;
		}
		
		startTime = System.currentTimeMillis();
		mainTester.testAccurancy(mainTester.forwardConsensusBest(DIVISIONS, PERCENTAGE));
		endTime = System.currentTimeMillis();
		keyboard.close();
		System.out.println("Consensus = " + (endTime - startTime) + " milliseconds\n");			
	}
}

