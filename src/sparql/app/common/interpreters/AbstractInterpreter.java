package sparql.app.common.interpreters;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import sparql.app.common.misc.KnowledgeContainer;

public abstract class AbstractInterpreter implements Interpreter {
	protected KnowledgeContainer knowledgeContainer;

	public AbstractInterpreter(AbstractInterpreter interpreter)
	{
		if (interpreter != null) {
			this.knowledgeContainer = interpreter.getKnowledgeContainer();
		}
	}
	
	public KnowledgeContainer getKnowledgeContainer()
	{
		return this.knowledgeContainer;
	}
	
	public String resolveNodeName(org.apache.jena.graph.Node node)
	{
		if (node.isVariable()) {
			if (node.getName().startsWith("?")) {
				return "_:"+node.getName().replace("?", "");
			} else {
				return "?"+node.getName();
			}
		} else if (node.isURI()) {
			for (Entry<String, String> entry: this.getKnowledgeContainer().getPrefixMap().getNsPrefixMap().entrySet()) {
				if (node.toString().startsWith(entry.getValue())) {
					return node.toString().replace(entry.getValue(), entry.getKey()+":");
				}
			}
			
			return node.getLocalName();
		} else if (node.isLiteral()) {
			return "\""+node.getLiteralLexicalForm()+"\"";
		} else {
			return node.toString();
		}
	}
	
	public String beautifyExpression(String input)
	{
		String result = "";
		int pad = 0;
		boolean inQuotes = false;
		boolean inApostrophe = false;
		boolean nested = true;
		
		input = input.replaceAll("\\s+", " ");
		
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
		
		Map<String, String> map = this.getKnowledgeContainer().getPrefixMap().getNsPrefixMap();
		
		for (Entry<String, String> entry: map.entrySet()) {
			//System.out.println();
			result = result.replace(entry.getValue(), entry.getKey()+":");
		}
		
		result = result.replaceAll("<([^:]+):([^>]+)>", "$1:$2");				
		
		//System.out.println(result);
		return result+"\\l";
	}

	public String getUUID()
	{
		return this.getUUID(this.hashCode());
	}

	public String getUUID(int hashCode)
	{
		try {
			String hash = String.valueOf(hashCode);
			byte[] bytes = hash.getBytes("UTF-8");
			UUID uuid = UUID.nameUUIDFromBytes(bytes);
			String id = uuid.toString().substring(0, 8);
			return id;
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
