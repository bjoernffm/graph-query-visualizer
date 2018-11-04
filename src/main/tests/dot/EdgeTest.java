package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;

public class EdgeTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		Node node1 = new Node("1");
		Node node2 = new Node("2");
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setArrowhead("dot");
		edge.setLabel("Label");
		edge.setStyle("dotted");
		edge.setLhead("cluster_1");
		
		Graph graph = new Graph("main");
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);

		assertEquals(edge.getFrom(), node1);
		assertEquals(edge.getTo(), node2);
		assertTrue(edge.getLabel().equals("Label"));
		assertTrue(edge.getLabel().equals("Label"));
		assertEquals(edge.toDot(), "\"c4ca4238-a0b9-3382-8dcc-509a6f75849b_main\" -> \"c81e728d-9d4c-3f63-af06-7f89cc14862c_main\" [label=\"Label\", style=\"dotted\", arrowhead=\"dot\", lhead=\"cluster_1\"]");
	}

}
