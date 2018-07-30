package main.app.common;

import org.apache.jena.query.Query;

import main.app.common.interpreters.QueryInterpreter;
import main.app.dot.Graph;
import main.app.dot.Subgraph;

final public class DotVisualizer extends QueryVisualizer implements QueryVisualizerInterface {
	
	Graph graph = new Graph("main");
	Subgraph subgraph = new Subgraph("cluster_1");

	public DotVisualizer(String query) {
		super(query);
	}

	public String visualize() throws Exception
	{
		(new QueryInterpreter()).interpret((Query) this.query, this);
		
		this.graph.addSubgraph(this.subgraph);
		
		System.out.println(this.graph);
		
		return "";
	}
	
	public Graph getGraph()
	{
		return this.graph;
	}
	
	public Subgraph getSubgraph()
	{
		return this.subgraph;
	}
}
