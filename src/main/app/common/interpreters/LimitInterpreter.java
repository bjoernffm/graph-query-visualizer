package main.app.common.interpreters;

import java.lang.Long;
import java.util.Map;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.AggregateNode;

public class LimitInterpreter extends AbstractInterpreter implements Interpreter {

	public LimitInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != Long.class) {
			throw new Exception(Long.class+" needed as Object. Given: "+obj.getClass());
		}
		
		long limit = (long) obj;
		
		Node limitNode = new AggregateNode("limit_"+this.hashCode());
		limitNode.setLabel("LIMIT "+limit);
		
		Map<String, Node> nodes = graph.getNodes();
		Node targetNode = (Node) nodes.values().toArray()[0];
		
		Edge edge = new Edge();
		edge.setArrowhead("none");
		edge.setFrom(limitNode);
		edge.setTo(targetNode);
		edge.setLhead("cluster_1");
		
		graph.addNode(limitNode);
		graph.addEdge(edge);
	}

}
