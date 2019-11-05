package sparql.app.misc;

import sparql.app.dot.Node;

public class RecursiveNodeContainer {
	int level;
	Node node;
	
	public RecursiveNodeContainer(int level, Node node) {
		this.level = level;
		this.node = node;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
}
