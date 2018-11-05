package main.app.common.interpreters;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementMinus;
import org.apache.jena.sparql.syntax.ElementOptional;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementUnion;

import main.app.dot.Graph;
import main.app.dot.Subgraph;

public class ElementGroupInterpreter extends AbstractInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception {
		if (obj.getClass() != ElementGroup.class) {
			throw new Exception(ElementGroup.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementGroup queryPattern = (ElementGroup) obj;

		for(int i = 0; i < queryPattern.size(); i++) {
			Element el = queryPattern.get(i);

			if (el instanceof ElementPathBlock) {
				(new ElementPathBlockInterpreter()).setOptional(this.getOptional()).interpret((ElementPathBlock) el, graph);
			} else if (el instanceof ElementFilter) {
				(new ElementFilterInterpreter()).setOptional(this.getOptional()).interpret((ElementFilter) el, graph);
			} else if (el instanceof ElementData) {
				(new ElementDataInterpreter()).setOptional(this.getOptional()).interpret((ElementData) el, graph);
			} else if (el instanceof ElementBind) {
				(new ElementBindInterpreter()).setOptional(this.getOptional()).interpret((ElementBind) el, graph);
			} else if (el instanceof ElementOptional) {
				(new ElementOptionalInterpreter()).interpret((ElementOptional) el, graph);
			} else if (el instanceof ElementSubQuery) {
				Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
				graph.addSubgraph(subgraph);
				(new QueryInterpreter()).interpret(((ElementSubQuery) el).getQuery(), subgraph);
			} else if (el instanceof ElementUnion) {
				(new ElementUnionInterpreter()).interpret((ElementUnion) el, graph);
			} else if (el instanceof ElementGroup) {
				Subgraph subgraph = new Subgraph("cluster_"+this.getUUID()+"_"+this.getUUID(el.hashCode()));
				graph.addSubgraph(subgraph);
				(new ElementGroupInterpreter()).interpret((ElementGroup) el, subgraph);
			} else if (el instanceof ElementMinus) {
				(new ElementMinusInterpreter()).interpret((ElementMinus) el, graph);
			} else {
				System.out.println(el.getClass());
				System.out.println(el+"\n");
				throw new Exception("Stopping here");
			}
		}
	}
}
