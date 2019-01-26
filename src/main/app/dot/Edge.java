package main.app.dot;

import java.util.ArrayList;
import java.util.UUID;

public class Edge extends Object {
	protected Node from;
	protected Node to;
	protected String label;
	protected String labeltooltip;
	protected String dir;
	protected String arrowhead;
	protected int penwidth = 1;
	protected String lhead;
	protected Boolean constraint = true;

	public String getId() {
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

	public String getLabeltooltip() {
		return this.labeltooltip;
	}

	public void setLabeltooltip(String labeltooltip) {
		this.labeltooltip = labeltooltip;
	}

	public String getDirection() {
		return this.dir;
	}

	public void setDirection(String dir) {
		this.dir = dir;
	}

	public String getArrowhead() {
		return this.arrowhead;
	}

	public void setArrowhead(String arrowhead) {
		this.arrowhead = arrowhead;
	}

	public int getPenwidth() {
		return this.penwidth;
	}

	public void setPenwidth(int penwidth) {
		this.penwidth = penwidth;
	}

	public String getLhead() {
		return this.lhead;
	}

	public void setLhead(String lhead) {
		this.lhead = lhead;
	}

	public void setNoConstraint() {
		this.constraint = false;
	}

	public String toDot() {
		ArrayList<String> argumentList = new ArrayList<>();
		argumentList.add("dottype=\"" + this.getClass().getSimpleName().toString() + "\"");

		if (this.label != null && !this.label.equals("")) {
			argumentList.add("label=\"" + this.escape(this.label) + "\"");
		}
		if (this.labeltooltip != null && !this.labeltooltip.equals("")) {
			argumentList.add("labeltooltip=\"" + this.escape(this.labeltooltip) + "\"");
		}
		if (this.style != null && !this.style.equals("")) {
			argumentList.add("style=\"" + this.style + "\"");
		}
		if (this.dir != null && !this.dir.equals("")) {
			argumentList.add("dir=\"" + this.dir + "\"");
		}
		if (this.arrowhead != null && !this.arrowhead.equals("")) {
			argumentList.add("arrowhead=\"" + this.arrowhead + "\"");
		}
		if (this.color != null && !this.color.equals("")) {
			argumentList.add("color=\"" + this.color + "\"");
		}
		if (this.penwidth != 1) {
			argumentList.add("penwidth=" + this.penwidth);
		}
		if (this.lhead != null && !this.lhead.equals("")) {
			argumentList.add("lhead=\"" + this.lhead + "\"");
		}
		if (this.constraint == false) {
			argumentList.add("constraint=" + this.constraint.toString());
		}

		return "\"" + this.getFrom().getUniqueId() + "\" -> \"" + this.getTo().getUniqueId() + "\" ["
				+ String.join(", ", argumentList) + "]";
	}

	public String toString() {
		return this.toDot();
	}
}
