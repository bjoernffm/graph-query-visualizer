package main.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.Syntax;
import org.apache.jena.sparql.syntax.ElementGroup;

public abstract class QueryVisualizer {	

	protected Query query;
	protected UpdateRequest update;
	protected String queryType;
	
	/**
	 * objectMap holds the objects and the related ids, vice versa
	 */
	protected Map<String, Integer> objectMap = new HashMap<>();
	
	/**
	 * currentObjectId is about the latest id, needed for increment
	 */
	protected Integer currentObjectId = 0;
	
	/**
	 * The statement list contains the actual dot statements
	 */
	protected List<String> statementList = new ArrayList<>();
	
	public QueryVisualizer(String query)
	{
		try {
			this.query = QueryFactory.create(query, Syntax.syntaxARQ);
			this.queryType = "select";
		} catch(QueryParseException queryException) {
			// check if possibly an update request has been entered
			if (
				queryException.getMessage().toLowerCase().contains("delete") ||
				queryException.getMessage().toLowerCase().contains("insert") ||
				queryException.getMessage().toLowerCase().contains("with")
			) {
				try {
					this.update = UpdateFactory.create(query);
					this.queryType = "update";
				} catch(QueryParseException updateException) {
					throw updateException;
				}
			} else {
				throw queryException;
			}
		}
	}
	
	/**
	 * Returns an id based on the given object string
	 * 
	 * @param object
	 * @return The id
	 */
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
		} catch(QueryParseException e) {
			throw e;
		}
	}
}
