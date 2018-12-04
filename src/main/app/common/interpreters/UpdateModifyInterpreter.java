package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.modify.request.QuadAcc;
import org.apache.jena.sparql.modify.request.UpdateDataInsert;
import org.apache.jena.sparql.modify.request.UpdateModify;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.Subgraph;
import main.app.dot.objects.AggregateNode;
import main.app.dot.objects.EntityNode;

public class UpdateModifyInterpreter extends AbstractInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != UpdateModify.class) {
			throw new Exception(UpdateModify.class+" needed as Object. Given: "+obj.getClass());
		}
		
		UpdateModify update = (UpdateModify) obj;
		System.out.println(update);
	}

}
