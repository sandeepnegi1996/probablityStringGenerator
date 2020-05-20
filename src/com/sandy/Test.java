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

	public static List<String> list = Collections.emptyList();
	
	public static List<String> batchArray=new ArrayList<String>();
	
	// output file name
	
	public static String fileLocation=System.getProperty("user.dir");
	public static String outputfile=fileLocation+"\\src\\com\\sandy\\outputprobab.txt";

	

	ProbablityGeneratorImpl(String filename) {
		this.filename = filename;

	}

	// Function for reading file and Storing it in list

	public void readFile() {

		try {
			list = Files.readAllLines(Paths
					.get(fileLocation+"\\src\\com\\sandy\\" + filename));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String getNextString() {

		// below function will read the file and store the result in the list
		readFile();

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
	

	

	// function for writing the string in the file
	
	//Batching is done using static batchArray

	public static void writeToOutputFile(String str) {

		FileWriter fw = null;
		try {
			fw = new FileWriter(outputfile, true);
			
			batchArray.add(str);
			batchArray.add("\n");
			
			if(batchArray.size()==50) {
				
				for(String batchedString:batchArray) {
					
					fw.write(batchedString);
				}
				
				batchArray.clear();
				
			}
			

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	

}

public class Test {

/*	// output file name

	String fileLocation=System.getProperty("user.dir");
	
	String outputfile2=fileLocation+"\\src\\com\\sandy\\outputprobab.txt";
	
//	public static String outputFile = "C:\\END1COB\\ECLIPSE_PHOTON\\PHOTON_WORKSPACE\\AmitSolution\\src\\com\\sandy\\outputprobab.txt";
*/	

	public static void main(String[] args) throws IOException {

		
		ProbablityGeneratorImpl gen = new ProbablityGeneratorImpl("probablities.txt");

		ProbablityGeneratorImpl.k = 100;

		String nextStr = null;

		
		for (int i = 0; i < 100; i++) {
			nextStr = gen.getNextString();

			if (nextStr == "") {
				i--;

			} else {

				ProbablityGeneratorImpl.writeToOutputFile(nextStr);
			}
		}
	}

}
