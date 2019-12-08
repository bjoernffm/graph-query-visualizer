package examples;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Node;

public class CreateGraph {

	public static void main(String[] args) {

		// Erstellen der Knoten
		Node node1 = new Node("John");
		Node node2 = new Node("Buch");

		// Verbinden der Knoten durch eine Kante
		Edge edge1 = new Edge();
		edge1.setFrom(node1);
		edge1.setTo(node2);
		edge1.setLabel("liest");

		// Einfügen der Elemente in den Graphen
		Graph graph = new Graph("main");
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addEdge(edge1);

		// An dieser Stelle kann jedes Element nach wie vor manipuliert werden
		// ...
		
		// Nur node1 (John) wird ausgegeben 
		System.out.println(node1.toDot());
		/**
		 * Ausgabe:
		 * 
		 * "61409aa1_main" [dottype="Node", nodetype="unknown", label="John", tooltip="John"]
		 */
		
		// Finaler DOT String wird ausgegeben
		System.out.println(graph.toDot());
		/**
		 * Ausgabe:
		 * 
		 * digraph main {
		 *     compound=true;
		 *     graph [fontsize=8 fontname="Arial"];
		 *     label="";
		 *     tooltip="";
		 *     style="solid";
		 *     color="black";
		 *     node [fontsize=8 fontname="Arial"];
		 *     edge [fontsize=8 fontname="Arial"];
		 *     "61409aa1_main" [dottype="Node", nodetype="unknown", label="John", tooltip="John"]
		 *     "57d0e82d_main" [dottype="Node", nodetype="unknown", label="Buch", tooltip="Buch"]
		 *     "61409aa1_main" -> "57d0e82d_main" [dottype="Edge", nodetype="unknown", label="liest", color="black"]
		 * }
		 */
	}

}
