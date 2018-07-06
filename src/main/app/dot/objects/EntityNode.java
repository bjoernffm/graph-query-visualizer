package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class EntityNode extends Node {

	public EntityNode(String id) throws UnsupportedEncodingException {
		super(id);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("circle");
	}

}
