package main.app.dot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Graph {
	protected Map<String, Node> nodeMap = new HashMap<>();
	
	protected ArrayList<Edge> edgeList = new ArrayList<>();
	protected String type = "digraph";
	
	public void addNode(Node node)
	{
		this.nodeMap.put(node.getId(), node);
	}
	
	public void addEdge(Edge edge)
	{
		this.edgeList.add(edge);
	}
	
	public String toDot()
	{
		String ret = this.type+" {\n";

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
