package main.app.common.interpreters;

import org.apache.jena.sparql.core.PathBlock;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.path.Path;
import org.apache.jena.sparql.syntax.ElementPathBlock;

import main.app.common.DotVisualizer;
import main.app.dot.Edge;
import main.app.dot.Node;
import main.app.dot.objects.EntityNode;

public class ElementPathBlockInterpreter implements Interpreter {
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception
	{
		if (obj.getClass() != ElementPathBlock.class) {
			throw new Exception(ElementPathBlock.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementPathBlock element = (ElementPathBlock) obj;
		PathBlock pathBlock = (PathBlock) element.getPattern();

		for(int i = 0; i < pathBlock.size(); i++) {
			TriplePath el = pathBlock.get(i);

			Node fromNode;
			Node toNode;

			org.apache.jena.graph.Node subject = el.getSubject();
			org.apache.jena.graph.Node predicate = el.getPredicate();
			org.apache.jena.graph.Node object = el.getObject();
			
			//System.out.println(el.isTriple());
			
			/**
			 * multiple paths
			 
			 org.apache.jena.sparql.path.P_Seq path = (org.apache.jena.sparql.path.P_Seq) el.getPath();
			org.apache.jena.sparql.path.P_Link path2 = (org.apache.jena.sparql.path.P_Link) path.getRight();
			org.apache.jena.graph.Node node = path2.getNode();
			System.out.println(node.getLocalName());*/
			
			/**
			 * one or more
			 
			 org.apache.jena.sparql.path.P_Seq path = (org.apache.jena.sparql.path.P_Seq) el.getPath();
			org.apache.jena.sparql.path.P_OneOrMore1 path2 = (org.apache.jena.sparql.path.P_OneOrMore1) path.getLeft();
			org.apache.jena.sparql.path.P_Link subpath = (org.apache.jena.sparql.path.P_Link) path2.getSubPath();
			System.out.println(subpath);
			*/
			
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
			//} else if (object.isConcrete()) {
			//	toNode = new EntityNode(object.getLiteralLexicalForm());
			//	toNode.setShape("box");
			} else {
				toNode = new EntityNode(object.toString());
				toNode.setShape("box");
			}
			
			Edge edge = new Edge();
			edge.setFrom(fromNode);
			edge.setTo(toNode);
			if (el.isTriple()) {
				edge.setLabel(predicate.getLocalName());
			} else {
				org.apache.jena.sparql.path.P_Seq path = (org.apache.jena.sparql.path.P_Seq) el.getPath();
				edge.setLabel(path.toString());
			}
			
			visualizer.getSubgraph().addNode(fromNode);
			visualizer.getSubgraph().addNode(toNode);
			visualizer.getSubgraph().addEdge(edge);
		}
	}
}
