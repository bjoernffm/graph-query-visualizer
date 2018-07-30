package main.app.common.interpreters;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementOptional;

import main.app.common.DotVisualizer;

public class ElementOptionalInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception
	{
		if (obj.getClass() != ElementOptional.class) {
			throw new Exception(ElementOptional.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementOptional element = (ElementOptional) obj;
		Element optionalElement = element.getOptionalElement();

		if (optionalElement instanceof org.apache.jena.sparql.syntax.ElementGroup) {
			(new ElementGroupInterpreter()).interpret((ElementGroup) optionalElement, visualizer);	
		} else {
			System.out.println(optionalElement.getClass());
			System.out.println(optionalElement+"\n");
			throw new Exception("Stopping here");
		}
	}

}
