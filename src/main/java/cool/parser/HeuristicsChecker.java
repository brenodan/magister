package cool.parser;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class HeuristicsChecker {

	private HeuristicsChecker() {}
	
	/**
	 * This method receives a str and compares it against the keywords of commonly coded credentials
	 * @param str
	 * @return true | false
	 */
	public static boolean heuristicSearchForSecret (String str) {
		
		for (int i = 0; i < Constants.keywords.length; i++) {
			
			if(Constants.keywords[i].length() >= str.length()) {
				
				if(Constants.keywords[i].toLowerCase().contains(str.toLowerCase())){
					return true;	
				}
				
			} else {
				
				if(str.toLowerCase().contains(Constants.keywords[i].toLowerCase())){
					return true;	
				}
			}
		}
		
		return false;		
		
	}
	
	public static HeuristicType generateHeuristicAnalysisReport(String string) {
		
		return generateHeuristicTypeFromString(string);
		
	}
	
	public static HeuristicType generateHeuristicAnalysisResult2(String keyword, String analyzedString) {
		
		if (StringUtils.compareIfOneStringContainsTheOtherCaseInsensitive(keyword, analyzedString)) {
			
			return HeuristicUtils.getHeuristicType(keyword);
			
		}
	
		return HeuristicType.none;
		
	}
	
	public static ArrayList<HeuristicType> generateArrayListHeuristicTypeFromString(String string) {
		
		ArrayList<HeuristicType> returnArray = new ArrayList<HeuristicType>();
		
		for (int i = 0; i < Constants.keywords.length; i++) {
		
			HeuristicType heuristicType =  generateHeuristicAnalysisResult2(Constants.keywords[i], string);
			
			if(heuristicType != HeuristicType.none) {
				returnArray.add(heuristicType);
			}
			
		}
		
		if(returnArray.size() == 0) {
			returnArray.add(HeuristicType.none);
		}
		
		return returnArray;
		
	}
	
	
	public static HeuristicType generateHeuristicTypeFromString(String string) {
		
		for (int i = 0; i < Constants.keywords.length; i++) {
				
			try {
					
				if(StringUtils.compareIfOneStringContainsTheOtherCaseInsensitive(Constants.keywords[i], string)) {
					
					return HeuristicUtils.getHeuristicType(Constants.keywords[i]);
					
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return HeuristicType.none;
		
	}
	
}
