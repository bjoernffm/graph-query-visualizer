package sparql.tests.dot.interpreters;

import static org.junit.Assert.*;

import org.junit.Test;

import sparql.app.dot.utils.Interpreter;

public class InterpreterTest {

	@Test
	public void test() {
		Interpreter in = new Interpreter("digraph main { compound=true; graph [fontsize=8 fontname=\"Arial\"]; label=\"\"; tooltip=\"\"; style=\"solid\"; color=\"black\"; node [fontsize=8 fontname=\"Arial\"]; edge [fontsize=8 fontname=\"Arial\"]; subgraph cluster_1 { label=\"\"; tooltip=\"\"; style=\"solid\"; color=\"black\"; \"be7b15fa_cluster_1\" [dottype=\"SelectNode\", nodetype=\"variable\", label=\"?age\", tooltip=\"?age\", shape=\"doublecircle\", fillcolor=\"aliceblue\", style=\"filled\"] \"1ad67058_cluster_1\" [dottype=\"EntityNode\", nodetype=\"literal\", label=\"\\\"Max\\\"\", tooltip=\"\\\"Max\\\"\", shape=\"box\", fillcolor=\"aliceblue\", style=\"filled\"] \"242b78ab_cluster_1\" [dottype=\"EntityNode\", nodetype=\"variable\", label=\"?person\", tooltip=\"?person\", shape=\"circle\", fillcolor=\"aliceblue\", style=\"filled\"] } \"242b78ab_cluster_1\" -> \"1ad67058_cluster_1\" [dottype=\"Edge\", nodetype=\"uri\", label=\"foaf:name\", labeltooltip=\"http://xmlns.com/foaf/0.1/name\", color=\"black\"] \"242b78ab_cluster_1\" -> \"be7b15fa_cluster_1\" [dottype=\"Edge\", nodetype=\"uri\", label=\"foaf:age\", labeltooltip=\"http://xmlns.com/foaf/0.1/age\", color=\"black\"] }");
		in.toSparql();
		assertTrue(true);
	}

}
