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

import java.util.List;

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
				str = this.visualizeElementPathBlock((ElementPathBlock) el);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementFilter) {
				str = this.visualizeElementFilter((ElementFilter) el);
			} else {
				System.out.println(el);
				System.out.println(el.getClass());
			}
			
			System.out.print(str);
			
		}
		
		return "";
	}
	
	protected String visualizeElementUnion(ElementUnion element)
	{
		String returnString = ""; 
		returnString += "+---------------------+\n";
		returnString += "+---------------------+\n";
		return returnString;
	}
	
	protected String visualizeElementPathBlock(ElementPathBlock element)
	{
		String returnString = ""; 
		PathBlock pathBlock = (PathBlock) element.getPattern();
		for(int i = 0; i < pathBlock.size(); i++){
			TriplePath el = pathBlock.get(i);
			returnString += "["+el.getSubject()+"] --("+el.getPredicate()+")--> ["+el.getObject()+"]\n";
		}
		return returnString;
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
