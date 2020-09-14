package cool.parser;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegexChecker {

	private RegexChecker() {}
	
		
	
	/**
	 * This method receives a str and compares it against the regex that commonly hardcoded-secrets
	 * @param str
	 * @return true | false
	 */
	public static boolean regexSearchForSecret (String str) {
		
		for (int i = 0; i < Constants.regexList.length; i++) {
			
			if(Pattern.matches(Constants.regexList[i], str)) {
				return true;	
			}
		}
		
		return false;		
		
	}
	
	public static ArrayList<RegexType> generateArrayListRegexTypeFromString(String string) {
		
		ArrayList<RegexType> returnArray = new ArrayList<RegexType>();
		
		for (int i = 0; i < Constants.regexList.length; i++) {
		
			RegexType regexType = generateRegexAnalysisResult2(Constants.regexList[i], string);
			
			if(regexType != RegexType.none) {
			
				returnArray.add(regexType);
			
			}
		
		}
		
		if(returnArray.size() == 0) {
			returnArray.add(RegexType.none);
		}
		
		return returnArray;
		
	}
	
	
	public static RegexType generateRegexTypeFromString(String string) {
		
		
		for (int i = 0; i < Constants.regexList.length; i++) {
			
			try {
				
				RegexType regexType = generateRegexAnalysisResult2(Constants.regexList[i], string);
				if(regexType != RegexType.none) {
					return regexType;
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}			
		}
		
		return RegexType.none;
		
	}
	
	public static RegexType generateRegexAnalysisResult2(String regex, String analyzedString) {
		
		if(Pattern.matches(regex, analyzedString)) {
			
			try {
				return RegexUtils.getRegexType(regex);	
			} catch (Exception e) {
				e.printStackTrace();//test comment
			}
				
		}
		
		return RegexType.none;
	}
}
