package main.app.dot;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Node {
	protected String id;
	protected String label;
	protected String shape;
	protected String fillcolor;
	protected String style;
	
	public Node(String id) throws UnsupportedEncodingException
	{
		this.setId(id);
		this.setLabel(id);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) throws UnsupportedEncodingException {
		byte[] bytes = id.getBytes("UTF-8");
		UUID uuid = UUID.nameUUIDFromBytes(bytes);
		
		this.id = uuid.toString();
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label.trim();
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape.trim();
	}

	public String getFillcolor() {
		return fillcolor;
	}

	public void setFillcolor(String fillcolor) {
		this.fillcolor = fillcolor.trim();
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style.trim();
	}
	
	public String toDot()
	{
		ArrayList<String> argumentList = new ArrayList<>();

		if (this.label != null && !this.label.equals("")) {
			argumentList.add("label=\""+this.label+"\"");
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

		return "\""+this.id+"\""+" ["+String.join(", ", argumentList)+"]";
	}
	
	public String toString()
	{
		return this.toDot();
	}
}
