package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementUnion;

import main.app.dot.Graph;
import main.app.dot.Subgraph;

public class ElementUnionInterpreter extends AbstractInterpreter implements Interpreter {
	
	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementUnion.class) {
			throw new Exception(ElementUnion.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementUnion element = (ElementUnion) obj;
		List<Element> elements = element.getElements();
		
		Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
		subgraph.setLabel("UNION");
		graph.addSubgraph(subgraph);
		for(int j = 0; j < elements.size(); j++) {
			Subgraph unionSubgraph = new Subgraph("cluster_"+this.hashCode()+"_"+j);
			unionSubgraph.setColor("#cccccc");
			unionSubgraph.setStyle("dashed");
			subgraph.addSubgraph(unionSubgraph);
			(new QueryPatternInterpreter()).interpret((Element) elements.get(j), unionSubgraph);
		}
	}
}
