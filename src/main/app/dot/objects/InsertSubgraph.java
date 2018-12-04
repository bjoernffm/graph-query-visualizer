package main.app.dot.objects;

import main.app.dot.Subgraph;

public class InsertSubgraph extends Subgraph {

	public InsertSubgraph(String id) {
		super(id);

		this.setStyle("solid,filled");
		this.setFillcolor("#ccffcc");
	}

}
