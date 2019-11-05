package sparql.app.dot;

public class Subgraph extends Graph {

	protected String id;
	
	public Subgraph(String id)
	{
		super(id);
		
		this.compoundProperty = "";
		this.graphProperties = "";
		this.nodeProperties = "";
		this.edgeProperties = "";
		this.type = "subgraph";
	}
}
