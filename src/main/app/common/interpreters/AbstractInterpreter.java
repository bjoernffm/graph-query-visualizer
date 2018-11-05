package main.app.common.interpreters;

import main.app.dot.objects.EntityNode;

public abstract class AbstractInterpreter implements Interpreter {
	protected boolean optional = false;
	
	public Interpreter setOptional(boolean optional)
	{
		this.optional = optional;
		return this;
	}
	
	public Interpreter setOptional()
	{
		return this.setOptional(true);
	}
	
	public boolean getOptional()
	{
		return this.optional;
	}
	
	public String resolveNodeName(org.apache.jena.graph.Node node)
	{
		if (node.isVariable()) {
			return "?"+node.getName();
		} else if (node.isURI()) {
			return node.getLocalName();
		} else if (node.isLiteral()) {
			return node.getLiteralLexicalForm();
		} else {
			return node.toString();
		}
	}
}
