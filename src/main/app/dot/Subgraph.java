package main.app.dot;

public class Subgraph extends Graph {

	protected String id;
	
	public Subgraph(String id)
	{
		this.setId(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		id = this.escape(id);
		this.id = id.trim();
		this.type = "subgraph "+this.getId();
	}
}
