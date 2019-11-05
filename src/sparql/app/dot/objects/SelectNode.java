package sparql.app.dot.objects;

import sparql.app.dot.Node;

public class SelectNode extends Node {

	public SelectNode(String id) {
		super(id);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("doublecircle");
	}

}
