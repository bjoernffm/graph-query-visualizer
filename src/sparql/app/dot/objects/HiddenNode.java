package sparql.app.dot.objects;

import sparql.app.dot.Node;

public class HiddenNode extends Node {
	public HiddenNode(String id) {
		super(id);

		this.setStyle("invis");
	}
}
