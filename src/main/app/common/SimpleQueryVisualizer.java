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

import main.app.dot.Edge;
import main.app.dot.Node;

import org.apache.jena.sparql.syntax.ElementFilter;

import java.util.Iterator;
import java.util.List;

final public class SimpleQueryVisualizer extends QueryVisualizer implements QueryVisualizerInterface {

	public SimpleQueryVisualizer(String query) {
		super(query);
	}

	public String visualize()
	{
		//ElementGroup elGroup = this.getElementGroupByQuery(string);

		//List<Element> list = elGroup.getElements();
		
		
		//return iterate(list);
		return "";
	}

	protected String iterate(List<Element> list)
	{
		for(int i = 0; i < list.size(); i++) {
			Element el = list.get(i);
			System.out.println(el.getClass());
			System.out.println(el+"\n");

			if (el instanceof org.apache.jena.sparql.syntax.ElementPathBlock) {
				//this.visualizeElementPathBlock((ElementPathBlock) el);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementFilter) {
				//this.visualizeElementFilter((ElementFilter) el);
			} else {
				//System.out.println(el.getClass());
				//System.out.println(el+"\n");
			}
		}
		
		return "";
		
		/*String dotString = "digraph {\n";
		
		// First prepare the objects and their ids
		Iterator<String> iter = this.objectMap.keySet().iterator();
		while (iter.hasNext()) {
			String object = (String) iter.next();
		    dotString += "\t"+this.getObjectId(object)+" [label=\""+this.escape(object)+"\"];\n";
		}
		
		dotString += "\n";
		
		// Now preparing the relations
		for(int i = 0; i < this.statementList.size(); i++) {
			dotString += "\t"+this.statementList.get(i)+";\n";
		}
		
		dotString += "}";
		
		return dotString;*/
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
		
		for(int i = 0; i < pathBlock.size(); i++) {
			TriplePath el = pathBlock.get(i);

			Node fromNode = new Node();
			fromNode.setLabel(el.getSubject().toString());
			
			Node toNode = new Node();
			toNode.setLabel(el.getObject().toString());
			
			Edge edge = new Edge();
			edge.setFrom(fromNode);
			edge.setTo(toNode);
			edge.setLabel(el.getPredicate().toString());
		}
	}
	
	protected String visualizeElementFilter(ElementFilter el)
	{
		System.out.println(el);
		String returnString = "";
		Expr expression = el.getExpr();
		ExprFunction exFunction = expression.getFunction();
		
		String gvn = expression.getVarName();
		NodeValue gc = expression.getConstant();
		ExprVar gev = expression.getExprVar();
		ExprFunction gf = expression.getFunction();
		
		returnString += "!Filter: "+gf+"\n";
		
		return returnString;
	}
}
