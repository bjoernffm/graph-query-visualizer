package main.app.dot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import main.app.dot.objects.ClarifyEdge;
import main.app.dot.objects.ConllNode;
import main.app.dot.objects.GraphNode;
import main.app.misc.RecursiveNodeContainer;

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
	
	protected Graph parent = null;
	
	public Graph(String id)
	{
		this.setId(id);
		this.setStyle("solid");
	}
	
	public void addNode(Node node)
	{
		node.setParentGraph(this);
		if(node instanceof ConllNode && this.nodeMap.containsKey(node.getId()) && this.nodeMap.get(node.getId()) instanceof ConllNode) {
			ConllNode originalNode = (ConllNode) this.nodeMap.get(node.getId());
			ConllNode newNode = (ConllNode) node;
			
			newNode.migrate(originalNode);
			this.nodeMap.put(newNode.getId(), newNode);
		} else if(!(node instanceof ConllNode) && this.nodeMap.containsKey(node.getId()) && this.nodeMap.get(node.getId()) instanceof ConllNode) {
			// do nothing
		} else {
			this.nodeMap.put(node.getId(), node);
		}
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
		subgraph.setParent(this);
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
	
	public Map<String, ArrayList<RecursiveNodeContainer>> getNodesRecursive()
	{
		return getNodesRecursive(0);
	}
	
	public Map<String, ArrayList<RecursiveNodeContainer>> getNodesRecursive(int level)
	{
		Map<String, ArrayList<RecursiveNodeContainer>> localNodeList = new HashMap<>();
		
		// First get the nodes contained in this graph
		for (Entry<String, Node> entry: this.nodeMap.entrySet()) {
			Node node = entry.getValue();

			ArrayList<RecursiveNodeContainer> tmpNodeList;
			
			if (!localNodeList.containsKey(node.getId())) {
				tmpNodeList = new ArrayList<>();
			} else {
				tmpNodeList = localNodeList.get(node.getId());
			}
			
			RecursiveNodeContainer tmpContainer = new RecursiveNodeContainer(level, node);
			
			tmpNodeList.add(tmpContainer);
			localNodeList.put(node.getId(), tmpNodeList);
		}
		
		// Second check the subgraphs
		for (Entry<String, Subgraph> subgraphMapEntry: this.subgraphMap.entrySet()) {
			Subgraph subgraph = subgraphMapEntry.getValue();
			Map<String, ArrayList<RecursiveNodeContainer>> subgraphNodeList = subgraph.getNodesRecursive((level+1));

			// get all nodes from this subgraph
			for (Entry<String, ArrayList<RecursiveNodeContainer>> nodeMapEntry: subgraphNodeList.entrySet()) {
				String nodeId = nodeMapEntry.getKey();
				
				ArrayList<RecursiveNodeContainer> entryNodeList = nodeMapEntry.getValue();
				
				// add all node entries to the "big summary list"
				for(int i = 0; i < entryNodeList.size(); i++) {
					ArrayList<RecursiveNodeContainer> tmpNodeList;
					
					if (!localNodeList.containsKey(nodeId)) {
						tmpNodeList = new ArrayList<>();
					} else {
						tmpNodeList = localNodeList.get(nodeId);
					}

					tmpNodeList.add(entryNodeList.get(i));
					localNodeList.put(nodeId, tmpNodeList);	
				}
			}
		}
		
		return localNodeList;
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
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		label = this.escape(label);
		this.label = label.trim();
	}
	
	public Graph getParent() {
		return this.parent;
	}
	
	public void setParent(Graph graph) {
		this.parent = graph;
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
		if (!this.getLabel().isEmpty()) {
			ret += "\tlabel=\""+this.getLabel()+"\";\n";
		} else {
			ret += "\tlabel=\"\";\n";
		}
		if (!this.getStyle().isEmpty()) {
			ret += "\tstyle=\""+this.getStyle()+"\";\n";
		}
		if (this.getFillcolor() != null && !this.getFillcolor().equals("")) {
			ret += "\tfillcolor=\""+this.getFillcolor()+"\";\n";
		}
		if (!this.getColor().isEmpty()) {
			ret += "\tcolor=\""+this.getColor()+"\";\n";
		}
		if (!this.nodeProperties.isEmpty()) {
			ret += "\tnode ["+this.nodeProperties+"];\n";
		}
		if (!this.edgeProperties.isEmpty()) {
			ret += "\tedge ["+this.edgeProperties+"];\n";
		}
		ret += "\n";
		
		// not for subgraphs, only the top graph should draw the lines
		if (this.getClass() == Graph.class) {
			Map<String, ArrayList<RecursiveNodeContainer>> nodeMap = this.getNodesRecursive();
			for (Entry<String, ArrayList<RecursiveNodeContainer>> entry: nodeMap.entrySet()) {
				ArrayList<RecursiveNodeContainer> nodeList = entry.getValue();

				if (nodeList.size() == 2) {
					Edge edge = new ClarifyEdge();
					edge.setFrom(nodeList.get(0).getNode());
					edge.setTo(nodeList.get(1).getNode());
					edge.setNoConstraint();
					this.addEdge(edge);
					
					if (nodeList.get(1).getNode() instanceof GraphNode) {
						edge.setLhead(nodeList.get(1).getNode().getParentGraph().getId());
					}
				} else if (nodeList.size() > 2) {
					if (nodeList.get(0).getLevel() == 1) {
						for(int i = 1; i < nodeList.size(); i++) {
							Edge edge = new ClarifyEdge();
							edge.setFrom(nodeList.get(0).getNode());
							edge.setTo(nodeList.get(i).getNode());
							this.addEdge(edge);
							
							if (nodeList.get(i).getNode() instanceof GraphNode) {
								edge.setLhead(nodeList.get(i).getNode().getParentGraph().getId());
							}
						}
					} else {
						Node masterNode = new Node(nodeList.get(0).getNode());
						this.subgraphMap.get("cluster_1").addNode(masterNode);
						
						for(int i = 0; i < nodeList.size(); i++) {
							Edge edge = new ClarifyEdge();
							edge.setFrom(masterNode);
							edge.setTo(nodeList.get(i).getNode());
							this.addEdge(edge);
							
							if (nodeList.get(i).getNode() instanceof GraphNode) {
								edge.setLhead(nodeList.get(i).getNode().getParentGraph().getId());
							}
						}
					}
				}
			}
		}
		
		// Adding subgraphs
		for (Entry<String, Subgraph> entry: this.subgraphMap.entrySet()) {
			Subgraph subgraph = entry.getValue();
			
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
