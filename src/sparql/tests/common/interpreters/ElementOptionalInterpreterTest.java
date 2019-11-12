package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementOptionalInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementOptionalInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX mo: <http://purl.org/ontology/mo/> PREFIX foaf:  <http://xmlns.com/foaf/0.1/> SELECT ?name ?img WHERE { ?a a mo:MusicArtist ; foaf:name ?name . OPTIONAL { ?a foaf:img ?img } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"OPTIONAL\";"));
		assertTrue(ret.get(0).contains("tooltip=\"OPTIONAL\";"));		
	}

	@Test
	public void fail() throws Exception {
		ElementOptionalInterpreter interpreter = new ElementOptionalInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementOptional needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
