package main.app.dot;

abstract public class Object {
	protected String color = "black";
	protected String style;
	protected String fillcolor;
	protected String label = "";

	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		if (color != null) {
			this.color = color.trim();
		} else {
			this.color = null;
		}
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
		style = this.escape(style);
		this.style = style.trim();
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label.trim();
	}
	
	protected String escape(String string)
	{
		return string.replace("\"", "\\\"");
	}
}
