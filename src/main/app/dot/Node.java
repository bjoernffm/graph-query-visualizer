package main.app.dot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

public class Node extends Object {
	protected String id;
	protected String tooltip;
	protected String labeljust;
	protected String shape;
	protected Graph parentGraph;
	protected boolean drawSeparate = false;
	protected String idSuffix = "";
	
	public Node(String id) throws UnsupportedEncodingException
	{
		this.setId(id);
		this.setLabel(id);
	}
	
	public Node(Node node)
	{
		this.id = node.getId();
		this.label = node.getLabel();
		this.tooltip = node.getTooltip();
		this.labeljust = node.getLabeljust();
		this.shape = node.getShape();
		this.fillcolor = node.getFillcolor();
		this.style = node.getStyle();
	}
	
	public void drawSeparate()
	{		
		this.idSuffix = String.valueOf(Math.random());
		byte[] bytes;
		try {
			bytes = this.idSuffix.getBytes("UTF-8");
			UUID uuid = UUID.nameUUIDFromBytes(bytes);
			this.idSuffix = uuid.toString().substring(0, 8);
		} catch (UnsupportedEncodingException e) {}

		this.drawSeparate = true;
	}
	
	public boolean isSeparate()
	{
		return this.drawSeparate;
	}
	
	public String getIdSuffix()
	{
		return this.idSuffix;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public String getUniqueId()
	{		
		if (this.parentGraph != null) {
			return this.id+"_"+this.parentGraph.getId()+this.idSuffix;
		} else {
			return this.id+this.idSuffix;
		}
	}
	
	public void setId(String id) throws UnsupportedEncodingException {
		byte[] bytes = id.getBytes("UTF-8");
		UUID uuid = UUID.nameUUIDFromBytes(bytes);
		
		this.id = uuid.toString().substring(0, 8);
	}
	
	public String getTooltip() {
		return this.tooltip;
	}
	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip.trim();
	}
	
	public String getLabeljust() {
		return this.labeljust;
	}
	
	public void setLabeljust(String labeljust) {
		this.labeljust = labeljust.trim();
	}

	public String getShape() {
		return this.shape;
	}

	public void setShape(String shape) {
		this.shape = shape.trim();
	}

	public Graph getParentGraph() {
		return parentGraph;
	}

	public void setParentGraph(Graph parentGraph) {
		this.parentGraph = parentGraph;
	}
	
	public String toDot()
	{		
		ArrayList<String> argumentList = new ArrayList<>();
		argumentList.add("dottype=\""+this.getClass().getSimpleName().toString()+"\"");
		argumentList.add("label=\""+this.escape(this.getLabel())+"\"");

		if (this.tooltip != null && !this.tooltip.equals("")) {
			argumentList.add("tooltip=\""+this.escape(this.tooltip)+"\"");
		}
		if (this.shape != null && !this.shape.equals("")) {
			argumentList.add("shape=\""+this.shape+"\"");
		}
		if (this.fillcolor != null && !this.fillcolor.equals("")) {
			argumentList.add("fillcolor=\""+this.fillcolor+"\"");
		}
		if (this.getStyle() != null && !this.getStyle().equals("")) {
			argumentList.add("style=\""+this.getStyle()+"\"");
		}

		return "\""+this.getUniqueId()+"\""+" ["+String.join(", ", argumentList)+"]";
	}
	
	public String toString()
	{
		return this.toDot();
	}
}
