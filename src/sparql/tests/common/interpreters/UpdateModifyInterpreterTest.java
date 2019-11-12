package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.UpdateModifyInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class UpdateModifyInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc:  <http://purl.org/dc/elements/1.1/> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> DELETE { ?book ?p ?v } WHERE { ?book dc:date ?date . FILTER ( ?date > \"1970-01-01T00:00:00-02:00\"^^xsd:dateTime ) ?book ?p ?v }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"DELETE\";"));
		assertTrue(ret.get(0).contains("tooltip=\"DELETE\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ffcccc\";"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX dc:  <http://purl.org/dc/elements/1.1/> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> DELETE { \"Subject\" a \"Object\" } WHERE { ?book dc:date ?date . FILTER ( ?date > \"1970-01-01T00:00:00-02:00\"^^xsd:dateTime ) ?book ?p ?v }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"DELETE\";"));
		assertTrue(ret.get(0).contains("tooltip=\"DELETE\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ffcccc\";"));
	}

	@Test
	public void test3() throws Exception {
		DotVisualizer sqv = new DotVisualizer("INSERT { ?a ?b ?c } WHERE { { ?a ?b ?c FILTER(contains(str(?a),'http://purl.org/olia') && !contains(str(?a),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?b),'http://purl.org/olia') && !contains(str(?b),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?c),'http://purl.org/olia') && !contains(str(?c),'http://purl.org/olia/olia.owl')) } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"INSERT\";"));
		assertTrue(ret.get(0).contains("tooltip=\"INSERT\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ccffcc\";"));
	}

	@Test
	public void test4() throws Exception {
		DotVisualizer sqv = new DotVisualizer("INSERT { \"Subject\" a \"Object\" } WHERE { { ?a ?b ?c FILTER(contains(str(?a),'http://purl.org/olia') && !contains(str(?a),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?b),'http://purl.org/olia') && !contains(str(?b),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?c),'http://purl.org/olia') && !contains(str(?c),'http://purl.org/olia/olia.owl')) } }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"INSERT\";"));
		assertTrue(ret.get(0).contains("tooltip=\"INSERT\";"));
		assertTrue(ret.get(0).contains("fillcolor=\"#ccffcc\";"));
	}

	@Test
	public void test5() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX foaf:  <http://xmlns.com/foaf/0.1/> WITH <http://example/addresses> DELETE { ?person ?property ?value } WHERE { ?person ?property ?value ; foaf:givenName 'Fred' } ");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"WITH\\naddresses\";"));
		assertTrue(ret.get(0).contains("tooltip=\"WITH\\naddresses\";"));
	}

	@Test
	public void fail() throws Exception {
		UpdateModifyInterpreter interpreter = new UpdateModifyInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.modify.request.UpdateModify needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
