package main.app.dot.objects;

import main.app.dot.Edge;

public class ClarifyEdge extends Edge {
	public ClarifyEdge() {
		super();

		this.setStyle("dotted");
		this.setDirection("none");
		this.setNoConstraint();
	}
}
