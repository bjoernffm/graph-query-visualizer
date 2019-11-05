package sparql.tests.common;

import static org.junit.Assert.*;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.E_LessThan;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprVar;
import org.apache.jena.sparql.expr.nodevalue.NodeValueInteger;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementTriplesBlock;
import org.junit.Test;

public class ArqTest {

	@Test
	public void test() {
		Node subject = NodeFactory.createURI("http://www.bjoern.de/index.html#lala");
		Node predicate = NodeFactory.createLiteral("hase");
		Node object = Var.alloc("question");
		//Triple pattern = Triple.create(Var.alloc("s"), Var.alloc("p"), Var.alloc("o"));
		Triple pattern = Triple.create(subject, predicate, object);
		Expr e = new E_LessThan(new ExprVar("s"), new NodeValueInteger(20));
		
		ElementTriplesBlock block = new ElementTriplesBlock(); // Make a BGP
		block.addTriple(pattern);                              // Add our pattern match
		ElementFilter filter = new ElementFilter(e);           // Make a filter matching the expression
		ElementGroup body = new ElementGroup();                // Group our pattern match and filter
		body.addElement(block);
		body.addElement(filter);

		Query q = QueryFactory.make();
		q.setQueryPattern(body);                               // Set the body of the query to our group
		q.setQuerySelectType();                                // Make it a select query
		q.addResultVar("s");                                   // Select ?s
		
		System.out.println(q);
		fail("Not yet implemented");
	}

}
