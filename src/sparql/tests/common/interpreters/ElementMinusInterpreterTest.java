package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementMinusInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementMinusInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("SELECT * {  ?s ?p ?o  MINUS  { ?x ?y ?z } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"MINUS\""));
		assertTrue(ret.get(0).contains("tooltip=\"MINUS\""));
	}

	@Test
	public void fail() throws Exception {
		ElementMinusInterpreter interpreter = new ElementMinusInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			assertEquals("class org.apache.jena.sparql.syntax.ElementMinus needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
