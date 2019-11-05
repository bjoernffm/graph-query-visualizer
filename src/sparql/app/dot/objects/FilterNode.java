package sparql.app.dot.objects;

import sparql.app.dot.Node;

public class FilterNode extends Node {
	public FilterNode(String id) {
		super(id);

		this.setFillcolor("skyblue");
		this.setStyle("filled,dashed");
		this.setShape("box");
	}
}
