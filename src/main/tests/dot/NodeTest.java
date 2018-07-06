package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import main.app.dot.Node;

public class NodeTest {

	@Test
	public void test1() throws UnsupportedEncodingException {
		Node node = new Node("10");
		node.setLabel(" Label");
		node.setShape("square ");
		node.setFillcolor(" aliceblue ");
		node.setStyle(" filled ");
		
		assertTrue(node.getId().equals("d3d94468-02a4-3259-b55d-38e6d163e820"));
		assertTrue(node.getLabel().equals("Label"));
		assertTrue(node.getShape().equals("square"));
		assertTrue(node.getFillcolor().equals("aliceblue"));
		assertTrue(node.getStyle().equals("filled"));
		assertTrue(node.toDot().equals("\"d3d94468-02a4-3259-b55d-38e6d163e820\" [label=\"Label\", shape=\"square\", fillcolor=\"aliceblue\", style=\"filled\"]"));
	}
	
	@Test
	public void test2() throws UnsupportedEncodingException {
		Node node = new Node("22");
		assertTrue(node.toDot().equals("\""+node.getId()+"\" [label=\"22\"]"));
	}
	
	@Test
	public void test3() throws UnsupportedEncodingException {
		Node node = new Node("\"data\"");
		assertTrue(node.toDot().equals("\""+node.getId()+"\" [label=\"\\\"data\\\"\"]"));
	}

}
