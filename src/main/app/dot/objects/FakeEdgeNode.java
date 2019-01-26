package main.app.dot.objects;

import main.app.dot.Node;

public class FakeEdgeNode extends Node {
	public FakeEdgeNode(String id) {
		super(id);

		this.setShape("plain");
		this.setStyle("none");
		this.drawSeparate();
	}
}
