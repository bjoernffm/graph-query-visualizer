package main.app.dot;

import java.util.ArrayList;

public class Edge {
	protected Node from;
	protected Node to;
	protected String label;
	
	public Node getFrom() {
		return from;
	}
	
	public void setFrom(Node from) {
		this.from = from;
	}
	
	public Node getTo() {
		return to;
	}
	
	public void setTo(Node to) {
		this.to = to;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toDot()
	{
		ArrayList<String> argumentList = new ArrayList<>();

		if (this.label != null && !this.label.equals("")) {
			argumentList.add("label=\""+this.label+"\"");
		}
		
		return this.getFrom().getId()+" -> "+this.getTo().getId()+" ["+String.join(", ", argumentList)+"]";
	}
}
