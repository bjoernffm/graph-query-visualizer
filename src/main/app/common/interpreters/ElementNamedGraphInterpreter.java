package main.app.common.interpreters;

import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementNamedGraph;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.Subgraph;
import main.app.dot.objects.EntityNode;
import main.app.dot.objects.GraphNode;

public class ElementNamedGraphInterpreter extends AbstractInterpreter implements Interpreter {
	
	public ElementNamedGraphInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementNamedGraph.class) {
			throw new Exception(ElementNamedGraph.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementNamedGraph element = (ElementNamedGraph) obj;
		
		Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
		subgraph.setLabel("GRAPH "+this.resolveNodeName(element.getGraphNameNode()));
		graph.addSubgraph(subgraph);
		
		Node node = new GraphNode(this.resolveNodeName(element.getGraphNameNode()));
		subgraph.addNode(node);
		
		(new ElementGroupInterpreter(this)).interpret((ElementGroup) element.getElement(), subgraph);
	}
}
