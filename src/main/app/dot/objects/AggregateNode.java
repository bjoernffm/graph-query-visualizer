package main.app.dot.objects;

import main.app.dot.Node;

public class AggregateNode extends Node {
	public AggregateNode(String id) {
		super(id);

		this.setFillcolor("greenyellow");
		this.setStyle("filled");
		this.setShape("box");
	}
}
