package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.UpdateDeleteWhereInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class UpdateDeleteWhereInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX foaf:  <http://xmlns.com/foaf/0.1/> DELETE WHERE { 'Person' a ?person. ?person foaf:givenName 'Fred'; ?property      ?value }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"DELETE\\nWHERE\";"));
		assertTrue(ret.get(0).contains("tooltip=\"DELETE\\nWHERE\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ffcccc\";"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX foaf:  <http://xmlns.com/foaf/0.1/> DELETE WHERE { GRAPH <http://example.com/names> { ?person foaf:givenName 'Fred' ; ?property1 ?value1 } GRAPH <http://example.com/addresses> { ?person ?property2 ?value2 } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"DELETE FROM\\nGRAPH addresses\\nWHERE\";"));
		assertTrue(ret.get(0).contains("tooltip=\"DELETE FROM\\nGRAPH names\\nWHERE\";"));
	}

	@Test
	public void fail() throws Exception {
		UpdateDeleteWhereInterpreter interpreter = new UpdateDeleteWhereInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.modify.request.UpdateDeleteWhere needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
