package main.app.common.interpreters;

import org.apache.jena.sparql.core.PathBlock;
import org.apache.jena.sparql.core.TriplePath;
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
			
			visualizer.getSubgraph().addNode(fromNode);
			visualizer.getSubgraph().addNode(toNode);
			visualizer.getSubgraph().addEdge(edge);
		}
	}
}
