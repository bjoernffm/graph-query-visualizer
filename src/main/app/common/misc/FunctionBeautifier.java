package main.app.common.misc;

public class FunctionBeautifier {

	static public String beautify(String input)
	{
		String result = "";
		int pad = 0;
		boolean inQuotes = false;
		boolean inApostrophe = false;
		boolean nested = true;
		
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == '(' && inQuotes == false && inApostrophe == false) {
				if (input.charAt(i-1) != ' ') {
					result += ' ';	
				}
				
				for(int j = i+1; j < input.length(); j++) {
					if (input.charAt(j) == ')') {
						nested = false;
						break;
					} else if (input.charAt(j) == '(') {
						nested = true;
						break;
					}
				}
				
				if (nested == false) {
					result += input.charAt(i);
				} else {
					pad += 3;
					result += input.charAt(i)+"\\l";
					if (pad > 0) {
						result += String.format("%"+pad+"s", "");
					}
				}
			} else if(input.charAt(i) == ')' && inQuotes == false && inApostrophe == false) {
				if (nested == false) {
					result += input.charAt(i);
					nested = true;
				} else {
					pad -= 3;
					result += "\\l";
					if (pad > 0) {
						result += String.format("%"+pad+"s", "");
					}
					result += input.charAt(i);
				}
			} else if(input.charAt(i) == '"' && inApostrophe == false) {
				if (input.charAt(i-1) != '\\') {
					inQuotes = !inQuotes;
				}
				result += input.charAt(i);
			} else if(input.charAt(i) == '\'' && inQuotes == false) {
				if (input.charAt(i-1) != '\\') {
					inApostrophe = !inApostrophe;
				}
				result += input.charAt(i);
			} else if(input.charAt(i) == ',' && input.charAt(i+1) != ' ' && inQuotes == false) {
				result += input.charAt(i)+" ";
			} else {
				result += input.charAt(i);
			}
		}
		
		//System.out.println(result);
		return result+"\\l";
	}
}
