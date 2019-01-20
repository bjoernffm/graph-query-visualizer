package main.app.common.interpreters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.graph.Node;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.modify.request.UpdateDataInsert;
import org.apache.jena.sparql.modify.request.UpdateModify;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementOptional;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Subgraph;
import main.app.dot.objects.DeleteSubgraph;
import main.app.dot.objects.EntityNode;
import main.app.dot.objects.FakeEdgeNode;
import main.app.dot.objects.InsertSubgraph;

public class UpdateModifyInterpreter extends AbstractInterpreter implements Interpreter {

	public UpdateModifyInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != UpdateModify.class) {
			throw new Exception(UpdateModify.class+" needed as Object. Given: "+obj.getClass());
		}
		
		UpdateModify update = (UpdateModify) obj;
		Map<String, Subgraph> subgraphMap = new HashMap<>();
		
		if (update.getWithIRI() != null) {
			graph.setLabel("WITH\\n"+this.resolveNodeName(update.getWithIRI()));
		}
		
		// First get all possible graphs
		List<Quad> insertQuads = update.getInsertQuads();
		
		for(int i = 0; i < insertQuads.size(); i++) {
			Quad quad = insertQuads.get(i);
			String graphKey = quad.getGraph().toString()+"_insert";
				
			if (!subgraphMap.containsKey(graphKey)) {
				Subgraph namedSubgraph = new InsertSubgraph("cluster_"+this.hashCode()+"_"+this.getUUID(graphKey.hashCode()));
				if (quad.isDefaultGraph()) {
					namedSubgraph.setLabel("INSERT");	
				} else {
					namedSubgraph.setLabel("INSERT INTO\\nGRAPH "+this.resolveNodeName(quad.getGraph()));
				}
				subgraphMap.put(graphKey, namedSubgraph);	
			}
		}

		for(int i = 0; i < insertQuads.size(); i++) {
			Quad quad = insertQuads.get(i);
			
			org.apache.jena.graph.Node subject = quad.getSubject();
			org.apache.jena.graph.Node predicate = quad.getPredicate();
			org.apache.jena.graph.Node object = quad.getObject();
			
			// Interpret the subject
			EntityNode fromNode = new EntityNode(this.resolveNodeName(subject));
			fromNode.setTooltip(subject.toString());
			if (!subject.isVariable()) {
				fromNode.setShape("box");
			}

			// Interpret the object
			EntityNode toNode = new EntityNode(this.resolveNodeName(object));			
			toNode.setTooltip(object.toString());
			if (!object.isVariable()) {
				toNode.setShape("box");
			}
			
			String graphKey = quad.getGraph().toString()+"_insert";
			Subgraph namedSubgraph = subgraphMap.get(graphKey);
			
			namedSubgraph.addNode(fromNode);
			namedSubgraph.addNode(toNode);
			
			// Interpret the path
			if (predicate.isVariable()) {
				FakeEdgeNode fakeNode = new FakeEdgeNode(this.resolveNodeName(predicate));
				namedSubgraph.addNode(fakeNode);
				
				Edge edge1 = new Edge();
				edge1.setArrowhead("none");
				edge1.setFrom(fromNode);
				edge1.setTo(fakeNode);
				
				Edge edge2 = new Edge();
				edge2.setFrom(fakeNode);
				edge2.setTo(toNode);
				
				namedSubgraph.addEdge(edge1);
				namedSubgraph.addEdge(edge2);
			} else {
				Edge edge = new Edge();
				edge.setFrom(fromNode);
				edge.setTo(toNode);
				edge.setLabel(this.resolveNodeName(predicate));
				edge.setLabeltooltip(predicate.toString());
				
				namedSubgraph.addEdge(edge);
			}
		}
		
		// First get all possible graphs
		List<Quad> deleteQuads = update.getDeleteQuads();
		
		for(int i = 0; i < deleteQuads.size(); i++) {
			Quad quad = deleteQuads.get(i);
			String graphKey = quad.getGraph().toString()+"_delete";
				
			if (!subgraphMap.containsKey(graphKey)) {
				Subgraph namedSubgraph = new DeleteSubgraph("cluster_"+this.hashCode()+"_"+this.getUUID(graphKey.hashCode()));
				if (quad.isDefaultGraph()) {
					namedSubgraph.setLabel("DELETE");	
				} else {
					namedSubgraph.setLabel("DELETE FROM\\nGRAPH "+this.resolveNodeName(quad.getGraph()));
				}
				subgraphMap.put(graphKey, namedSubgraph);	
			}
		}

		for(int i = 0; i < deleteQuads.size(); i++) {
			Quad quad = deleteQuads.get(i);
			
			org.apache.jena.graph.Node subject = quad.getSubject();
			org.apache.jena.graph.Node predicate = quad.getPredicate();
			org.apache.jena.graph.Node object = quad.getObject();
			
			// Interpret the subject
			EntityNode fromNode = new EntityNode(this.resolveNodeName(subject));
			fromNode.setTooltip(subject.toString());
			if (!subject.isVariable()) {
				fromNode.setShape("box");
			}

			// Interpret the object
			EntityNode toNode = new EntityNode(this.resolveNodeName(object));			
			toNode.setTooltip(object.toString());
			if (!object.isVariable()) {
				toNode.setShape("box");
			}
			
			String graphKey = quad.getGraph().toString()+"_delete";
			Subgraph namedSubgraph = subgraphMap.get(graphKey);
			
			namedSubgraph.addNode(fromNode);
			namedSubgraph.addNode(toNode);

			// Interpret the path
			if (predicate.isVariable()) {
				FakeEdgeNode fakeNode = new FakeEdgeNode(this.resolveNodeName(predicate));
				namedSubgraph.addNode(fakeNode);
				
				Edge edge1 = new Edge();
				edge1.setArrowhead("none");
				edge1.setFrom(fromNode);
				edge1.setTo(fakeNode);
				
				Edge edge2 = new Edge();
				edge2.setFrom(fakeNode);
				edge2.setTo(toNode);
				
				namedSubgraph.addEdge(edge1);
				namedSubgraph.addEdge(edge2);
			} else {
				Edge edge = new Edge();
				edge.setFrom(fromNode);
				edge.setTo(toNode);
				edge.setLabel(this.resolveNodeName(predicate));
				edge.setLabeltooltip(predicate.toString());
				
				namedSubgraph.addEdge(edge);
			}
		}
		
		for (Entry<String, Subgraph> entry: subgraphMap.entrySet()) {
			Subgraph subgraph = (Subgraph) entry.getValue();
			graph.addSubgraph(subgraph);
		}
		
		ArrayList<String> finalUsingList = new ArrayList<>();
		
		List<Node> usingList = update.getUsing();
		for(int i = 0; i < usingList.size(); i++) {
			Node node = usingList.get(i);
			finalUsingList.add("USING "+this.resolveNodeName(node));
		}
		
		List<Node> usingNamedList = update.getUsingNamed();
		for(int i = 0; i < usingNamedList.size(); i++) {
			Node node = usingNamedList.get(i);
			finalUsingList.add("USING NAMED "+this.resolveNodeName(node));
		}

		if (finalUsingList.isEmpty()) {
			(new ElementGroupInterpreter(this)).interpret((ElementGroup) update.getWherePattern(), graph);
		} else {
			Subgraph usingSubgraph = new Subgraph("cluster_"+this.hashCode()+"_using");
			usingSubgraph.setLabel(String.join(", ", finalUsingList));
			(new ElementGroupInterpreter(this)).interpret((ElementGroup) update.getWherePattern(), usingSubgraph);
			graph.addSubgraph(usingSubgraph);
		}
		
	}

}
