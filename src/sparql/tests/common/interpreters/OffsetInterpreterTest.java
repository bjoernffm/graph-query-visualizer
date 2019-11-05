package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.OffsetInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class OffsetInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX foaf: <http://xmlns.com/foaf/0.1/> SELECT ?name WHERE { ?x foaf:name ?name } LIMIT 20 OFFSET 10");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"AggregateNode\", nodetype=\"unknown\", label=\"OFFSET 10\", tooltip=\"OFFSET 10\", shape=\"box\", fillcolor=\"greenyellow\", style=\"filled\"]"));
	}

	@Test
	public void fail() throws Exception {
		OffsetInterpreter interpreter = new OffsetInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class java.lang.Long needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
