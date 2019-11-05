package sparql.app.dot.objects;

import sparql.app.dot.Node;

public class GraphNode extends Node {

	public GraphNode(String id) {
		super(id);

		this.setShape("point");
		this.setStyle("invis");
	}

}
