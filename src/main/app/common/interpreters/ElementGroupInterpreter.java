package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementOptional;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementUnion;

import main.app.dot.Graph;
import main.app.dot.Subgraph;

public class ElementGroupInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception {
		if (obj.getClass() != ElementGroup.class) {
			throw new Exception(ElementGroup.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementGroup queryPattern = (ElementGroup) obj;

		for(int i = 0; i < queryPattern.size(); i++) {
			Element el = queryPattern.get(i);
			if (el instanceof ElementPathBlock) {
				(new ElementPathBlockInterpreter()).interpret((ElementPathBlock) el, graph);
			} else if (el instanceof ElementFilter) {
				(new ElementFilterInterpreter()).interpret((ElementFilter) el, graph);
			} else if (el instanceof ElementData) {
				(new ElementDataInterpreter()).interpret((ElementData) el, graph);
			} else if (el instanceof ElementBind) {
				(new ElementBindInterpreter()).interpret((ElementBind) el, graph);
			} else if (el instanceof ElementOptional) {
				(new ElementOptionalInterpreter()).interpret((ElementOptional) el, graph);
			} else if (el instanceof ElementSubQuery) {
				Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
				graph.addSubgraph(subgraph);
				(new QueryInterpreter()).interpret(((ElementSubQuery) el).getQuery(), subgraph);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementUnion) {
				ElementUnion test = (ElementUnion) el;
				List<Element> elements = test.getElements();
				
				Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
				subgraph.setLabel("UNION");
				graph.addSubgraph(subgraph);
				for(int j = 0; j < elements.size(); j++) {
					(new QueryPatternInterpreter()).interpret((Element) elements.get(j), subgraph);
					//System.out.println(elements.get(i));
				}
				//throw new Exception("Stopping here");
			} else {
				System.out.println(el.getClass());
				System.out.println(el+"\n");
				throw new Exception("Stopping here");
			}
		}
	}

}
