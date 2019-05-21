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
		
		String ret = graph.toDot();
		System.out.println(ret);
		assertEquals(ret.hashCode(), 368889422);
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
		assertEquals(ret.hashCode(), 1171864895);
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
		assertEquals(ret.hashCode(), -98663907);
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
		
		Map<String, ArrayList<RecursiveNodeContainer>> ret = graph.getNodesRecursive();

		assertEquals(ret.get("0d61f837-0cad-3d41-af80-b84d143e1257").size(), 1);
		assertEquals(ret.get("9d5ed678-fe57-3cca-a101-40957afab571").size(), 2);
		assertEquals(ret.get("7fc56270-e7a7-3fa8-9a59-35b72eacbe29").size(), 1);
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

		assertEquals(ret.get("0d61f837-0cad-3d41-af80-b84d143e1257").size(), 1);
		assertEquals(ret.get("9d5ed678-fe57-3cca-a101-40957afab571").size(), 2);
		assertEquals(ret.get("7fc56270-e7a7-3fa8-9a59-35b72eacbe29").size(), 1);
	}
}
