package main.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.sparql.syntax.ElementGroup;

public abstract class QueryVisualizer {	
	
	protected Map<String, Integer> objectMap = new HashMap<>();
	protected Integer currentObjectId = 0;
	protected List<String> statementList = new ArrayList<>();
	
	protected Integer getObjectId(String object)
	{
		if (this.objectMap.containsKey(object)) {
			return this.objectMap.get(object);
		} else {
			this.objectMap.put(object, this.currentObjectId);
			this.currentObjectId++;
			
			return this.objectMap.get(object); 
		}
	}
	
	protected String escape(String string)
	{
		return string.replaceAll("\"", "");
	}
	
	public ElementGroup getElementGroupByQuery(String string)
	{
		try {
			//Query query = QueryFactory.create("SELECT ?s { ?s <http://example.com/val> ?val . FILTER ( ?val < 20 ) }");
			Query query = QueryFactory.create(string);

			ElementGroup elGroup = (ElementGroup) query.getQueryPattern();
			
			return elGroup;
			
			/*List<Element> list = elGroup.getElements();
			
			for(int i = 0; i < list.size(); i++) {
				Element el = list.get(i);
				System.out.println(el);
				System.out.println(el.getClass());
			}

			/*List<ExprAggregator> list = query.getAggregators();
			System.out.println(list.size());
			for(int i = 0; i < list.size(); i++) {
				ExprAggregator exp = list.get(i);
				System.out.println(exp.toString());
			}*/
		} catch(QueryParseException e) {
			throw e;
		}
	}
}
