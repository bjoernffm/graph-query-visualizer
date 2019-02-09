package main.app.common.visualizers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.update.Update;

import main.app.common.interpreters.QueryInterpreter;
import main.app.common.interpreters.UpdateInterpreter;
import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Graph;
import main.app.dot.Subgraph;

final public class DotVisualizer extends QueryVisualizer implements QueryVisualizerInterface
{
	protected boolean enableClarificationEdges = true;
	protected int subgraphDepth = 100;
	
	public DotVisualizer(String query) {
		super(query);
	}

	public List<String> visualize() throws Exception
	{
		ArrayList<String> list = new ArrayList<String>();
		KnowledgeContainer kc = new KnowledgeContainer();
		
		if (this.queryType.equals("select")) {
			Graph graph = new Graph("main");
			graph.enableClarificationEdges(this.enableClarificationEdges);
			graph.setMaxSubgraphDepth(this.subgraphDepth);
			Subgraph subgraph = new Subgraph("cluster_1");
			graph.addSubgraph(subgraph);
			
			kc.setPrefixMap(this.query.getPrefixMapping());
			
			(new QueryInterpreter(kc)).interpret((Query) this.query, subgraph);
			list.add(graph.toString());
			
		} else {
			kc.setPrefixMap(this.update.getPrefixMapping());
			
			for ( Iterator<Update> i = this.update.iterator(); i.hasNext(); )
			{				
				Update o = i.next();

				Graph graph = new Graph("main");
				graph.enableClarificationEdges(this.enableClarificationEdges);
				graph.setMaxSubgraphDepth(this.subgraphDepth);
				Subgraph subgraph = new Subgraph("cluster_1");
				graph.addSubgraph(subgraph);
				
				(new UpdateInterpreter(kc)).interpret(o, subgraph);
				list.add(graph.toString());
			}
		}
		
		return list;
	}
	
	public void enableClarificationEdges()
	{
		this.enableClarificationEdges = true;
	}
	
	public void enableClarificationEdges(boolean enabled)
	{
		this.enableClarificationEdges = enabled;
	}
	
	public void disableClarificationEdges()
	{
		this.enableClarificationEdges = false;
	}
	
	public void setSubgraphDepth(int depth) throws Exception
	{
		if (depth > 1) {
			this.subgraphDepth = depth;
		} else {
			throw new Exception("Depth greater than 1 is required");
		}
	}
}