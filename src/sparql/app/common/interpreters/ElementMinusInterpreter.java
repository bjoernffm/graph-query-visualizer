package sparql.app.common.interpreters;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementMinus;

import sparql.app.dot.Graph;
import sparql.app.dot.Subgraph;

public class ElementMinusInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementMinusInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementMinus.class) {
			throw new Exception(ElementMinus.class+" needed as Object. Given: "+obj.getClass());
		}

		ElementMinus element = (ElementMinus) obj;
		Element minusElement = element.getMinusElement();

		if (minusElement instanceof ElementGroup) {
			Subgraph subgraph = new Subgraph("cluster_"+this.getUUID()+"_"+this.getUUID(minusElement.hashCode()));
			subgraph.setLabel("MINUS");
			graph.addSubgraph(subgraph);
			(new ElementGroupInterpreter(this)).interpret((ElementGroup) minusElement, subgraph);
		} else {
			System.out.println(minusElement.getClass());
			System.out.println(minusElement+"\n");
			throw new Exception("Stopping here");
		}
	}

}
