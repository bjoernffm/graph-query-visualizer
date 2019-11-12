package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.OrderByInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class OrderByInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX space: <http://purl.org/net/schemas/space/> SELECT ?craft { ?craft a space:Spacecraft. ?craft space:numberOfPassengers ?number } ORDER BY DESC(?number)");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"AggregateNode\", nodetype=\"unknown\", label=\"ORDER BY\\n---------\\n* ?number DESC\\l\", tooltip=\"ORDER BY\\n---------\\n* ?number DESC\\l\", shape=\"box\", fillcolor=\"greenyellow\", style=\"filled\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", style=\"dotted\", arrowhead=\"dot\", color=\"black\"]"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX space: <http://purl.org/net/schemas/space/> SELECT ?craft { ?craft a space:Spacecraft. ?craft space:numberOfPassengers ?number } ORDER BY ASC(?number)");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"AggregateNode\", nodetype=\"unknown\", label=\"ORDER BY\\n---------\\n* ?number ASC\\l\", tooltip=\"ORDER BY\\n---------\\n* ?number ASC\\l\", shape=\"box\", fillcolor=\"greenyellow\", style=\"filled\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", style=\"dotted\", arrowhead=\"dot\", color=\"black\"]"));
	}

	@Test
	public void fail() throws Exception {
		OrderByInterpreter interpreter = new OrderByInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class java.util.ArrayList needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
