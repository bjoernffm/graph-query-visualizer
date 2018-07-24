package main.app.common;

import org.apache.jena.sparql.core.PathBlock;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingHashMap;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprAggregator;
import org.apache.jena.sparql.expr.ExprFunction;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.aggregate.Aggregator;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.Subgraph;
import main.app.dot.objects.DataNode;
import main.app.dot.objects.EntityNode;
import main.app.dot.objects.FilterNode;

import org.apache.jena.sparql.syntax.ElementFilter;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final public class SimpleQueryVisualizer extends QueryVisualizer implements QueryVisualizerInterface {
	
	Graph graph = new Graph();
	Subgraph subgraph = new Subgraph("cluster_1");

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
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementData) {
				this.visualizeElementData((ElementData) el);
			}  else if (el instanceof org.apache.jena.sparql.syntax.ElementBind) {
				this.visualizeElementBind((ElementBind) el);
			} else {
				System.out.println(el.getClass());
				System.out.println(el+"\n");
			}
		}
		
		VarExprList project = this.query.getProject();
		
		List<Var> projectVars = project.getVars();
		Map<Var, Expr> projectExpressions = project.getExprs();
		
		for(int i = 0; i < projectVars.size(); i++) {
			Var var = projectVars.get(i);

			try {
				Node entityNode = new EntityNode("?"+var.getName());
				entityNode.setShape("doublecircle");
				this.subgraph.addNode(entityNode);
				
				if (projectExpressions.containsKey(var)) {
					ExprAggregator projectExpression = (ExprAggregator) projectExpressions.get(var);

					Aggregator aggregator = projectExpression.getAggregator();
					ExprList expressionList = aggregator.getExprList();
					//System.out.println(aggregator);
					
					Set<Var> mentionedVars = expressionList.getVarsMentioned();
					for(Var mentionedVar: mentionedVars) {
						Node varNode = new EntityNode(mentionedVar.toString());
						this.subgraph.addNode(varNode);
						
						Edge edge = new Edge();
						edge.setLabel(aggregator.toString());
						edge.setFrom(varNode);
						edge.setTo(entityNode);
						this.subgraph.addEdge(edge);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.graph.addSubgraph(this.subgraph);
		System.out.println(this.graph);
		return "";
	}
	
	protected void visualizeElementBind(ElementBind el) {
		// TODO Auto-generated method stub
		//System.out.println(el);
		Expr expression = el.getExpr();
		Var var = el.getVar();
		//System.out.println(expression.getVarsMentioned());
		
		try {
			Node entityNode = new EntityNode("?"+var.getVarName());
			this.subgraph.addNode(entityNode);
			
			Set<Var> mentionedVars = expression.getVarsMentioned();
			for(Var mentionedVar: mentionedVars) {
				Node varNode = new EntityNode(mentionedVar.toString());
				this.subgraph.addNode(varNode);
				
				Edge edge = new Edge();
				edge.setLabel(el.toString());
				edge.setFrom(varNode);
				edge.setTo(entityNode);
				this.subgraph.addEdge(edge);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void visualizeElementData(ElementData el) {
		List<Binding> rows = el.getRows();
		BindingHashMap ele = (BindingHashMap) rows.get(0);
		
		for ( Iterator<Var> iterator = ele.vars(); iterator.hasNext(); ) {
			Var var = iterator.next();
			//System.out.println(var.getName());
			//System.out.println(ele.get(var).);
			//System.out.println(ele.get(var).getLocalName());

			try {
				Node dataNode = new DataNode(ele.get(var).toString());
				Node entityNode = new EntityNode("?"+var.getName());
				Edge edge = new Edge();
				edge.setFrom(dataNode);
				edge.setTo(entityNode);
				edge.setLabel("value");
				edge.setStyle("dotted");
				
				this.subgraph.addNode(dataNode);
				this.subgraph.addNode(entityNode);
				this.subgraph.addEdge(edge);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void visualizeElementPathBlock(ElementPathBlock element)
	{
		PathBlock pathBlock = (PathBlock) element.getPattern();

		for(int i = 0; i < pathBlock.size(); i++) {
			TriplePath el = pathBlock.get(i);

			Node fromNode;
			Node toNode;
			try {
				org.apache.jena.graph.Node subject = el.getSubject();
				org.apache.jena.graph.Node predicate = el.getPredicate();
				org.apache.jena.graph.Node object = el.getObject();
				
				if (subject.isVariable()) {
					fromNode = new EntityNode("?"+subject.getName());
				} else if (subject.isConcrete()) {
					fromNode = new EntityNode(subject.getLiteralLexicalForm());
					fromNode.setShape("box");
				} else {
					fromNode = new EntityNode(subject.toString());
					fromNode.setShape("box");
				}
				
				if (object.isVariable()) {
					toNode = new EntityNode("?"+object.getName());
				} else if (object.isConcrete()) {
					toNode = new EntityNode(object.getLiteralLexicalForm());
					toNode.setShape("box");
				} else {
					toNode = new EntityNode(object.toString());
					toNode.setShape("box");
				}
				
				Edge edge = new Edge();
				edge.setFrom(fromNode);
				edge.setTo(toNode);
				edge.setLabel(predicate.getLocalName());
				
				this.subgraph.addNode(fromNode);
				this.subgraph.addNode(toNode);
				this.subgraph.addEdge(edge);
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
				Node filter = new FilterNode(exFunction.toString());
				filter.setLabel(el.toString());
				this.subgraph.addNode(filter);
				
				Set<Var> mentionedVars = exFunction.getVarsMentioned();
				for(Var mentionedVar: mentionedVars) {
					Node filter2 = new EntityNode(mentionedVar.toString());
					this.subgraph.addNode(filter2);
					Edge edge = new Edge();
					edge.setArrowhead("dot");
					edge.setFrom(filter);
					edge.setTo(filter2);
					this.subgraph.addEdge(edge);
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
