package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementNamedGraphInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementNamedGraphInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX foaf: <http://xmlns.com/foaf/0.1/> PREFIX data: <http://example.org/foaf/> SELECT ?nick FROM NAMED <http://example.org/foaf/aliceFoaf> FROM NAMED <http://example.org/foaf/bobFoaf> WHERE { GRAPH data:bobFoaf { ?x foaf:mbox <mailto:bob@work.example> . ?x foaf:nick ?nick } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"GRAPH data:bobFoaf\";"));
	}

	@Test
	public void fail() throws Exception {
		ElementNamedGraphInterpreter interpreter = new ElementNamedGraphInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementNamedGraph needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}
}
