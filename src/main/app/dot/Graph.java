package main.app.dot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Graph {
	protected Map<Integer, Node> nodeMap = new HashMap<>();
	protected Integer nodeCounter = 0;
	
	protected ArrayList<Edge> edgeList = new ArrayList<>();
	protected String type = "digraph";
	
	public void addNode(Node node)
	{
		node.setId(this.nodeCounter);
		this.nodeMap.put(this.nodeCounter, node);
		this.nodeCounter++;
	}
	
	public void addEdge(Edge edge)
	{
		this.edgeList.add(edge);
	}
	
	public String toDot()
	{
		String ret = this.type+" {\n";

		// Adding nodes
		for(int i = 0; i < this.nodeMap.size(); i++) {
			Node node = this.nodeMap.get(i);
			ret += "\t"+node.toDot()+"\n";
		}
		
		// Adding edges
		for ( Iterator<Edge> iterator = this.edgeList.iterator(); iterator.hasNext(); ) {
			Edge edge = iterator.next(); 
			ret += "\t"+edge.toDot()+"\n";
		}
		
		ret += "}";

		return ret;
	}
}
