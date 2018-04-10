package Common;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;

public abstract class QueryVisualizer {	
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
			System.out.println(e.getMessage());
			System.out.println(e.getColumn());
		}
		
		return null;
	}
}
