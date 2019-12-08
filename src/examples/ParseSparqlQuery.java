package examples;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;

public class ParseSparqlQuery {

	public static void main(String[] args) {
		String queryStr = "SELECT ?person WHERE { ?person <http://xmlns.com/foaf/0.1/name> \"Max Mustermann\" }";
		Query query = QueryFactory.create(queryStr);
		System.out.println(query.getQueryType() == Query.QueryTypeSelect);
		System.out.println(query.getProjectVars());
	}

}
