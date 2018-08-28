package main.app.dot;

import java.util.ArrayList;
import java.util.UUID;

public class Edge extends Object {
	protected Node from;
	protected Node to;
	protected String label;
	protected String style;
	protected String arrowhead;
	protected String lhead;
	
	public String getId()
	{
		return UUID.nameUUIDFromBytes(this.toDot().getBytes()).toString();
	}
	
	public Node getFrom() {
		return this.from;
	}
	
	public void setFrom(Node from) {
		this.from = from;
	}
	
	public Node getTo() {
		return this.to;
	}
	
	public void setTo(Node to) {
		this.to = to;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getArrowhead() {
		return this.label;
	}
	
	public void setArrowhead(String arrowhead) {
		this.arrowhead = arrowhead;
	}
	
	public String getLhead() {
		return this.lhead;
	}
	
	public void setLhead(String lhead) {
		this.lhead = lhead;
	}
	
	public String toDot()
	{
		ArrayList<String> argumentList = new ArrayList<>();

		if (this.label != null && !this.label.equals("")) {
			argumentList.add("label=\""+this.label+"\"");
		}
		if (this.style != null && !this.style.equals("")) {
			argumentList.add("style=\""+this.style+"\"");
		}
		if (this.arrowhead != null && !this.arrowhead.equals("")) {
			argumentList.add("arrowhead=\""+this.arrowhead+"\"");
		}
		if (this.lhead != null && !this.lhead.equals("")) {
			argumentList.add("lhead=\""+this.lhead+"\"");
		}
		
		return "\""+this.getFrom().getId()+"\" -> \""+this.getTo().getId()+"\" ["+String.join(", ", argumentList)+"]";
	}
	
	public String toString()
	{
		return this.toDot();
	}
}
