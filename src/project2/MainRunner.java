package project2;

import java.io.*;
import java.util.*;

public class MainRunner {
	static final String FILE_NAME = "cs_205_large33.txt";
	
	public static void main(String[] args){

		Tester mainTester = new Tester();

		try {
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

