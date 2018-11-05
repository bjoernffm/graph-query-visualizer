package main.app.dot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

public class Node extends Object {
	protected String id;
	protected String label;
	protected String tooltip;
	protected String labeljust;
	protected String shape;
	protected String fillcolor;
	protected String style;
	protected Graph parentGraph;
	protected boolean optional;
	
	public Node(String id) throws UnsupportedEncodingException
	{
		this.setId(id);
		this.setLabel(id);
	}
	
	public Node(Node node)
	{
		this.id = node.id;
		this.label = node.label;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getUniqueId() {
		return this.id+"_"+this.parentGraph.getId();
	}
	
	public void setId(String id) throws UnsupportedEncodingException {
		byte[] bytes = id.getBytes("UTF-8");
		UUID uuid = UUID.nameUUIDFromBytes(bytes);
		
		this.id = uuid.toString().substring(0, 8);
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label.trim();
	}
	
	public String getTooltip() {
		return this.tooltip;
	}
	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip.trim();
	}

	public String getShape() {
		return this.shape;
	}

	public void setShape(String shape) {
		this.shape = shape.trim();
	}

	public String getFillcolor() {
		return this.fillcolor;
	}

	public void setFillcolor(String fillcolor) {
		this.fillcolor = fillcolor.trim();
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style.trim();
	}

	public Graph getParentGraph() {
		return parentGraph;
	}

	public void setParentGraph(Graph parentGraph) {
		this.parentGraph = parentGraph;
	}
	
	public void setOptional(boolean optional)
	{
		this.optional = optional;
	}
	
	public boolean getOptional()
	{
		return this.optional;
	}
	
	public String toDot()
	{
		if (this.getOptional() == true) {
			this.fillcolor = "antiquewhite";
			this.style = "filled,dashed";
		}
		
		ArrayList<String> argumentList = new ArrayList<>();

		if (this.label != null && !this.label.equals("")) {
			argumentList.add("label=\""+this.escape(this.label)+"\"");
		}
		if (this.tooltip != null && !this.tooltip.equals("")) {
			argumentList.add("tooltip=\""+this.escape(this.tooltip)+"\"");
		}
		if (this.shape != null && !this.shape.equals("")) {
			argumentList.add("shape=\""+this.shape+"\"");
		}
		if (this.fillcolor != null && !this.fillcolor.equals("")) {
			argumentList.add("fillcolor=\""+this.fillcolor+"\"");
		}
		if (this.style != null && !this.style.equals("")) {
			argumentList.add("style=\""+this.style+"\"");
		}

		return "\""+this.getUniqueId()+"\""+" ["+String.join(", ", argumentList)+"]";
	}
	
	public String toString()
	{
		return this.toDot();
	}
}
