package sparql.app.common.interpreters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.modify.request.UpdateDeleteWhere;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Subgraph;
import sparql.app.dot.objects.DeleteSubgraph;
import sparql.app.dot.objects.EntityNode;

public class UpdateDeleteWhereInterpreter extends AbstractInterpreter implements Interpreter {

	public UpdateDeleteWhereInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != UpdateDeleteWhere.class) {
			throw new Exception(UpdateDeleteWhere.class+" needed as Object. Given: "+obj.getClass());
		}
		
		UpdateDeleteWhere update = (UpdateDeleteWhere) obj;
		List<Quad> quads = update.getQuads();

		Map<String, Subgraph> subgraphMap = new HashMap<>();

		// First get all possible graphs
		for(int i = 0; i < quads.size(); i++) {
			Quad quad = quads.get(i);
			String insertGraphKey = quad.getGraph().toString();
				
			if (!subgraphMap.containsKey(insertGraphKey)) {
				Subgraph namedSubgraph = new DeleteSubgraph("cluster_"+this.hashCode()+"_"+this.getUUID(insertGraphKey.hashCode()));
				if (quad.isDefaultGraph()) {
					namedSubgraph.setLabel("DELETE\\nWHERE");	
				} else {
					namedSubgraph.setLabel("DELETE FROM\\nGRAPH "+this.resolveNodeName(quad.getGraph())+"\\nWHERE");
				}
				subgraphMap.put(insertGraphKey, namedSubgraph);	
			}
		}

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
			
			String insertGraphKey = quad.getGraph().toString();
			Subgraph namedSubgraph = subgraphMap.get(insertGraphKey);
			
			namedSubgraph.addNode(fromNode);
			namedSubgraph.addNode(toNode);
			namedSubgraph.addEdge(edge);
		}
		
		for (Entry<String, Subgraph> entry: subgraphMap.entrySet()) {
			Subgraph subgraph = (Subgraph) entry.getValue();
			graph.addSubgraph(subgraph);
		}
	}

}
