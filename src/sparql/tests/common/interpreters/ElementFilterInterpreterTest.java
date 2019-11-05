package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementFilterInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementFilterInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX a:      <http://www.w3.org/2000/10/annotation-ns#> PREFIX dc:     <http://purl.org/dc/elements/1.1/> PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#> SELECT ?annot WHERE { ?annot  a:annotates  <http://www.w3.org/TR/rdf-sparql-query/> . ?annot  dc:date      ?date . FILTER ( ?date > \"2005-01-01T00:00:00Z\"^^xsd:dateTime ) }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"FilterNode\", nodetype=\"unknown\", label=\"FILTER ( ?date > \\\"2005-01-01T00:00:00Z\\\"^^xsd:dateTime )\\l\", tooltip=\"FILTER ( ?date > \\\"2005-01-01T00:00:00Z\\\"^^xsd:dateTime )\\l\", shape=\"box\", fillcolor=\"skyblue\", style=\"filled,dashed\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", arrowhead=\"dot\", color=\"black\"]"));
	}

	@Test
	public void fail() throws Exception {
		ElementFilterInterpreter interpreter = new ElementFilterInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementFilter needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
