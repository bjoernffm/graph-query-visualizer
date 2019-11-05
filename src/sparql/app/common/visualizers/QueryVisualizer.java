package sparql.app.common.visualizers;

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
	
	protected String escape(String string)
	{
		return string.replaceAll("\"", "");
	}
}
