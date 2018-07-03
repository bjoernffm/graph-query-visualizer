package main.tests.dot;

import static org.junit.Assert.*;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;

public class GraphTest {

	@Test
	public void test() {		
		Node node1 = new Node();
		node1.setLabel("Node 1");
		Node node2 = new Node();
		node2.setLabel("Node 2");
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setLabel("Label");
		
		Graph graph = new Graph();
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);
		
		assertEquals(node1.getId(), 0);
		assertEquals(node2.getId(), 1);
		
		String ret = graph.toDot();
		assertEquals(ret.length(), 75);
	}

}
