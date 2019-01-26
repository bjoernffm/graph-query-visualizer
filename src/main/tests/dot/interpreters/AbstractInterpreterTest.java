package main.tests.dot.interpreters;

import static org.junit.Assert.*;

import org.junit.Test;

import main.app.dot.interpreters.Interpreter;

public class AbstractInterpreterTest {

	@Test
	public void test() {
		Interpreter in = new Interpreter("digraph main { compound=true; graph [fontsize=8 fontname=\"Arial\"]; label=\"\"; style=\"solid\"; color=\"black\"; node [fontsize=8 fontname=\"Arial\"]; edge [fontsize=8 fontname=\"Arial\"]; subgraph cluster_1 { label=\"\"; style=\"solid\"; color=\"black\"; \"e66568a2_cluster_1\" [dottype=\"SelectNode\", nodetype=\"variable\", label=\"?author\", tooltip=\"?author\", shape=\"doublecircle\", fillcolor=\"aliceblue\", style=\"filled\"] \"64d11271_cluster_1\" [dottype=\"EntityNode\", nodetype=\"uri\", label=\":book1\", tooltip=\"http://example.org/book/book1\", shape=\"box\", fillcolor=\"aliceblue\", style=\"filled\"] \"9fa081cf_cluster_1\" [dottype=\"EntityNode\", nodetype=\"variable\", label=\"?book\", tooltip=\"?book\", shape=\"circle\", fillcolor=\"aliceblue\", style=\"filled\"] \"eae44d3e_cluster_1\" [dottype=\"SelectNode\", nodetype=\"variable\", label=\"?title\", tooltip=\"?title\", shape=\"doublecircle\", fillcolor=\"aliceblue\", style=\"filled\"] } \"64d11271_cluster_1\" -> \"eae44d3e_cluster_1\" [dottype=\"Edge\", nodetype=\"uri\", label=\"dc:title\", labeltooltip=\"http://purl.org/dc/elements/1.1/title\", color=\"black\"] \"eae44d3e_cluster_1\" -> \"9fa081cf_cluster_1\" [dottype=\"Edge\", nodetype=\"uri\", label=\"dc:title\", labeltooltip=\"http://purl.org/dc/elements/1.1/title\", color=\"black\"] \"9fa081cf_cluster_1\" -> \"e66568a2_cluster_1\" [dottype=\"Edge\", nodetype=\"uri\", label=\"dc:author\", labeltooltip=\"http://purl.org/dc/elements/1.1/author\", color=\"black\"] }");
		in.toSparql();
		assertTrue(true);
	}

}
