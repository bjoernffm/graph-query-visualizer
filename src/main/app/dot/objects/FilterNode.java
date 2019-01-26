package main.app.dot.objects;

import main.app.dot.Node;

public class FilterNode extends Node {
	public FilterNode(String id) {
		super(id);

		this.setFillcolor("skyblue");
		this.setStyle("filled,dashed");
		this.setShape("box");
	}
}
