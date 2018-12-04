package main.app.dot.objects;

import main.app.dot.Subgraph;

public class DeleteSubgraph extends Subgraph {

	public DeleteSubgraph(String id) {
		super(id);

		this.setStyle("solid,filled");
		this.setFillcolor("#ffcccc");
	}

}
