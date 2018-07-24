package main.app.common.interpreters;

import java.lang.Long;
import java.util.Map;

import main.app.common.DotVisualizer;
import main.app.dot.Edge;
import main.app.dot.Node;
import main.app.dot.objects.AggregateNode;

public class LimitInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception
	{
		if (obj.getClass() != Long.class) {
			throw new Exception(Long.class+" needed as Object. Given: "+obj.getClass());
		}
		
		long limit = (long) obj;
		Node limitNode = new AggregateNode("LIMIT "+limit);
		
		Map<String, Node> nodes = visualizer.getSubgraph().getNodes();
		Node targetNode = (Node) nodes.values().toArray()[0];
		
		Edge edge = new Edge();
		edge.setArrowhead("none");
		edge.setFrom(limitNode);
		edge.setTo(targetNode);
		edge.setLhead("cluster_1");
		visualizer.getGraph().addEdge(edge);
		
		visualizer.getGraph().addNode(limitNode);
	}

}
