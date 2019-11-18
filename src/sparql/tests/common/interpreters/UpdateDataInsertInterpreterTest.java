package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.UpdateDataInsertInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class UpdateDataInsertInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT DATA {  <http://example/book1> dc:title \"A new book\" ; dc:creator \"A.N.Other\" . }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"INSERT\";"));
		assertTrue(ret.get(0).contains("tooltip=\"INSERT\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ccffcc\";"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc: <http://purl.org/dc/elements/1.1/> PREFIX ns: <http://example.org/ns#> INSERT DATA { GRAPH <http://example/bookStore> { <http://example/book1>  ns:price  42 } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"INSERT INTO\\nGRAPH bookStore\";"));
		assertTrue(ret.get(0).contains("tooltip=\"INSERT INTO\\nGRAPH bookStore\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ccffcc\";"));
	}

	@Test
	public void fail() throws Exception {
		UpdateDataInsertInterpreter interpreter = new UpdateDataInsertInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.modify.request.UpdateDataInsert needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
