package sparql.app.common.interpreters;

import java.util.Iterator;
import java.util.List;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingHashMap;
import org.apache.jena.sparql.syntax.ElementData;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Node;
import sparql.app.dot.objects.DataNode;
import sparql.app.dot.objects.EntityNode;

public class ElementDataInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementDataInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementData.class) {
			throw new Exception(ElementData.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementData element = (ElementData) obj;
		List<Binding> rows = element.getRows();
		for(int i = 0; i < rows.size(); i++) {
			BindingHashMap ele = (BindingHashMap) rows.get(i);
			
			for ( Iterator<Var> iterator = ele.vars(); iterator.hasNext(); ) {
				Var var = iterator.next();

				Node dataNode = new DataNode(this.resolveNodeName(ele.get(var)));
				dataNode.setNodeType(ele.get(var));
				dataNode.setTooltip(ele.get(var).toString());
				
				Node entityNode = new EntityNode(this.resolveNodeName(var));
				entityNode.setNodeType(var);
				entityNode.setTooltip(var.toString());
				
				Edge edge = new Edge();
				edge.setFrom(dataNode);
				edge.setTo(entityNode);
				edge.setLabel("set");
				edge.setLabeltooltip("set");
				edge.setStyle("dotted");
				
				graph.addNode(dataNode);
				graph.addNode(entityNode);
				graph.addEdge(edge);
			}
		}
	}

}
