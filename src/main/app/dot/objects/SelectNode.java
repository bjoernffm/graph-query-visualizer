package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class SelectNode extends Node {

	public SelectNode(String id) throws UnsupportedEncodingException {
		super(id);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("doublecircle");
	}

}
