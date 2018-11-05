package main.app.common.interpreters;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprAggregator;
import org.apache.jena.sparql.expr.ExprFunction;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.aggregate.Aggregator;

import main.app.misc.FunctionResolution;

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
	
	public FunctionResolution resolveFunctionName(Expr expression) throws Exception
	{
		FunctionResolution fr = new FunctionResolution();
		String retValue = "";
		
		if(expression.isFunction()) {
			ExprFunction function = expression.getFunction();
			
			List<Expr> args = function.getArgs();
			if (args.size() == 2) {
				FunctionResolution resolution1 = this.resolveFunctionName(args.get(0));
				FunctionResolution resolution2 = this.resolveFunctionName(args.get(1));
				retValue += resolution1.getName()+" "+function.getOpName()+" "+resolution2.getName();
				
				fr.synchronizeMentionedVars(resolution1);
				fr.synchronizeMentionedVars(resolution2);
			} else {
				throw new Exception("more than two args!");
			}
		} else if (expression.isConstant()) {
			NodeValue constant = expression.getConstant();
			retValue = constant.toString();
		} else if (expression.isVariable()) {
			retValue = expression.getVarName();
		} else {
			if (expression instanceof ExprAggregator) {
				ExprAggregator expressionAggregator = (ExprAggregator) expression;
				Aggregator aggregator = expressionAggregator.getAggregator();
				retValue = aggregator.toString();

				ExprList expressionList = aggregator.getExprList();
				for(int i = 0; i < expressionList.size(); i++) {
					Expr expressionItem = expressionList.get(i);
					if (expressionItem.isVariable()) {
						fr.addMentionedVar(expressionItem.getExprVar());
					}
				}
			} else {
				throw new Exception("Something else: "+expression.getClass());
			}
		}
		
		fr.setName(retValue);
		return fr;
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
