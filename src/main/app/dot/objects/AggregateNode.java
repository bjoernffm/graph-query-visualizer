package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class AggregateNode extends Node {
	public AggregateNode(String id) throws UnsupportedEncodingException {
		super(id);

		this.setFillcolor("greenyellow");
		this.setStyle("filled");
		this.setShape("box");
	}
}
