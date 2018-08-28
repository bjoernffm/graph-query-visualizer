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
	protected String id;
	
	protected String compoundProperty = "true";
	protected String graphProperties = "fontsize=8 fontname=\"Arial\"";
	protected String nodeProperties = "fontsize=8 fontname=\"Arial\"";
	protected String edgeProperties = "fontsize=8 fontname=\"Arial\"";
	
	public Graph(String id)
	{
		this.setId(id);
	}
	
	public void addNode(Node node)
	{
		this.nodeMap.put(node.getId(), node);
	}
	
	public void removeNode(Node node)
	{
		// remove in class
		this.nodeMap.remove(node.getId());
		
		// remove in all subgraphs
		for (Entry<String, Subgraph> entry: this.subgraphMap.entrySet()) {
			Subgraph subgraph = (Subgraph) entry.getValue();
			subgraph.removeNode(node);
		}
	}
	
	public Map<String, Node> getNodes()
	{
		return this.nodeMap;
	}
	
	public void addSubgraph(Subgraph subgraph)
	{
		this.subgraphMap.put(subgraph.getId(), subgraph);
	}
	
	public void addEdge(Edge edge)
	{
		for(int i = 0; i < this.edgeList.size(); i++) {
			if (this.edgeList.get(i).getId().equals(edge.getId())) {
				return;
			}
		}
		
		this.edgeList.add(edge);
	}
	
	public ArrayList<Edge> getEdgesRecursive()
	{
		ArrayList<Edge> localEdgeList = this.edgeList;
		
		for (Entry<String, Subgraph> entry: this.subgraphMap.entrySet()) {
			Subgraph subgraph = entry.getValue();
			ArrayList<Edge> subgraphEdgeList = subgraph.getEdgesRecursive();
			localEdgeList.addAll(subgraphEdgeList);
		}
		
		return localEdgeList;
	}
	
	public void removeEdges()
	{
		this.edgeList = new ArrayList<>();
		
		for (Entry<String, Subgraph> entry: this.subgraphMap.entrySet()) {
			Subgraph subgraph = entry.getValue();
			subgraph.removeEdges();
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		id = this.escape(id);
		this.id = id.trim();
	}
	
	public String toDot()
	{
		String ret = this.type+" "+this.id+" {\n\n";

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
			Subgraph subgraph = entry.getValue();

			// make sure that all nodes that are contained in multiple graphs will be up in hierarchy
			for (Entry<String, Node> node: this.nodeMap.entrySet()) {
				subgraph.removeNode(node.getValue());
			}
			
			// add edges from subgraphs
			
			ArrayList<Edge> edges = subgraph.getEdgesRecursive();
			for(int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);
				this.addEdge(edge);
			}
			subgraph.removeEdges();
			
			String subgraphStr = subgraph.toDot();
			subgraphStr = subgraphStr.replaceAll("\t", "\t\t");
			subgraphStr = subgraphStr.replaceAll("}", "\t}");
			ret += "\t"+subgraphStr+"\n\n";
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
		
		ret = ret.replaceAll("\n\n", "\n");

		return ret;
	}
	
	public String toString()
	{
		return this.toDot();
	}
}
