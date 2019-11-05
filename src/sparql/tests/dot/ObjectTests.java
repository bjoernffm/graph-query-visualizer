package sparql.tests.dot;

import static org.junit.Assert.*;

import org.junit.Test;

import sparql.app.dot.objects.AggregateNode;
import sparql.app.dot.objects.ConllNode;
import sparql.app.dot.objects.DataNode;
import sparql.app.dot.objects.DeleteSubgraph;
import sparql.app.dot.objects.EntityNode;
import sparql.app.dot.objects.FakeEdgeNode;
import sparql.app.dot.objects.FilterNode;
import sparql.app.dot.objects.GraphNode;
import sparql.app.dot.objects.InsertSubgraph;

public class ObjectTests {

	@Test
	public void test1() {
		ConllNode node = new ConllNode("A");
		node.set("key", "value");
		assertEquals("value", node.getConllList().get("key"));
	}

	@Test
	public void test2() {
		ConllNode node1 = new ConllNode("A");
		node1.set("key1", "value1");
		ConllNode node2 = new ConllNode("A");
		node2.set("key2", "value2");
		
		node1.migrate(node2);
		assertEquals("value1", node1.getConllList().get("key1"));
		assertEquals("value2", node1.getConllList().get("key2"));
	}

	@Test
	public void test3() {
		ConllNode node1 = new ConllNode("A");
		node1.set("key1", "value1");

		assertEquals(1911953630, node1.toDot().hashCode());
		assertEquals(true, !node1.toString().equals(""));
	}

	@Test
	public void test4() {
		EntityNode node1 = new EntityNode("A");
		assertEquals("aliceblue", node1.getFillcolor());
		assertEquals("circle", node1.getShape());
		assertEquals("filled", node1.getStyle());
		
		EntityNode node2 = new EntityNode(node1);
		assertEquals("aliceblue", node2.getFillcolor());
		assertEquals("circle", node2.getShape());
		assertEquals("filled", node2.getStyle());
	}

	@Test
	public void test5() {
		DataNode node1 = new DataNode("A");
		assertEquals("darkorchid1", node1.getFillcolor());
		assertEquals("box", node1.getShape());
		assertEquals("filled", node1.getStyle());
	}

	@Test
	public void test6() {
		AggregateNode node1 = new AggregateNode("A");
		assertEquals("greenyellow", node1.getFillcolor());
		assertEquals("box", node1.getShape());
		assertEquals("filled", node1.getStyle());
	}

	@Test
	public void test7() {
		FilterNode node1 = new FilterNode("A");
		assertEquals("skyblue", node1.getFillcolor());
		assertEquals("box", node1.getShape());
		assertEquals("filled,dashed", node1.getStyle());
	}

	@Test
	public void test8() {
		FakeEdgeNode node1 = new FakeEdgeNode("A");
		assertEquals(true, node1.isSeparate());
		assertEquals("plain", node1.getShape());
		assertEquals("none", node1.getStyle());
	}

	@Test
	public void test9() {
		DeleteSubgraph graph = new DeleteSubgraph("A");
		assertEquals("solid,filled", graph.getStyle());
		assertEquals("#ffcccc", graph.getFillcolor());
	}

	@Test
	public void test10() {
		InsertSubgraph graph = new InsertSubgraph("A");
		assertEquals("solid,filled", graph.getStyle());
		assertEquals("#ccffcc", graph.getFillcolor());
	}

	@Test
	public void test11() {
		GraphNode graph = new GraphNode("A");
		assertEquals("point", graph.getShape());
		assertEquals("invis", graph.getStyle());
	}

}
