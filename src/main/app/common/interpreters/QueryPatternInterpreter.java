package main.app.common.interpreters;

import org.apache.jena.query.Query;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementSubQuery;

import main.app.common.DotVisualizer;

public class QueryPatternInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception
	{
		Element queryPattern = (Element) obj;
		
		if (queryPattern instanceof org.apache.jena.sparql.syntax.ElementGroup) {
			(new ElementGroupInterpreter()).interpret((ElementGroup) queryPattern, visualizer);
		} else if (queryPattern instanceof org.apache.jena.sparql.syntax.ElementSubQuery) {
			ElementSubQuery el = (ElementSubQuery) queryPattern;
			Query el2 = el.getQuery();
			(new QueryInterpreter()).interpret((Query) el2, visualizer);
		} else {
			System.out.println(queryPattern.getClass());
			System.out.println(queryPattern+"\n");
			throw new Exception("Stopping here");
		}
	}

}
