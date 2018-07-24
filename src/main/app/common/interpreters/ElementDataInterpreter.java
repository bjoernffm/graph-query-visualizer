package main.app.common.interpreters;

import java.util.Iterator;
import java.util.List;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingHashMap;
import org.apache.jena.sparql.syntax.ElementData;

import main.app.common.DotVisualizer;
import main.app.dot.Edge;
import main.app.dot.Node;
import main.app.dot.objects.DataNode;
import main.app.dot.objects.EntityNode;

public class ElementDataInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception
	{
		if (obj.getClass() != ElementData.class) {
			throw new Exception(ElementData.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementData element = (ElementData) obj;
		List<Binding> rows = element.getRows();
		BindingHashMap ele = (BindingHashMap) rows.get(0);
		
		for ( Iterator<Var> iterator = ele.vars(); iterator.hasNext(); ) {
			Var var = iterator.next();

			Node dataNode = new DataNode(ele.get(var).toString());
			Node entityNode = new EntityNode("?"+var.getName());
			Edge edge = new Edge();
			edge.setFrom(dataNode);
			edge.setTo(entityNode);
			edge.setLabel("value");
			edge.setStyle("dotted");
			
			visualizer.getSubgraph().addNode(dataNode);
			visualizer.getSubgraph().addNode(entityNode);
			visualizer.getSubgraph().addEdge(edge);
		}
	}

}
