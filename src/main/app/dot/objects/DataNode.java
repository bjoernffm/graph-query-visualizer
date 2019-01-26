package main.app.dot.objects;

import main.app.dot.Node;

public class DataNode extends Node {
	public DataNode(String id) {
		super(id);

		this.setFillcolor("darkorchid1");
		this.setStyle("filled");
		this.setShape("box");
	}
}
