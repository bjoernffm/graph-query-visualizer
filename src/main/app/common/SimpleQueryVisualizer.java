package main.app.common;

import org.apache.jena.query.Query;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.shared.impl.PrefixMappingImpl;
import org.apache.jena.sparql.core.PathBlock;
import org.apache.jena.sparql.core.Prologue;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprAggregator;
import org.apache.jena.sparql.expr.ExprFunction;
import org.apache.jena.sparql.expr.ExprVar;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementUnion;
import org.apache.jena.sparql.syntax.Template;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;

import org.apache.jena.sparql.syntax.ElementFilter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

final public class SimpleQueryVisualizer extends QueryVisualizer implements QueryVisualizerInterface {
	
	Graph graph = new Graph();

	public SimpleQueryVisualizer(String query) {
		super(query);
	}

	public String visualize()
	{
		//ArrayList<ExprAggregator> ret2 = (ArrayList) this.query.getAggregators();
		//System.out.println(ret2.getClass());
		
		//String ret = this.query.getBaseURI();
		//System.out.println(ret);
		
		//List<String> ret = this.query.getGraphURIs();
		//System.out.println(ret);
		
		//PrefixMappingImpl ret2 = (PrefixMappingImpl) this.query.getPrefixMapping();

		ElementGroup queryPattern = (ElementGroup) this.query.getQueryPattern();
		for(int i = 0; i < queryPattern.size(); i++) {
			Element el = queryPattern.get(i);
			if (el instanceof org.apache.jena.sparql.syntax.ElementPathBlock) {
				this.visualizeElementPathBlock((ElementPathBlock) el);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementFilter) {
				this.visualizeElementFilter((ElementFilter) el);
			} else {
				//System.out.println(el.getClass());
				//System.out.println(el+"\n");
			}
		}
		
		List<Var> ret = this.query.getProjectVars();
		for ( Iterator<Var> iterator = ret.iterator(); iterator.hasNext(); ) {
			Var var = iterator.next();

			try {
				Node node = new Node("?"+var.getName());
				node.setFillcolor("green");
				node.setStyle("filled");
				node.setShape("circle");
				this.graph.addNode(node);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(this.graph);
		return "";
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
		PathBlock pathBlock = (PathBlock) element.getPattern();

		for(int i = 0; i < pathBlock.size(); i++) {
			TriplePath el = pathBlock.get(i);


			Node fromNode;
			Node toNode;
			try {
				fromNode = new Node(el.getSubject().toString());
				toNode = new Node(el.getObject().toString());
				
				org.apache.jena.graph.Node predicate = el.getPredicate();
				
				Edge edge = new Edge();
				edge.setFrom(fromNode);
				edge.setTo(toNode);
				edge.setLabel(predicate.getLocalName());
				
				this.graph.addNode(fromNode);
				this.graph.addNode(toNode);
				this.graph.addEdge(edge);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void visualizeElementFilter(ElementFilter el)
	{
		
		Expr expression = el.getExpr();
		ExprFunction exFunction = expression.getFunction();
		
		if (el.getExpr().isFunction()) {
			try {
				Node filter = new Node(exFunction.toString());
				filter.setLabel(el.toString());
				filter.setFillcolor("skyblue");
				filter.setStyle("filled,dotted");
				filter.setShape("box");
				this.graph.addNode(filter);
				
				Set<Var> mentionedVars = exFunction.getVarsMentioned();
				for(Var mentionedVar: mentionedVars) {
					Node filter2 = new Node(mentionedVar.toString());
					this.graph.addNode(filter2);
					Edge edge = new Edge();
					edge.setArrowhead("dot");
					edge.setFrom(filter);
					edge.setTo(filter2);
					this.graph.addEdge(edge);
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
