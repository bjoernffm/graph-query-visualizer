package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import main.app.dot.Graph;
import main.app.dot.Node;

public class NodeTest {

	@Test
	public void test1() throws UnsupportedEncodingException {
		Node node = new Node("10");
		node.setLabel(" Label");
		node.setShape("square ");
		node.setFillcolor(" aliceblue ");
		node.setStyle(" filled ");
		
		Graph graph = new Graph("main");
		graph.addNode(node);
		
		assertEquals(node.getId(), "d3d94468");
		assertEquals(node.getLabel(), "Label");
		assertEquals(node.getShape(), "square");
		assertEquals(node.getFillcolor(), "aliceblue");
		assertEquals(node.getStyle(), "filled");
		assertEquals(node.toDot(), "\"d3d94468_main\" [label=\"Label\", shape=\"square\", fillcolor=\"aliceblue\", style=\"filled\"]");
	}
	
	@Test
	public void test2() throws UnsupportedEncodingException {
		Node node = new Node("22");
		
		Graph graph = new Graph("main");
		graph.addNode(node);
		
		assertTrue(node.toDot().equals("\""+node.getUniqueId()+"\" [label=\"22\"]"));
	}
	
	@Test
	public void test3() throws UnsupportedEncodingException {
		Node node = new Node("\"data\"");
		
		Graph graph = new Graph("main");
		graph.addNode(node);
		
		System.out.println(node.toDot());
		//assertEquals(node.toDot(), "\""+node.getUniqueId()+"\" [label=\"\\\"data\\\"\"]");
	}

}
