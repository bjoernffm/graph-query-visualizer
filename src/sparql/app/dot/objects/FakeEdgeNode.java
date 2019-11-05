package sparql.app.dot.objects;

import sparql.app.dot.Node;

public class FakeEdgeNode extends Node {
	public FakeEdgeNode(String id) {
		super(id);

		this.setShape("plain");
		this.setStyle("none");
		this.drawSeparate();
	}
}
