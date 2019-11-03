package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.apache.jena.graph.NodeFactory;
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
		node.setTooltip(" tooltip ");
		node.setLabeljust(" labeljust ");
		node.setColor(" color ");
		
		Node nodeClone = new Node(node);
		
		Graph graph = new Graph("main");
		graph.addNode(node);
		graph.addNode(nodeClone);
		
		assertEquals("d3d94468", node.getId());
		assertEquals("Label", node.getLabel());
		assertEquals("square", node.getShape());
		assertEquals("aliceblue", node.getFillcolor());
		assertEquals("color", node.getColor());
		assertEquals("filled", node.getStyle());
		assertEquals("tooltip", node.getTooltip());
		assertEquals("labeljust", node.getLabeljust());
		assertEquals(false, node.isSeparate());
		assertTrue(node.getIdSuffix().equals(""));
		assertEquals(graph, node.getParentGraph());
		assertEquals("\"d3d94468_main\" [dottype=\"Node\", nodetype=\"unknown\", label=\"Label\", tooltip=\"tooltip\", shape=\"square\", fillcolor=\"aliceblue\", style=\"filled\"]", node.toDot());
		assertEquals(node.toString(), node.toDot());
		
		assertEquals(node.getId(), nodeClone.getId());
		assertEquals(node.getLabel(), nodeClone.getLabel());
		assertEquals(node.getTooltip(), nodeClone.getTooltip());
		assertEquals(node.getLabeljust(), nodeClone.getLabeljust());
		assertEquals(node.getShape(), nodeClone.getShape());
		assertEquals(node.getFillcolor(), nodeClone.getFillcolor());	
		assertEquals(node.getStyle(), nodeClone.getStyle());		
	}
	
	@Test
	public void test2() throws UnsupportedEncodingException {
		Node node = new Node("\"data\"");
		node.setColor(null);
		
		assertEquals("\"755af0e7\" [dottype=\"Node\", nodetype=\"unknown\", label=\"\\\"data\\\"\", tooltip=\"\\\"data\\\"\"]", node.toDot());
		assertEquals(null, node.getColor());
	}
	
	@Test
	public void test3() throws UnsupportedEncodingException {
		Node node = new Node("test");
		node.drawSeparate();
		
		assertEquals(true, node.isSeparate());
		assertFalse(node.getIdSuffix().equals(""));
	}

	@Test
	public void test4() throws UnsupportedEncodingException {
		Node node = new Node("10");
		node.setShape("");
		node.setFillcolor("");
		node.setStyle("");
		node.setTooltip("");
		node.setLabeljust("");
		
		assertEquals("\"d3d94468\" [dottype=\"Node\", nodetype=\"unknown\", label=\"10\", tooltip=\"10\"]", node.toDot());	
	}

	@Test
	public void testNodeType() throws UnsupportedEncodingException {
		Node node = new Node("test");
		assertEquals("\"098f6bcd\" [dottype=\"Node\", nodetype=\"unknown\", label=\"test\", tooltip=\"test\"]", node.toDot());
		
		org.apache.jena.graph.Node nodeUri = NodeFactory.createURI("http://www.bjoern.de/index.html#lala");
		node.setNodeType(nodeUri);
		assertEquals(node.getNodeType(), "uri");
		assertEquals("\"098f6bcd\" [dottype=\"Node\", nodetype=\"uri\", label=\"test\", tooltip=\"test\"]", node.toDot());
		
		org.apache.jena.graph.Node nodeLiteral = NodeFactory.createLiteral("Literal");
		node.setNodeType(nodeLiteral);
		assertEquals(node.getNodeType(), "literal");
		
		org.apache.jena.graph.Node nodeVariable = NodeFactory.createVariable("Variable");
		node.setNodeType(nodeVariable);
		assertEquals(node.getNodeType(), "variable");
		
		org.apache.jena.graph.Node nodeUnknown = NodeFactory.createBlankNode();
		node.setNodeType(nodeUnknown);
		assertEquals(node.getNodeType(), "unknown");
		
		node.setNodeType("test");
		assertEquals(node.getNodeType(), "test");	
	}

}
