package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.visualizers.DotVisualizer;

public class ProjectVarInterpreterTest {

	@Test
	public void test1() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> SELECT (SUM(?val) AS ?sum) (COUNT(?a) AS ?count) WHERE { ?a rdf:value ?val . } GROUP BY ?a");
		List<String> ret = sqv.visualize();
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", label=\"SUM(?val)\", labeltooltip=\"SUM(?val) AS ?sum\", color=\"black\"]"));
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> SELECT (\"HELLO\" AS ?la) (SUM(?val) AS ?sum) (COUNT(?a) AS ?count) WHERE { ?a rdf:value ?val . } GROUP BY ?a");
		List<String> ret = sqv.visualize();	
		assertTrue(ret.get(0).contains("[dottype=\"Edge\", nodetype=\"unknown\", label=\"AS\", labeltooltip=\"\\\"HELLO\\\" AS ?la\", color=\"black\"]"));
		assertTrue(ret.get(0).contains("[dottype=\"EntityNode\", nodetype=\"unknown\", label=\"\\\"HELLO\\\"\", tooltip=\"\\\"HELLO\\\"\", shape=\"circle\", fillcolor=\"aliceblue\", style=\"filled\"]"));
	}

	@Test
	public void test3() throws Exception {
		// TODO test projectExpressions.get(var) instanceof E_Str
	}
}
