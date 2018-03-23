package project2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MainRunner {
	// 205_proj2_data/CS205_BIGtestdata__20.txt
	static String FILE_NAME = "205_proj2_data/CS205_SMALLtestdata__41.txt";
	
	public static void main(String[] args){

		Tester mainTester = new Tester();

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
				DataPoint data = new DataPoint(type, attributes);
				mainTester.add(data);
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

		startTime = System.currentTimeMillis();
		mainTester.testAccurancy(mainTester.alwaysRemoveOne());
		endTime = System.currentTimeMillis();
		System.out.println("RemoveOne = " + (endTime - startTime) + " milliseconds\n");
		
		mainTester.resample(3);
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Input the number of brute force iteration.");
		int n = keyboard.nextInt();
		mainTester.callBruteForce(n);
		System.out.println();
		keyboard.close();
	}
}

