package main.app.dot.objects;

import main.app.dot.Node;

public class HiddenNode extends Node {
	public HiddenNode(String id) {
		super(id);

		this.setStyle("invis");
	}
}
