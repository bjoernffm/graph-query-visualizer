package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;

public class EdgeTest {

	@Test
	public void test1() throws UnsupportedEncodingException {
		Node node1 = new Node("1");
		Node node2 = new Node("2");
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setArrowhead("dot");
		edge.setLabel("Label");
		edge.setStyle("dotted");
		edge.setLhead("cluster_1");
		edge.setLabeltooltip("tooltip");
		edge.setDirection("direction");
		edge.setPenwidth(5);
		edge.setNoConstraint();
		
		Graph graph = new Graph("main");
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);

		assertEquals("48dda655-05c6-3be6-9902-37c3452a0fee", edge.getId());
		assertEquals(node1, edge.getFrom());
		assertEquals(node2, edge.getTo());
		assertEquals("dot", edge.getArrowhead());
		assertEquals("Label", edge.getLabel());
		assertEquals("cluster_1", edge.getLhead());
		assertEquals("tooltip", edge.getLabeltooltip());
		assertEquals("direction", edge.getDirection());
		assertEquals(5, edge.getPenwidth());
		assertEquals(edge.toString(), edge.toDot());
		assertEquals("\"c4ca4238_main\" -> \"c81e728d_main\" [dottype=\"Edge\", nodetype=\"unknown\", label=\"Label\", labeltooltip=\"tooltip\", style=\"dotted\", dir=\"direction\", arrowhead=\"dot\", color=\"black\", penwidth=5, lhead=\"cluster_1\", constraint=false]", edge.toDot());
	}

	@Test
	public void test2() throws UnsupportedEncodingException {
		Node node1 = new Node("1");
		Node node2 = new Node("2");
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setColor(null);
		
		Graph graph = new Graph("main");
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);
		
		assertEquals("\"c4ca4238_main\" -> \"c81e728d_main\" [dottype=\"Edge\", nodetype=\"unknown\"]", edge.toDot());
	}

	@Test
	public void test3() throws UnsupportedEncodingException {
		Node node1 = new Node("1");
		Node node2 = new Node("2");
		
		Edge edge = new Edge();
		edge.setFrom(node1);
		edge.setTo(node2);
		edge.setArrowhead("");
		edge.setLabel("");
		edge.setStyle("");
		edge.setLhead("");
		edge.setColor("");
		edge.setLabeltooltip("");
		edge.setDirection("");
		
		Graph graph = new Graph("main");
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge);
		
		assertEquals("\"c4ca4238_main\" -> \"c81e728d_main\" [dottype=\"Edge\", nodetype=\"unknown\"]", edge.toDot());
	}

}
