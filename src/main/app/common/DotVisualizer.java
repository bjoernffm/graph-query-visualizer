package main.app.common;

import java.util.Iterator;

import org.apache.jena.query.Query;
import org.apache.jena.sparql.modify.request.UpdateModify;
import org.apache.jena.update.Update;

import main.app.common.interpreters.QueryInterpreter;
import main.app.common.interpreters.UpdateInterpreter;
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
		this.graph.addSubgraph(this.subgraph);
		
		if (this.queryType.equals("select")) {
			(new QueryInterpreter()).interpret((Query) this.query, this.subgraph);
		} else {
			System.out.println();
			for ( Iterator<Update> i = this.update.iterator(); i.hasNext(); )
			{
				Update o = i.next();
				UpdateModify u = (UpdateModify) o;
				//System.out.println(u.);
			}
		}
		
		return this.graph.toString();
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
