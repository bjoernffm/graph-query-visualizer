package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementDataInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementDataInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc:   <http://purl.org/dc/elements/1.1/> PREFIX :     <http://example.org/book/> PREFIX ns:   <http://example.org/ns#>  SELECT ?book ?title ?price { VALUES ?book { :book1 :book3 } ?book dc:title ?title ; ns:price ?price . }");
		List<String> ret = sqv.visualize();

		assertTrue(ret.get(0).contains("[dottype=\"DataNode\", nodetype=\"uri\", label=\":book1\", tooltip=\"http://example.org/book/book1\", shape=\"box\", fillcolor=\"darkorchid1\", style=\"filled\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", label=\"set\", labeltooltip=\"set\", style=\"dotted\", color=\"black\"]"));
	}

	@Test
	public void fail() throws Exception {
		ElementDataInterpreter interpreter = new ElementDataInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementData needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
