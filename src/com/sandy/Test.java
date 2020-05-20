package com.sandy;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class ProbablityGeneratorImpl implements ProbabilityGenerator {

	private String filename;
	public static int k;

	public static List<Integer> probablityKeys = new ArrayList<Integer>();

	public static List<String> probablityString = new ArrayList<String>();

	ProbablityGeneratorImpl(String filename) {
		this.filename = filename;

	}

	//Function for reading file and Storing it in list
	
	
	
	
	

	//Function for writing the output string in the file
	
	
	
	
	public String getNextString() {

		// Read a String and store it in a list of Strings

		List<String> list = Collections.emptyList();

		try {
			list = Files.readAllLines(Paths
					.get("C:\\END1COB\\ECLIPSE_PHOTON\\PHOTON_WORKSPACE\\AmitSolution\\src\\com\\sandy\\" + filename));
		} catch (IOException e) {

			e.printStackTrace();
		}

	
		List<Double> listofProbab = new ArrayList<Double>();
		for (String x : list) {
			String arr[] = x.split(",");

			probablityString.add(arr[0]);
			listofProbab.add(Double.valueOf(arr[1]));
		}


		for (int i = 0; i < listofProbab.size(); i++) {

			Double keyVal = listofProbab.get(i);
			double key = (keyVal.doubleValue()) * k;
			int newVal = (int) key;
			Integer x = new Integer(newVal);
			probablityKeys.add(x);

		}

		int multipliedKey = probablityKeys.get(0).intValue();

		if (multipliedKey > 0) {
			probablityKeys.set(0, --multipliedKey);
			return probablityString.get(0);
		} else {
			probablityKeys.remove(0);
			probablityString.remove(0);
			return "";
		}

	}
}

public class Test {

	public static void main(String[] args) throws IOException {

		ProbabilityGenerator gen = new ProbablityGeneratorImpl("probablities.txt");

		ProbablityGeneratorImpl.k = 100;

		// Output file name
		String outputFile = "C:\\END1COB\\ECLIPSE_PHOTON\\PHOTON_WORKSPACE\\AmitSolution\\src\\com\\sandy\\outputprobab.txt";

		List<String> batchList=new ArrayList<String>();
		String nextStr=null;
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(outputFile, true);

			for (int i = 0; i < 100; i++) {
				 nextStr = gen.getNextString();

				if (nextStr == "") {
					i--;

				} else {
					fw.write(nextStr);
					fw.write("\n");
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		fw.close();

	}

}
