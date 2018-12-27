package main.app.common.interpreters;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprAggregator;
import org.apache.jena.sparql.expr.ExprFunction;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.aggregate.Aggregator;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Subgraph;
import main.app.misc.FunctionResolution;

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
			return "?"+node.getName();
		} else if (node.isURI()) {
			Object prefix = this.getKnowledgeContainer().getPrefixMap().getNsURIPrefix(node.getNameSpace());
			if (prefix == null) {
				return node.getLocalName();
			} else {
				return prefix+":"+node.getLocalName();
			}
		} else if (node.isLiteral()) {
			return node.getLiteralLexicalForm();
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
