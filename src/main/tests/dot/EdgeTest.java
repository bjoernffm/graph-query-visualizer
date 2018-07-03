package main.tests.dot;

import static org.junit.Assert.*;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Node;

public class EdgeTest {

	@Test
	public void test() {
		Node node1 = new Node();
		node1.setId(1);
		Node node2 = new Node();
		node2.setId(2);
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setLabel("Label");
		
		assertEquals(edge.getFrom(), node1);
		assertEquals(edge.getTo(), node2);
		assertTrue(edge.getLabel().equals("Label"));
		assertTrue(edge.getLabel().equals("Label"));
		assertTrue(edge.toDot().equals("1 -> 2 [label=\"Label\"]"));
	}

}
