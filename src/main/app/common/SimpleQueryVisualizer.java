package main.app.common;

import org.apache.jena.sparql.core.PathBlock;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprFunction;
import org.apache.jena.sparql.expr.ExprVar;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementUnion;
import org.apache.jena.sparql.syntax.ElementFilter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final public class SimpleQueryVisualizer extends QueryVisualizer implements QueryVisualizerInterface {
	
	public String visualize(String string)
	{
		ElementGroup elGroup = this.getElementGroupByQuery(string);

		List<Element> list = elGroup.getElements();
		
		return iterate(list);
	}
	
	protected String iterate(List<Element> list)
	{
		for(int i = 0; i < list.size(); i++) {
			String str = "";
			Element el = list.get(i);
			
			if (el instanceof org.apache.jena.sparql.syntax.ElementPathBlock) {
				this.visualizeElementPathBlock((ElementPathBlock) el);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementFilter) {
				str = this.visualizeElementFilter((ElementFilter) el);
			} else {
				System.out.println(el);
				System.out.println(el.getClass());
			}
			
			System.out.print(str);
			
		}
		
		String dotString = "digraph {\n";
		
		// First prepare the objects and their ids
		Iterator<String> iter = this.objectMap.keySet().iterator();
		while (iter.hasNext()) {
			String object = (String) iter.next();
		    dotString += "\t"+this.getObjectId(object)+" [label=\""+object+"\"];\n";
		}
		dotString += "\n";
		
		// Now preparing the relations
		for(int i = 0; i < this.statementList.size(); i++) {
			dotString += "\t"+this.statementList.get(i)+";\n";
		}
		
		dotString += "}";
		
		System.out.println(dotString);
		return dotString;
	}
	
	protected String visualizeElementUnion(ElementUnion element)
	{
		String returnString = ""; 
		returnString += "+---------------------+\n";
		returnString += "+---------------------+\n";
		return returnString;
	}
	
	protected void visualizeElementPathBlock(ElementPathBlock element)
	{
		String returnString = ""; 
		PathBlock pathBlock = (PathBlock) element.getPattern();
		for(int i = 0; i < pathBlock.size(); i++){
			TriplePath el = pathBlock.get(i);
			returnString = this.getObjectId(el.getSubject().toString())+" -> "+this.getObjectId(el.getObject().toString())+" [ label=\""+el.getPredicate()+"\" ]";
			this.statementList.add(returnString);
		}

		
	}
	
	protected String visualizeElementFilter(ElementFilter el)
	{
		String returnString = "";
		Expr expression = el.getExpr();
		
		String gvn = expression.getVarName();
		NodeValue gc = expression.getConstant();
		ExprVar gev = expression.getExprVar();
		ExprFunction gf = expression.getFunction();
		
		returnString += "!Filter: "+gf+"\n";
		
		return returnString;
	}
}
