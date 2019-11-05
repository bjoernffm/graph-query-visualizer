package sparql.app.dot.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementTriplesBlock;

import sparql.app.dot.Edge;
import sparql.app.dot.Node;
import sparql.app.dot.objects.SelectNode;

public class Interpreter extends Parser {

	public Interpreter(String dot)
	{
		super(dot);
	}
	
	public String toSparql()
	{
		Map<String, org.apache.jena.graph.Node> sparqlNodeMap = new HashMap<>();
		
		// first transform the dot nodes to sparql nodes
		for (Entry<String, Node> entry: this.nodeMap.entrySet()) {
			Node node = entry.getValue();
			org.apache.jena.graph.Node sparqlNode = null;
			
			if (node.getNodeType().equals("uri")) {
				sparqlNode = NodeFactory.createURI(node.getTooltip());
			} else if (node.getNodeType().equals("variable")) {
				sparqlNode = NodeFactory.createVariable(node.getLabel().substring(1));
			} else if (node.getNodeType().equals("literal")) {
				String label = node.getLabel();
				label = label.replace("\\\"", "");
				sparqlNode = NodeFactory.createLiteral(label);
				System.out.println(sparqlNode.getClass());
			} else {
				sparqlNode = NodeFactory.createBlankNode();
			}
			
			sparqlNodeMap.put(entry.getKey(), sparqlNode);
		}
		
		ElementGroup body = new ElementGroup();
		
		for (Entry<String, Edge> entry: this.edgeMap.entrySet()) {
			Edge edge = entry.getValue();
			org.apache.jena.graph.Node sparqlNode = null;
			
			if (edge.getNodeType().equals("uri")) {
				sparqlNode = NodeFactory.createURI(edge.getLabeltooltip());
			} else if (edge.getNodeType().equals("variable")) {
				sparqlNode = NodeFactory.createVariable(edge.getLabel().substring(1));
			} else if (edge.getNodeType().equals("literal")) {
				sparqlNode = NodeFactory.createLiteral(edge.getLabel());
			} else {
				sparqlNode = NodeFactory.createBlankNode();
			}
			
			String[] nodes = entry.getKey().split(",");
			Triple pattern = Triple.create(
				sparqlNodeMap.get(nodes[0]),
				sparqlNode,
				sparqlNodeMap.get(nodes[1])
			);
			ElementTriplesBlock block = new ElementTriplesBlock();
			block.addTriple(pattern);
			body.addElement(block);
		}
		
		Query q = QueryFactory.make();
		q.setQueryPattern(body);
		q.setQuerySelectType();
		
		for (Entry<String, Node> entry: this.nodeMap.entrySet()) {
			Node node = entry.getValue();
			if (node instanceof SelectNode) {
				q.addResultVar(node.getLabel().substring(1));  
			}
		}
		
		System.out.println(q);
		
		return null;
	}

}
