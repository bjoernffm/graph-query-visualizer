package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.UpdateDataDeleteInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class UpdateDataDeleteInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc: <http://purl.org/dc/elements/1.1/> DELETE DATA { <http://example/book2> dc:title \"David Copperfield\" ; dc:creator \"Edmund Wells\" . }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"DELETE\";"));
		assertTrue(ret.get(0).contains("tooltip=\"DELETE\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ffcccc\";"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc: <http://purl.org/dc/elements/1.1/> DELETE DATA { GRAPH <http://example/bookStore> { <http://example/book1>  dc:title  \"Fundamentals of Compiler Desing\" } } ; PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT DATA { GRAPH <http://example/bookStore> { <http://example/book1>  dc:title  \"Fundamentals of Compiler Design\" } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"DELETE FROM\\nGRAPH bookStore\";"));
		assertTrue(ret.get(0).contains("tooltip=\"DELETE FROM\\nGRAPH bookStore\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ffcccc\";"));
	}

	@Test
	public void fail() throws Exception {
		UpdateDataDeleteInterpreter interpreter = new UpdateDataDeleteInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.modify.request.UpdateDataDelete needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
