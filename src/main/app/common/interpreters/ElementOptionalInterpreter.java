package main.app.common.interpreters;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementOptional;

import main.app.dot.Graph;
import main.app.dot.Subgraph;

public class ElementOptionalInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementOptionalInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementOptional.class) {
			throw new Exception(ElementOptional.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementOptional element = (ElementOptional) obj;
		Element optionalElement = element.getOptionalElement();

		if (optionalElement instanceof org.apache.jena.sparql.syntax.ElementGroup) {
			Subgraph subgraph = new Subgraph("cluster_"+this.hashCode());
			subgraph.setLabel("OPTIONAL");
			graph.addSubgraph(subgraph);
			(new ElementGroupInterpreter(this)).interpret((ElementGroup) optionalElement, subgraph);	
		} else {
			System.out.println(optionalElement.getClass());
			System.out.println(optionalElement+"\n");
			throw new Exception("Stopping here");
		}
	}

}
