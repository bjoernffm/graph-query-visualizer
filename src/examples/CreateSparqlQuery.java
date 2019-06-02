package examples;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementTriplesBlock;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;

public class CreateSparqlQuery {

	public static void main(String[] args) {
		// Erstellung des Triples
		Var subject = Var.alloc("person");
		Node predicate = NodeFactory.createURI("http://xmlns.com/foaf/0.1/name");
		Node object = NodeFactory.createLiteral("Max Mustermann");
		Triple pattern = Triple.create(subject, predicate, object);
	   
		// Erstellen des WHERE Blocks
		ElementTriplesBlock block = new ElementTriplesBlock();
		block.addTriple(pattern);
		ElementGroup body = new ElementGroup();
		body.addElement(block);
		
		Query q = QueryFactory.make();
		q.setQueryPattern(body);  // Setzen des WHERE Blocks
		q.setQuerySelectType();   // Definition als SELECT Abfrage
		q.addResultVar("person"); // Definition des Resultats
	   
		System.out.println(q.toString()); // Ausgabe
	}

}
