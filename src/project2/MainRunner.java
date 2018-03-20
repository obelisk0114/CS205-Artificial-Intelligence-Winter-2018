package project2;

import java.io.*;
import java.util.*;

public class MainRunner {
	// CS205_SMALLtestdata__41.txt
	static String FILE_NAME = "205_proj2_data/CS205_BIGtestdata__20.txt";
	
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
			while(sc.hasNextLine()){
				Scanner lineSc = new Scanner(sc.nextLine());
				int type = (int) lineSc.nextDouble();
				List<Double> attributes = new ArrayList<Double>();
				while(lineSc.hasNextDouble()){
					attributes.add(lineSc.nextDouble());
				}
				lineSc.close(); 
				mainTester.add(new DataPoint(type,attributes));
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainTester.normalize();
		mainTester.testAccurancy(mainTester.forwardSearchBest());
		System.out.println();
		mainTester.testAccurancy(mainTester.backwardSearchBest());
		System.out.println();
		mainTester.testAccurancy(mainTester.forwardConsensusBest());
	}
}

