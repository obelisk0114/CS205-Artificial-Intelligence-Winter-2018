package project2;

import java.util.Arrays;

import java.util.List;
import java.util.ArrayList;

public class Tester {
	List<DataPoint> data;
	
	public Tester(){
		data = new ArrayList<DataPoint>();
	}
	
	public void add(DataPoint d){
		data.add(d);
	}
	
	public void normalize(){
		for (int i = 0; i < data.get(0).getAttributeDimension(); i++) {
			double total = 0;
			double average = 0;
			double squaredDiff = 0;
			double stdev = 0;
			for (DataPoint dp : data) {
				double dpValue = dp.getAttribute(i);
				total += dpValue;
			}
			average = total / data.size();
			for (DataPoint dp : data) {
				double dpValue = dp.getAttribute(i);
				squaredDiff += (dpValue - average) * (dpValue - average);
			}
			stdev = Math.sqrt(squaredDiff / data.size());
			for (DataPoint dp : data) {
				dp.setAttribute(i, (dp.getAttribute(i) - average) / (stdev));
			}
		}
	}
	
	public List<Integer> forwardSearchBest(){
		boolean[] attributeUseTable = new boolean[data.get(0).getAttributeDimension()];
		List<Integer> pickedAttributes = new ArrayList<Integer>();
		for (int j = 0; j < data.get(0).getAttributeDimension(); j++) {
			double bestAccurancyThisTurn = 0;
			int bestAttributeToAdd = 0;
			for (int i = 0; i < attributeUseTable.length; i++) {
				List<Integer> tempPickedAttributes = new ArrayList<Integer>(pickedAttributes);
				if(attributeUseTable[i]){
					continue;
				}
				
				tempPickedAttributes.add(i);
				double result = testAccurancy(tempPickedAttributes);
				//System.out.println("        Using feature(s) " + listToString(tempPickedAttributes) + "accuracy is " + result);
				
				if(result > bestAccurancyThisTurn){
					bestAccurancyThisTurn = result;
					bestAttributeToAdd = i;
				}
			}
			
			pickedAttributes.add(bestAttributeToAdd);
			attributeUseTable[bestAttributeToAdd] = true;
			System.out.println("Feature set " + listToString(pickedAttributes) + " was best, accuracy is " + bestAccurancyThisTurn);
		}
		return pickedAttributes;
	}
	
	public void resample(int n) {
		List<List<DataPoint>> sample = new ArrayList<List<DataPoint>>();
		for (int j = 0; j < n; j++) {
			int removeNumber = data.size() * 5 / 100;
			int[] removeElement = new int[removeNumber];
			
			int[] pool = new int[data.size()];
			for (int i = 0; i < pool.length; i++) {
				pool[i] = i;
			}
			// Fisher-Yates shuffle
			for (int i = 0; i < pool.length; i++) {
				int r = i + (int) (Math.random() * (pool.length - i));   // between i and n-1
				// exchange
				int tmp = pool[i];
				pool[i] = pool[r];
				pool[r] = tmp;
			}
			
			for (int i = 0; i < removeNumber; i++) {
				removeElement[i] = pool[i];
			}
			Arrays.sort(removeElement);
			
			List<DataPoint> sampleData = new ArrayList<DataPoint>();
			int counter = 0;
			for (int i = 0; i < data.size(); i++) {
				if (counter < removeElement.length && i == removeElement[counter]) {
					counter++;
					continue;
				}
				sampleData.add(data.get(i));
			}
			
			sample.add(sampleData);
		}
		forwardSearchSample(sample, n);
	}
	
	public void forwardSearchSample(List<List<DataPoint>> sampleData, int n){
		for (int k = 0; k < n; k++) {
			boolean[] attributeUseTable = new boolean[sampleData.get(0).get(0).getAttributeDimension()];
			List<Integer> pickedAttributes = new ArrayList<Integer>();
			for (int j = 0; j < sampleData.get(k).get(0).getAttributeDimension(); j++) {
				double bestAccurancyThisTurn = 0;
				int bestAttributeToAdd = 0;
				for (int i = 0; i < attributeUseTable.length; i++) {
					List<Integer> tempPickedAttributes = new ArrayList<Integer>(pickedAttributes);
					if(attributeUseTable[i]){
						continue;
					}
					
					tempPickedAttributes.add(i);
					double result = testAccurancy(tempPickedAttributes, sampleData.get(k));
					//System.out.println("        Using feature(s) " + listToString(tempPickedAttributes) + "accuracy is " + result);
					
					if(result > bestAccurancyThisTurn){
						bestAccurancyThisTurn = result;
						bestAttributeToAdd = i;
					}
				}
				
				pickedAttributes.add(bestAttributeToAdd);
				attributeUseTable[bestAttributeToAdd] = true;
				System.out.println("Feature set " + listToString(pickedAttributes) + " was best, accuracy is " + bestAccurancyThisTurn);
			}
		}
	}
	
