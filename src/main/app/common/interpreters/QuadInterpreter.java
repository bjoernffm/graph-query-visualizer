package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.AggregateNode;
import main.app.dot.objects.EntityNode;

public class QuadInterpreter extends AbstractInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != Quad.class) {
			throw new Exception(Quad.class+" needed as Object. Given: "+obj.getClass());
		}
		
		Quad quad = (Quad) obj;
		
	}

}
