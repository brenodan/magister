package cool.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {

	
	public static ArrayList<String> readFile(String filePath) {
		
		BufferedReader reader;
		ArrayList<String> returnArray = new ArrayList<String>();
		
		try {
		
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				
				System.out.println("in file: " + line);
				line = reader.readLine();
				returnArray.add(line);
			}
			
			reader.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
		
			return returnArray;
		
		}
		
	}
	
	public static ArrayList<DatasetElement> parseFile(ArrayList<String> fileContents) {
		
		ArrayList<DatasetElement> elements = new ArrayList<DatasetElement>();
		
		
		for (String line: fileContents) {
			
			if(line != null) {
				
				if(!line.isEmpty()) {
				
					Boolean label = false;
					String tempString = line;
					
					try {
					
						tempString = line.substring(line.indexOf(":*") + 2);
					
					} catch (Exception e) {
						
					}
					
					if(tempString.contains("__true__")) {
						
						tempString = tempString.replace("__true__", "");
						label = true;
					} else {
						
						tempString = tempString.replace("__false__", "");
										
					}
					
					String[] arr = tempString.split(", ");
					for (String elem: arr) {
						
						elem = elem.replace(" ", "");
						System.out.println(elem);
						elements.add(new DatasetElement(elem, ShannonEntropy.getShannonEntropy(elem), label, HeuristicsChecker.generateArrayListHeuristicTypeFromString(elem), RegexChecker.generateArrayListRegexTypeFromString(elem)));
					}
				}
			}
		}
		
		
		return elements;
		
	}
	
	public static ArrayList<DatasetElement> parseUnclusteredFile(ArrayList<String> fileContents) {
		
		ArrayList<DatasetElement> elements = new ArrayList<DatasetElement>();
		
		for (String line: fileContents) {
			
			if(line != null) {
				
				if(!line.isEmpty()) {
				
					String elem = line;
					
					elem = line.replace("\"", "");
					elements.add(new DatasetElement(elem, ShannonEntropy.getShannonEntropy(elem), false, HeuristicsChecker.generateArrayListHeuristicTypeFromString(elem), RegexChecker.generateArrayListRegexTypeFromString(elem)));
				
				}
			}
		}
		
		
		return elements;
		
	}
	
	public static void writeFile(String filePath, ArrayList<DatasetElement> elements) {
		
		File fout = new File(filePath);
		FileOutputStream fos;
		BufferedWriter bw; 
		
		try {
			
			fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for (DatasetElement elem: elements) {
				
				bw.write(elem.toString());
				//bw.write(elem.toString2());
				bw.newLine();
				
			}
			
			bw.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void main(String[] args) {
		
		String filePath = "/Users/brenodcruz/eclipse-workspace/cool.parser/output/big-file.txt";
		//String filePath = "/Users/brenodcruz/eclipse-workspace/cool.parser/output/strings_for_clustering_ascII.txt";
		ArrayList<String> dataContents = readFile(filePath);
		ArrayList<DatasetElement> elements = parseFile(dataContents);
		//ArrayList<DatasetElement> elements = parseUnclusteredFile(dataContents);
		for (DatasetElement elem: elements) {
			System.out.println(elem.toString());
		}
		
		String outputPath = "/Users/brenodcruz/eclipse-workspace/cool.parser/output/output-file.txt";
		//String outputPath = "/Users/brenodcruz/eclipse-workspace/cool.parser/output/output-unclustered-file.txt";
		writeFile(outputPath, elements);

		
		
		
	}
	
}
