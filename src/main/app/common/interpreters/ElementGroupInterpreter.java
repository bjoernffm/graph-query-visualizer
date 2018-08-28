package main.app.common.interpreters;

import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementOptional;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementUnion;

import main.app.common.DotVisualizer;

public class ElementGroupInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception {
		if (obj.getClass() != ElementGroup.class) {
			throw new Exception(ElementGroup.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementGroup queryPattern = (ElementGroup) obj;

		for(int i = 0; i < queryPattern.size(); i++) {
			Element el = queryPattern.get(i);
			if (el instanceof org.apache.jena.sparql.syntax.ElementPathBlock) {
				(new ElementPathBlockInterpreter()).interpret((ElementPathBlock) el, visualizer);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementFilter) {
				(new ElementFilterInterpreter()).interpret((ElementFilter) el, visualizer);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementData) {
				(new ElementDataInterpreter()).interpret((ElementData) el, visualizer);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementBind) {
				(new ElementBindInterpreter()).interpret((ElementBind) el, visualizer);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementOptional) {
				(new ElementOptionalInterpreter()).interpret((ElementOptional) el, visualizer);
			}  else if (el instanceof org.apache.jena.sparql.syntax.ElementUnion) {
				ElementUnion test = (ElementUnion) el;
				//System.out.println(test.getElements());
				System.out.println(el.getClass());
				throw new Exception("Stopping here");
			} else {
				System.out.println(el.getClass());
				System.out.println(el+"\n");
				throw new Exception("Stopping here");
			}
		}
	}

}
