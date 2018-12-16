package main.app.common.visualizers;

import java.util.Iterator;

import org.apache.jena.query.Query;
import org.apache.jena.sparql.modify.request.UpdateModify;
import org.apache.jena.update.Update;

import main.app.common.interpreters.QueryInterpreter;
import main.app.common.interpreters.UpdateInterpreter;
import main.app.common.misc.KnowledgeContainer;
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
		KnowledgeContainer kc = new KnowledgeContainer();
		
		if (this.queryType.equals("select")) {
			kc.setPrefixMap(this.query.getPrefixMapping());
			
			(new QueryInterpreter(kc)).interpret((Query) this.query, this.subgraph);
		} else {
			kc.setPrefixMap(this.update.getPrefixMapping());
			
			for ( Iterator<Update> i = this.update.iterator(); i.hasNext(); )
			{				
				Update o = i.next();
				
				(new UpdateInterpreter(kc)).interpret(o, this.subgraph);
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