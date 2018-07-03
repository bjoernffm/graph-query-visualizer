package main.tests.dot;

import static org.junit.Assert.*;

import org.junit.Test;

import main.app.dot.Node;

public class NodeTest {

	@Test
	public void test1() {
		Node node = new Node();
		node.setId(10);
		node.setLabel(" Label");
		node.setShape("square ");
		node.setFillcolor(" aliceblue ");
		node.setStyle(" filled ");
		
		assertEquals(node.getId(), 10);
		assertTrue(node.getLabel().equals("Label"));
		assertTrue(node.getShape().equals("square"));
		assertTrue(node.getFillcolor().equals("aliceblue"));
		assertTrue(node.getStyle().equals("filled"));
		assertTrue(node.toDot().equals("10 [label=\"Label\", shape=square, fillcolor=aliceblue, style=filled]"));
	}
	
	@Test
	public void test2() {
		Node node = new Node();
		assertTrue(node.toDot().equals(node.getId()+""));
	}

}
