package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;

public class GraphTest {

	@Test
	public void test() throws UnsupportedEncodingException {		
		Node node1 = new Node("1");
		node1.setLabel("Node 1");
		Node node2 = new Node("2");
		node2.setLabel("Node 2");
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setLabel("Label");
		
		Graph graph = new Graph();
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);
		
		String ret = graph.toDot();
		assertEquals(ret.length(), 223);
	}

}
