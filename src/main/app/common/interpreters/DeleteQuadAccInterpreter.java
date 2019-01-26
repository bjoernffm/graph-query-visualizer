package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.modify.request.QuadAcc;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Subgraph;
import main.app.dot.objects.EntityNode;

public class DeleteQuadAccInterpreter extends AbstractInterpreter implements Interpreter {

	public DeleteQuadAccInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != QuadAcc.class) {
			throw new Exception(QuadAcc.class+" needed as Object. Given: "+obj.getClass());
		}
		
		QuadAcc quadAcc = (QuadAcc) obj;
		List<Quad> quads = quadAcc.getQuads();
		
		for(int i = 0; i < quads.size(); i++) {
			Quad quad = quads.get(i);
			
			org.apache.jena.graph.Node subject = quad.getSubject();
			org.apache.jena.graph.Node predicate = quad.getPredicate();
			org.apache.jena.graph.Node object = quad.getObject();
			
			// Interpret the subject
			EntityNode fromNode = new EntityNode(this.resolveNodeName(subject));
			fromNode.setNodeType(subject);
			fromNode.setTooltip(subject.toString());
			if (!subject.isVariable()) {
				fromNode.setShape("box");
			}

			// Interpret the object
			EntityNode toNode = new EntityNode(this.resolveNodeName(object));
			toNode.setNodeType(object);			
			toNode.setTooltip(object.toString());
			if (!object.isVariable()) {
				toNode.setShape("box");
			}
			
			// Interpret the path
			Edge edge = new Edge();
			edge.setFrom(fromNode);
			edge.setTo(toNode);
			edge.setNodeType(predicate);
			edge.setLabel(this.resolveNodeName(predicate));
			edge.setLabeltooltip(predicate.toString());
			
			Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
			
			if (quad.isDefaultGraph()) {
				subgraph.setLabel("DELETE");			
			} else {
				subgraph.setLabel("DELETE INTO\nGRAPH "+this.resolveNodeName(quad.getGraph()));
			}
			
			graph.addSubgraph(subgraph);
			
			subgraph.addNode(fromNode);
			subgraph.addNode(toNode);
			subgraph.addEdge(edge);
		}
	}

}
