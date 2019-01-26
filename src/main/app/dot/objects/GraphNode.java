package main.app.dot.objects;

import main.app.dot.Node;

public class GraphNode extends Node {

	public GraphNode(String id) {
		super(id);

		this.setShape("point");
		this.setStyle("invis");
	}

}
