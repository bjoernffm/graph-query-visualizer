package main.app.dot.objects;

import java.io.UnsupportedEncodingException;

import main.app.dot.Node;

public class EntityNode extends Node {

	public EntityNode(String id) {
		super(id);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("circle");
	}

	public EntityNode(Node node) {
		super(node);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("circle");
	}

}
