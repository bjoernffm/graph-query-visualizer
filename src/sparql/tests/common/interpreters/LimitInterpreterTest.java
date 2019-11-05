package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.LimitInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class LimitInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX foaf: <http://xmlns.com/foaf/0.1/> SELECT ?name WHERE { ?x foaf:name ?name } LIMIT 20");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"AggregateNode\", nodetype=\"unknown\", label=\"LIMIT 20\", tooltip=\"LIMIT 20\", shape=\"box\", fillcolor=\"greenyellow\", style=\"filled\"]"));
	}

	@Test
	public void fail() throws Exception {
		LimitInterpreter interpreter = new LimitInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class java.lang.Long needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
