package sparql.app.dot.objects;

import sparql.app.dot.Subgraph;

public class DeleteSubgraph extends Subgraph {

	public DeleteSubgraph(String id) {
		super(id);

		this.setStyle("solid,filled");
		this.setFillcolor("#ffcccc");
	}

}
