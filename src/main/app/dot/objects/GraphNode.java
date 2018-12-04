package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class GraphNode extends Node {

	public GraphNode(String id) throws UnsupportedEncodingException {
		super(id);

		this.setShape("point");
		this.setStyle("invis");
	}

}
