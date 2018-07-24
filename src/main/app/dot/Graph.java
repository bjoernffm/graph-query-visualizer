package main.app.dot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Graph extends Object {
	protected Map<String, Node> nodeMap = new HashMap<>();
	protected Map<String, Subgraph> subgraphMap = new HashMap<>();
	
	protected ArrayList<Edge> edgeList = new ArrayList<>();
	protected String type = "digraph";
	
	protected String compoundProperty = "true";
	protected String graphProperties = "fontsize=8 fontname=\"Arial\"";
	protected String nodeProperties = "fontsize=8 fontname=\"Arial\"";
	protected String edgeProperties = "fontsize=8 fontname=\"Arial\"";
	
	public void addNode(Node node)
	{
		this.nodeMap.put(node.getId(), node);
	}
	
	public void addSubgraph(Subgraph subgraph)
	{
		this.subgraphMap.put(subgraph.getId(), subgraph);
	}
	
	public void addEdge(Edge edge)
	{
		this.edgeList.add(edge);
	}
	
	public String toDot()
	{
		String ret = this.type+" {\n\n";

		if (!this.compoundProperty.isEmpty()) {
			ret += "\tcompound="+this.compoundProperty+";\n";
		}
		if (!this.graphProperties.isEmpty()) {
			ret += "\tgraph ["+this.graphProperties+"];\n";
		}
		if (!this.nodeProperties.isEmpty()) {
			ret += "\tnode ["+this.nodeProperties+"];\n";
		}
		if (!this.edgeProperties.isEmpty()) {
			ret += "\tedge ["+this.edgeProperties+"];\n";
		}
		ret += "\n";

		// Adding subgraphs
		for (Entry<String, Subgraph> entry: this.subgraphMap.entrySet()) {
			ret += "\t"+entry.getValue().toDot()+"\n";
		}

		// Adding nodes
		for (Entry<String, Node> entry: this.nodeMap.entrySet()) {
			ret += "\t"+entry.getValue().toDot()+"\n";
		}
		
		// Adding edges
		for ( Iterator<Edge> iterator = this.edgeList.iterator(); iterator.hasNext(); ) {
			Edge edge = iterator.next(); 
			ret += "\t"+edge.toDot()+"\n";
		}
		
		ret += "}";

		return ret;
	}
	
	public String toString()
	{
		return this.toDot();
	}
}