	public List<Integer> alwaysRemoveOne(){
		boolean[] attributeUseTable = new boolean[data.get(0).getAttributeDimension()];
		List<Integer> pickedAttributes = new ArrayList<Integer>();
		for (int j = 0; j < data.get(0).getAttributeDimension(); j++) {
			double bestAccurancyThisTurn = 0;
			int bestAttributeToAdd = 0;
			for (int i = 0; i < data.get(0).getAttributeDimension(); i++) {
				if (attributeUseTable[i]) {
					continue;
				}
				
				List<Integer> turnAttributes = new ArrayList<Integer>();
				turnAttributes.add(i);
				double result = testAccurancy(turnAttributes);
				//System.out.println("        Using feature(s) " + listToString(tempPickedAttributes) + "accuracy is " + result);
				
				if(result > bestAccurancyThisTurn){
					bestAccurancyThisTurn = result;
					bestAttributeToAdd = i;
				}
			}
			
			pickedAttributes.add(bestAttributeToAdd);
			attributeUseTable[bestAttributeToAdd] = true;
			System.out.println("Feature set " + listToString(pickedAttributes) + " was best, accuracy is " + bestAccurancyThisTurn);
		}
		return pickedAttributes;
	}
	
	public List<Integer> backwardSearchBest(){
		boolean[] attributeUseTable = new boolean[data.get(0).getAttributeDimension()];
		List<Integer> pickedAttributes = new ArrayList<Integer>();
		for(int i =0; i < data.get(0).getAttributeDimension(); ++i){
			pickedAttributes.add(i);
		}
		for(int j=0;j<data.get(0).getAttributeDimension();++j){
			double bestAccurancyThisTurn = 0;
			int bestAttributeToAdd = 0;
			for(int i=0;i<attributeUseTable.length;++i){
				List<Integer> tempPickedAttributes = new ArrayList<Integer>(pickedAttributes);
				if(attributeUseTable[i]){
					continue;
				}
				tempPickedAttributes.remove(new Integer(i));
				double result = testAccurancy(tempPickedAttributes);
				System.out.println("        Using feature(s) " + listToString(tempPickedAttributes) + "accuracy is " + result);
				if(result > bestAccurancyThisTurn){
					bestAccurancyThisTurn = result;
					bestAttributeToAdd = i;
				}
			}
			pickedAttributes.remove(new Integer(bestAttributeToAdd));
			attributeUseTable[bestAttributeToAdd] = true;
			System.out.println("Feature set " + listToString(pickedAttributes) + " was best, accuracy is " + bestAccurancyThisTurn);
		}
		return pickedAttributes;
	}
	
	public void callBruteForce(int length) {
		String[] res = {"-1.0", ""};
		List<Integer> pickedAttributes = new ArrayList<Integer>(); 
		bruteForce(0, length, pickedAttributes, res);
		System.out.println("Feature set " + res[1] + " was best, accuracy is " + res[0]);
	}
	
	private void bruteForce(int index, int length, List<Integer> attrIndex, String[] res) {
		if (length == 0) {
			double result = testAccurancy(attrIndex);
			if (result > Double.parseDouble(res[0])) {
				res[0] = String.valueOf(result);
				res[1] = listToString(attrIndex);
			}
			return;
		}
		
		for (int i = index; i < data.get(0).getAttributeDimension(); i++) {
			attrIndex.add(i);
			bruteForce(index + 1, length - 1, attrIndex, res);
			attrIndex.remove(attrIndex.size() - 1);
		}
	}
	
	public double testAccurancy(List<Integer> attrIndex, List<DataPoint> sampleData) {
		int numOfCorrectPredictions = 0;
		for (int i = 0; i < sampleData.size(); i++) {
			if (test(i, attrIndex)) {
				numOfCorrectPredictions++;
			}
		}
		return ((double) numOfCorrectPredictions) / sampleData.size();
	}
	
	public double testAccurancy(List<Integer> attrIndex) {
		return testAccurancy(attrIndex, data);
	}
	
	public boolean test(int index, List<Integer> attrIndex, List<DataPoint> sampleData) {
		DataPoint testerPoint = sampleData.get(index);
		double minDistanceSquared = Double.MAX_VALUE;
		int predictedType = 0;
		for (int i = 0; i < sampleData.size(); i++) {
			if(i == index){
				continue;
			}
			double curDistanceSquared = 0;
			DataPoint candidatePoint = sampleData.get(i);
			for(Integer j : attrIndex){
				curDistanceSquared += (candidatePoint.getAttribute(j) - testerPoint.getAttribute(j)) * (candidatePoint.getAttribute(j) - testerPoint.getAttribute(j));
			}
			if(curDistanceSquared < minDistanceSquared){
				minDistanceSquared = curDistanceSquared;
				predictedType = candidatePoint.getType();
			}
		}
		return testerPoint.getType() == predictedType;
	}

	public boolean test(int index, List<Integer> attrIndex) {
		return test(index, attrIndex, data);
	}
	
	@Override
	public String toString() {
		String str = "Size of table: " + data.size() + "\n";
		for (DataPoint d : data) {
			str += d + "\n";
		}
		return str;
	}
	
	// HELPER FUNCTIONS
	private String listToString(List<Integer> l) {
		String str = "[";
		for (int i = 0; i < l.size(); i++) {
			str += l.get(i) + 1 + ",";
		}
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		str += "]";
		return str;
	}

}
