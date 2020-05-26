package com.amit;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class ProbablityGeneratorImpl implements ProbabilityGenerator {

	private String filename;
	public static int k;

	public static List<Integer> probablityKeys = new ArrayList<Integer>();

	public static List<String> probablityString = new ArrayList<String>();

	public static List<String> list = Collections.emptyList();

	public static List<String> batchArray = new ArrayList<String>();
	
	public static List<Double> listofProbab = new ArrayList<Double>();

	// output file name

	public static String fileLocation = System.getProperty("user.dir");
	public static String outputfile = fileLocation + "\\src\\com\\amit\\outputprobab.txt";

	ProbablityGeneratorImpl(String filename) {
		this.filename = filename;

	}

	// Function for reading file and Storing it in list

	public void readFile(int k) {
		
		System.out.println("====================================================================");
		

		try {
			list = Files.readAllLines(Paths.get(fileLocation + "\\src\\com\\amit\\" + filename));
			
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		//After reading sort the data and store the string in one List and key in another list
		
		for (String x : list) {
			String arr[] = x.split(",");

			// String
			probablityString.add(arr[0]); 
			//Probablity
			listofProbab.add(Double.valueOf(arr[1]));
		}
		
		System.out.println("printing list of strings and list of probablity");
		
		probablityString.stream().forEach(e->System.out.println(e));
		
		System.out.println("printing list of Probablity Keys in Double ");
		
		listofProbab.stream().forEach(e->System.out.println(e));
		
		
		// ProbablityKeys after multiplied by the value
		
		
		   probablityKeys =listofProbab.stream()
    				.map(x -> x*k)
    				.map(x->x.intValue())
    				.collect(Collectors.toList());


	// Below list if listofProbab with multiplied by k and store in int value
	probablityKeys.stream().forEach(e->System.out.println(e));



	}

	public String getNextString() {
	       
		int pCurrentKey = probablityKeys.get(0);

		if (pCurrentKey > 0) {
			
			probablityKeys.set(0, --pCurrentKey);
			return probablityString.get(0);
		} else {
			probablityKeys.remove(0);
			probablityString.remove(0);
			return "";
		}

	}

	// function for writing the string in the file

	// Batching is done using static batchArray

	public static void writeToOutputFile(String str) {

		FileWriter fw = null;
		try {
			fw = new FileWriter(outputfile, true);

			batchArray.add(str);
			batchArray.add("\n");

			if (batchArray.size() == 50) {

				for (String batchedString : batchArray) {

					fw.write(batchedString);
				}

				batchArray.clear();

			}

		} catch (IOException e) {
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

	public static void main(String[] args) throws IOException {

		System.out.println("==========================================================");
		System.out.println();
		System.out.println("Sending the file name to the implementation Class ");
		ProbablityGeneratorImpl gen = new ProbablityGeneratorImpl("probablities.txt");
		
		System.out.println("Enter the number of iteration:  ");
		Scanner scan=new Scanner(System.in);
		int numberOfIteration=scan.nextInt();
				
		System.out.println("number of iteration is "+ numberOfIteration );		
		
		System.out.println("Reading the file and storing the string in one list... ");
		gen.readFile(numberOfIteration);
		
		System.out.println("Reading finished and results stored in list ");

		// K is the number of iteration this we can take from user
		
		
				
		ProbablityGeneratorImpl.k = numberOfIteration;

		
		
		String nextStr = null;

		for (int i = 0; i < numberOfIteration; i++) {
			
			// Calling the function and getting the String 
			nextStr = gen.getNextString();

			if (nextStr == "") {
				i--;

			} else {

				// Writing the output received in the outputFile
				ProbablityGeneratorImpl.writeToOutputFile(nextStr);
			}
		}
		
		System.out.println(" !!! Finished Writing in the file outoutptobab ...");
	}

}
