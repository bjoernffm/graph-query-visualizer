package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementBindInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementBindInterpreterTest {

	@Test
	public void test() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?title ?price {  ?x ns:price ?p . ?x ns:discount ?discount BIND (?p*(1-?discount) AS ?price) ?x dc:title ?title . }");
		List<String> ret = sqv.visualize();

		assertTrue(ret.get(0).contains("[dottype=\"FakeEdgeNode\", nodetype=\"unknown\", label=\"BIND\", tooltip=\"BIND\", shape=\"plain\", style=\"none\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"FilterNode\", nodetype=\"unknown\", label=\"BIND (\\l    (\\l       ?p * ( 1 - ?discount ) \\l   ) AS ?price\\l)\\l\", tooltip=\"BIND (\\l    (\\l       ?p * ( 1 - ?discount ) \\l   ) AS ?price\\l)\\l\", shape=\"box\", fillcolor=\"skyblue\", style=\"filled,dashed\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", arrowhead=\"none\", color=\"black\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", style=\"dashed\", arrowhead=\"none\", color=\"black\"]"));
	}

	@Test
	public void fail() throws Exception {
		ElementBindInterpreter interpreter = new ElementBindInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementBind needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
