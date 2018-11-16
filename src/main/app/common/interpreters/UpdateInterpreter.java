package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.graph.Node;
import org.apache.jena.query.Query;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.modify.request.QuadAcc;
import org.apache.jena.sparql.modify.request.UpdateModify;
import org.apache.jena.sparql.syntax.Element;

import main.app.dot.Graph;

public class UpdateInterpreter extends AbstractInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != UpdateModify.class) {
			throw new Exception(UpdateModify.class+" needed as Object. Given: "+obj.getClass());
		}
		
		UpdateModify query = (UpdateModify) obj;
		
		/*QuadAcc deleteAcc = query.getDeleteAcc();
		Node deleteGraph = deleteAcc.getGraph();
		List<Quad> deleteQuads = deleteAcc.getQuads();
		Quad deleteQuad = deleteQuads.get(0);*/
		
		QuadAcc deleteAcc = query.getInsertAcc();
		Node deleteGraph = deleteAcc.getGraph();
		List<Quad> deleteQuads = deleteAcc.getQuads();
		//Quad deleteQuad = deleteQuads.get(0);
		System.out.println(deleteQuads);
		
		Element wherePattern = query.getWherePattern();
		(new QueryPatternInterpreter()).interpret(wherePattern, graph);
	}

}
