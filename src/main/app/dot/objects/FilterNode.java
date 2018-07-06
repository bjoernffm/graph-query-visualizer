package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class FilterNode extends Node {
	public FilterNode(String id) throws UnsupportedEncodingException {
		super(id);

		this.setFillcolor("skyblue");
		this.setStyle("filled,dashed");
		this.setShape("box");
	}
}
