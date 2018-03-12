package project2;

import java.util.*;

public class DataPoint {
	private int type;
	private List<Double> attributes;
	
	public DataPoint(int type, List<Double> attributes){
		this.type = type;
		this.attributes = new ArrayList<Double> (attributes);
	}
	
	public int getAttributeDimension(){
		return attributes.size();
	}
	
	public void setAttribute(int index, double value){
		attributes.set(index, value);
	}
	
	public Double getAttribute(int index){
		return attributes.get(index);
	}
	
	public int getType(){
		return type;
	}

	@Override
	public String toString() {
		String str = "DataPoint [" + type;
		for(Double d : attributes){
			str += (" " + d);
		}
		str += "]";
		return str;
	}

}

