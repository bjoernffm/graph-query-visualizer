package sparql.app.common.interpreters;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementMinus;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.apache.jena.sparql.syntax.ElementOptional;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementSubQuery;
import org.apache.jena.sparql.syntax.ElementUnion;

import sparql.app.dot.Graph;
import sparql.app.dot.Subgraph;

public class ElementGroupInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementGroupInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception {
		if (obj.getClass() != ElementGroup.class) {
			throw new Exception(ElementGroup.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementGroup queryPattern = (ElementGroup) obj;

		for(int i = 0; i < queryPattern.size(); i++) {
			Element el = queryPattern.get(i);

			if (el instanceof ElementPathBlock) {
				(new ElementPathBlockInterpreter(this)).interpret((ElementPathBlock) el, graph);
			} else if (el instanceof ElementFilter) {
				(new ElementFilterInterpreter(this)).interpret((ElementFilter) el, graph);
			} else if (el instanceof ElementData) {
				(new ElementDataInterpreter(this)).interpret((ElementData) el, graph);
			} else if (el instanceof ElementBind) {
				(new ElementBindInterpreter(this)).interpret((ElementBind) el, graph);
			} else if (el instanceof ElementOptional) {
				(new ElementOptionalInterpreter(this)).interpret((ElementOptional) el, graph);
			} else if (el instanceof ElementSubQuery) {
				Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
				subgraph.setLabel("SUBSELECT");
				graph.addSubgraph(subgraph);
				(new QueryInterpreter(this)).interpret(((ElementSubQuery) el).getQuery(), subgraph);
			} else if (el instanceof ElementUnion) {
				(new ElementUnionInterpreter(this)).interpret((ElementUnion) el, graph);
			} else if (el instanceof ElementGroup) {
				Subgraph subgraph = new Subgraph("cluster_"+this.getUUID()+"_"+this.getUUID(el.hashCode()));
				graph.addSubgraph(subgraph);
				(new ElementGroupInterpreter(this)).interpret((ElementGroup) el, subgraph);
			} else if (el instanceof ElementMinus) {
				(new ElementMinusInterpreter(this)).interpret((ElementMinus) el, graph);
			} else if (el instanceof ElementNamedGraph) {
				(new ElementNamedGraphInterpreter(this)).interpret((ElementNamedGraph) el, graph);
			} else {
				throw new Exception("Stopping here: "+el.getClass().toString());
			}
		}
	}
}
