package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class FakeEdgeNode extends Node {
	public FakeEdgeNode(String id) throws UnsupportedEncodingException {
		super(id);

		this.setShape("plain");
		this.setStyle("none");
		this.drawSeparate();
	}
}
