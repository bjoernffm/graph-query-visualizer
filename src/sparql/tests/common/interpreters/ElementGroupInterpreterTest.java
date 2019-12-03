package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementGroupInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementGroupInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX : <http://example/> SELECT ?x { ?x a :Toy . { SELECT ?x ( count(?order) as ?q ) { ?x :order ?order } GROUP BY ?x } FILTER ( ?q > 5 ) }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"SUBSELECT\""));
		assertTrue(ret.get(0).contains("tooltip=\"SUBSELECT\""));
	}

	@Test
	public void fail() throws Exception {
		ElementGroupInterpreter interpreter = new ElementGroupInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementGroup needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
