package sparql.tests.dot;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Node;
import sparql.app.dot.Subgraph;

public class MultipleNodesInSubgraphsTest {

	@Test
	public void twoNodesTest1() throws UnsupportedEncodingException {
		Node node1 = new Node("A");
		node1.setLabel("A");
		Node node2 = new Node("B");
		node2.setLabel("B");
		
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("has");
		
		Subgraph subgraph1 = new Subgraph("cluster_1");
		subgraph1.addNode(node1);
		subgraph1.addNode(node2);
		subgraph1.addEdge(edge1);
		
		Node node3 = new Node("A");
		node3.setLabel("A");
		Node node4 = new Node("C");
		node4.setLabel("C");
		
		Edge edge2 = new Edge();
		edge2.setFrom(node3);
		edge2.setTo(node4);
		edge2.setLabel("has");
		
		Subgraph subgraph2 = new Subgraph("cluster_2");
		subgraph2.addNode(node3);
		subgraph2.addNode(node4);
		subgraph2.addEdge(edge2);
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph1);
		graph.addSubgraph(subgraph2);
		
		String ret = graph.toDot();
		//System.out.println(ret.hashCode());
		assertEquals(210686621, ret.hashCode());
	}

	@Test
	public void twoNodesTest2() throws UnsupportedEncodingException {
		Node node1 = new Node("A");
		node1.setLabel("A");
		Node node2 = new Node("B");
		node2.setLabel("B");
		
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("has");
		
		Subgraph subgraph1 = new Subgraph("cluster_1");
		subgraph1.addNode(node1);
		subgraph1.addNode(node2);
		subgraph1.addEdge(edge1);
		
		Node node3 = new Node("A");
		node3.setLabel("A");
		Node node4 = new Node("C");
		node4.setLabel("C");
		
		Edge edge2 = new Edge();
		edge2.setFrom(node3);
		edge2.setTo(node4);
		edge2.setLabel("has");
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph1);
		graph.addNode(node3);
		graph.addNode(node4);
		graph.addEdge(edge2);
		
		String ret = graph.toDot();
		//System.out.println(ret.hashCode());
		assertEquals(-1967900315, ret.hashCode());
	}

	@Test
	public void threeNodesTest1() throws UnsupportedEncodingException {
		Node node1 = new Node("A");
		node1.setLabel("A");
		Node node2 = new Node("B");
		node2.setLabel("B");
		
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("has");
		
		Subgraph subgraph1 = new Subgraph("cluster_1");
		subgraph1.addNode(node1);
		subgraph1.addNode(node2);
		subgraph1.addEdge(edge1);
		
		Node node3 = new Node("A");
		node3.setLabel("A");
		Node node4 = new Node("C");
		node4.setLabel("C");
		
		Edge edge2 = new Edge();
		edge2.setFrom(node3);
		edge2.setTo(node4);
		edge2.setLabel("has");
		
		Subgraph subgraph2 = new Subgraph("cluster_2");
		subgraph2.addNode(node3);
		subgraph2.addNode(node4);
		subgraph2.addEdge(edge2);
		
		Node node5 = new Node("A");
		node5.setLabel("A");
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph1);
		graph.addSubgraph(subgraph2);
		graph.addNode(node5);
		
		String ret = graph.toDot();
		//System.out.println(ret.hashCode());
		assertEquals(-219604524, ret.hashCode());
	}

	@Test
	public void threeNodesTest2() throws UnsupportedEncodingException {
		Node node1 = new Node("A");
		node1.setLabel("A");
		Node node2 = new Node("B");
		node2.setLabel("B");
		
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("has");
		
		Subgraph subgraph1 = new Subgraph("cluster_1");
		subgraph1.addNode(node1);
		subgraph1.addNode(node2);
		subgraph1.addEdge(edge1);
		
		Node node3 = new Node("A");
		node3.setLabel("A");
		Node node4 = new Node("C");
		node4.setLabel("C");
		
		Edge edge2 = new Edge();
		edge2.setFrom(node3);
		edge2.setTo(node4);
		edge2.setLabel("has");
		
		Subgraph subgraph2 = new Subgraph("cluster_2");
		subgraph2.addNode(node3);
		subgraph2.addNode(node4);
		subgraph2.addEdge(edge2);
		
		Node node5 = new Node("A");
		node5.setLabel("A");
		Node node6 = new Node("D");
		node6.setLabel("D");
		
		Edge edge3 = new Edge();
		edge3.setFrom(node5);
		edge3.setTo(node6);
		edge3.setLabel("has");
		
		Subgraph subgraph3 = new Subgraph("cluster_3");
		subgraph3.addNode(node5);
		subgraph3.addNode(node6);
		subgraph3.addEdge(edge3);
		
		Graph graph = new Graph("main");
		graph.addSubgraph(subgraph1);
		graph.addSubgraph(subgraph2);
		graph.addSubgraph(subgraph3);
		
		String ret = graph.toDot();
		assertEquals(687830408, ret.hashCode());
	}
}
