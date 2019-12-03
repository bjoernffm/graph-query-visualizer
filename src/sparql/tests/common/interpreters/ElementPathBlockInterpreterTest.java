package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.ElementPathBlockInterpreter;
import sparql.app.common.visualizers.DotVisualizer;
import sparql.app.dot.Graph;

public class ElementPathBlockInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> SELECT ?head { ?word conll:HEAD ?head. ?word conll:ID \"2\". ?word conll:POS \"VBP\". ?word conll:LEMMA \"be\" }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"ConllNode\", nodetype=\"variable\", label=\"?word\\n\\nconll:POS \\\"VBP\\\"\\lconll:LEMMA \\\"be\\\"\\lconll:ID \\\"2\\\"\\l\", tooltip=\"?word\\n\\nconll:POS \\\"VBP\\\"\\lconll:LEMMA \\\"be\\\"\\lconll:ID \\\"2\\\"\\l\", shape=\"octagon\", fillcolor=\"aliceblue\", style=\"filled\"]"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:title/dc:label ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:title / dc:label\", labeltooltip=\"<http://purl.org/dc/elements/1.1/title>/<http://purl.org/dc/elements/1.1/label>\""));
	}

	@Test
	public void test3() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:title|dc:label ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:title|dc:label\", labeltooltip=\"<http://purl.org/dc/elements/1.1/title>|<http://purl.org/dc/elements/1.1/label>\""));
	}

	@Test
	public void test4() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 ^dc:label ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"^dc:label\", labeltooltip=\"^<http://purl.org/dc/elements/1.1/label>\""));
	}

	@Test
	public void test5() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:label* ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:label*\", labeltooltip=\"(<http://purl.org/dc/elements/1.1/label>)*\""));
	}

	@Test
	public void test6() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:label+ ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:label+\", labeltooltip=\"(<http://purl.org/dc/elements/1.1/label>)+\""));
	}

	@Test
	public void test7() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:label? ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:label?\", labeltooltip=\"(<http://purl.org/dc/elements/1.1/label>)?\""));
	}

	@Test
	public void test8() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 !(dc:title|dc:label) ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"!(dc:title|dc:label)\", labeltooltip=\"!(<http://purl.org/dc/elements/1.1/title>|<http://purl.org/dc/elements/1.1/label>)\""));
	}

	@Test
	public void test9() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:label{5} ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:label{5}\", labeltooltip=\"(<http://purl.org/dc/elements/1.1/label>){5}\""));
	}

	@Test
	public void test10() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 dc:title{2,3} ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"dc:title{2,3}\", labeltooltip=\"(<http://purl.org/dc/elements/1.1/title>){2,3}\""));
	}

	@Test
	public void test11() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?displayString { ns:book1 !^dc:title ?displayString }");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("label=\"!(^dc:title)\", labeltooltip=\"!^<http://purl.org/dc/elements/1.1/title>\""));
	}

	@Test
	public void fail() throws Exception {
		ElementPathBlockInterpreter interpreter = new ElementPathBlockInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.syntax.ElementPathBlock needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
