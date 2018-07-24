package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.Subgraph;

public class GraphTest {

	@Test
	public void test() throws UnsupportedEncodingException {		
		Node node1 = new Node("1");
		node1.setLabel("Node 1");
		Node node2 = new Node("2");
		node2.setLabel("Node 2");
		Node node3 = new Node("3");
		node3.setLabel("Node 3");
		
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("Label 1");
		
		Subgraph subgraph = new Subgraph("cluster_1");
		subgraph.addNode(node1);
		subgraph.addNode(node2);
		subgraph.addEdge(edge1);
		
		Edge edge2 = new Edge();
		edge2.setFrom(node3);
		edge2.setTo(node1);
		edge2.setLabel("Label 2");
		edge2.setLhead("cluster_1");
		
		Graph graph = new Graph();
		graph.addSubgraph(subgraph);
		graph.addNode(node3);
		graph.addEdge(edge2);
		
		String ret = graph.toDot();
		assertEquals(ret.length(), 557);
	}

}
