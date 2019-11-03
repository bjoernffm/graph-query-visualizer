package main.tests.dot;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.Subgraph;
import main.app.dot.objects.ConllNode;
import main.app.dot.objects.SelectNode;
import main.app.misc.RecursiveNodeContainer;

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
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph);
		graph.addNode(node3);
		graph.addEdge(edge2);
		
		graph.setLabel("Test");
		assertEquals("Test", graph.getLabel());

		assertEquals("invis", graph.getHiddenNode().getStyle());

		assertEquals(subgraph.hashCode(), subgraph.getFirstVisibleParent().hashCode());
		
		String ret = graph.toDot();
		assertEquals(-574427996, ret.hashCode());
		
		assertEquals(true, graph.clarificationEdgesEnabled());
		graph.disableClarificationEdges();
		assertEquals(false, graph.clarificationEdgesEnabled());
		graph.enableClarificationEdges();
		assertEquals(true, graph.clarificationEdgesEnabled());
		graph.enableClarificationEdges(false);
		assertEquals(false, graph.clarificationEdgesEnabled());
	}
	
	@Test
	public void testEdgeDuplicates() throws UnsupportedEncodingException {		
		Node node1 = new Node("1");
		node1.setLabel("Node 1");
		Node node2 = new Node("2");
		node2.setLabel("Node 2");
		
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("Label");
		
		Edge edge2 = new Edge();
		edge2.setFrom(node1);
		edge2.setTo(node2);
		edge2.setLabel("Label");
		
		Graph graph = new Graph("main");
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		
		String ret = graph.toDot();
		assertEquals(-740375897, ret.hashCode());
	}
	
	/*@Test
	public void testMultipleNodes() throws UnsupportedEncodingException {
		Node nodeOuter = new Node("nodeOuter");
		Node nodeInner1 = new Node("nodeInner1");
		Node nodeInner2 = new Node("nodeInner2");
		
		Edge edge1 = new Edge();
		edge1.setFrom(nodeOuter);
		edge1.setTo(nodeInner1);
		
		Edge edge2 = new Edge();
		edge2.setFrom(nodeInner1);
		edge2.setTo(nodeInner2);
		
		Subgraph subgraph2 = new Subgraph("cluster_2");
		subgraph2.addNode(nodeInner1);
		subgraph2.addNode(nodeInner2);
		subgraph2.addNode(nodeOuter);
		
		Subgraph subgraph1 = new Subgraph("cluster_1");
		subgraph1.addNode(nodeInner1);
		subgraph1.addNode(nodeOuter);
		subgraph1.addSubgraph(subgraph2);
		
		Graph graph = new Graph("main");
		graph.addNode(nodeOuter);
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addSubgraph(subgraph1);
		
		String ret = graph.toDot();
		assertEquals(ret.length(), 553);
	}*/
	
	@Test
	public void testMultipleEdges() throws UnsupportedEncodingException {
		Node nodeOuter = new Node("nodeOuter");
		Node nodeInner1 = new Node("nodeInner1");
		Node nodeInner2 = new Node("nodeInner2");
		
		Edge edge1 = new Edge();
		edge1.setFrom(nodeOuter);
		edge1.setTo(nodeInner1);
		
		Edge edge2 = new Edge();
		edge2.setFrom(nodeInner1);
		edge2.setTo(nodeInner2);
		
		Subgraph subgraph2 = new Subgraph("cluster_2");
		subgraph2.addNode(nodeInner2);
		subgraph2.addEdge(edge2);
		
		Subgraph subgraph1 = new Subgraph("cluster_1");
		subgraph1.addNode(nodeInner1);
		subgraph1.addEdge(edge2);
		subgraph1.addSubgraph(subgraph2);
		
		Graph graph = new Graph("main");
		graph.addNode(nodeOuter);
		graph.addEdge(edge1);
		graph.addSubgraph(subgraph1);
		
		String ret = graph.toDot();
		assertEquals(105790826, ret.hashCode());
	}
	
	@Test
	public void testGetNodesRecursive1() throws UnsupportedEncodingException {
		Node node1 = new Node("A");
		Node node2 = new Node("B");
		
		Subgraph subgraph1 = new Subgraph("subgraph_1");
		subgraph1.addNode(node1);
		subgraph1.addNode(node2);
		
		Node node3 = new Node("B");
		
		Subgraph subgraph2 = new Subgraph("subgraph_2");
		subgraph2.addNode(node3);
		
		Node node4 = new Node("C");
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph1);
		graph.addSubgraph(subgraph2);
		graph.addNode(node4);
		
		assertEquals(1, graph.getNodes().size());
		assertEquals(true, graph.getNodes().containsKey("0d61f837"));
		
		Map<String, ArrayList<RecursiveNodeContainer>> ret = graph.getNodesRecursive();

		assertEquals(ret.get("0d61f837").size(), 1);
		assertEquals(ret.get("9d5ed678").size(), 2);
		assertEquals(ret.get("7fc56270").size(), 1);
		
		graph.removeNode(node4);
	}
	
	@Test
	public void testGetNodesRecursive2() throws UnsupportedEncodingException {
		Node node1 = new Node("A");
		Node node2 = new Node("B");
		
		Subgraph subgraph1 = new Subgraph("subgraph_1");
		subgraph1.addNode(node1);
		subgraph1.addNode(node2);
		
		Node node3 = new Node("B");
		
		Subgraph subgraph2 = new Subgraph("subgraph_2");
		subgraph2.addNode(node3);
		
		Node node4 = new Node("C");
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph1);
		graph.addSubgraph(subgraph2);
		graph.addNode(node4);
		
		Map<String, ArrayList<RecursiveNodeContainer>> ret = graph.getNodesRecursive();

		assertEquals(ret.get("0d61f837").size(), 1);
		assertEquals(ret.get("9d5ed678").size(), 2);
		assertEquals(ret.get("7fc56270").size(), 1);
	}
	
	@Test
	public void testMergeConllNodes() {
		Node node1 = new ConllNode("A");
		Node node2 = new ConllNode("A");
		
		Graph graph = new Graph("main");
		
		graph.addNode(node1);
		assertEquals(1, graph.getNodes().size());
		
		graph.addNode(node2);
		assertEquals(1, graph.getNodes().size());
	}
	
	@Test
	public void testSelectConllNodes() {
		Node node1 = new ConllNode("A");
		Node node2 = new SelectNode("A");
		
		Graph graph = new Graph("main");
		
		graph.addNode(node1);
		assertEquals(1, graph.getNodes().size());
		
		graph.addNode(node2);
		assertEquals(1, graph.getNodes().size());
		assertEquals("doubleoctagon", graph.getNodes().get("7fc56270").getShape());
	}
	
	@Test
	public void testIgnoreAfterConllNodes() {
		Node node1 = new ConllNode("A");
		Node node2 = new Node("A");
		
		Graph graph = new Graph("main");
		
		graph.addNode(node1);
		assertEquals(1, graph.getNodes().size());

		graph.addNode(node2);
		assertEquals(1, graph.getNodes().size());
		assertEquals("octagon", graph.getNodes().get("7fc56270").getShape());
	}
	
	@Test
	public void testSeperate() {
		Node node1 = new Node("A");
		node1.drawSeparate();
		
		Graph graph = new Graph("main");
		
		graph.addNode(node1);
		assertEquals(1, graph.getNodes().size());
		assertEquals(graph.toDot(), graph.toString());
	}
}
