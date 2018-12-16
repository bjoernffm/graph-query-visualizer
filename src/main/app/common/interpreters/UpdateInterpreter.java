package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.modify.request.QuadAcc;
import org.apache.jena.sparql.modify.request.UpdateDataDelete;
import org.apache.jena.sparql.modify.request.UpdateDataInsert;
import org.apache.jena.sparql.modify.request.UpdateDeleteInsert;
import org.apache.jena.sparql.modify.request.UpdateDeleteWhere;
import org.apache.jena.sparql.modify.request.UpdateModify;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.update.Update;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Graph;

public class UpdateInterpreter extends AbstractInterpreter implements Interpreter {

	public UpdateInterpreter(AbstractInterpreter interpreter)
	{
		super(interpreter);
	}
	
	public UpdateInterpreter(KnowledgeContainer knowledgeContainer)
	{
		super(null);
		this.knowledgeContainer = knowledgeContainer;
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (!(obj instanceof Update)) {
			throw new Exception(UpdateModify.class+" needed as Object. Given: "+obj.getClass());
		}
		
		if (obj instanceof UpdateDeleteWhere) {
			(new UpdateDeleteWhereInterpreter(this)).interpret((UpdateDeleteWhere) obj, graph);
		} else if (obj instanceof UpdateDataInsert) {
			(new UpdateDataInsertInterpreter(this)).interpret((UpdateDataInsert) obj, graph);
		} else if (obj instanceof UpdateDataDelete) {
			(new UpdateDataDeleteInterpreter(this)).interpret((UpdateDataDelete) obj, graph);
		} else if (obj instanceof UpdateModify) {
			(new UpdateModifyInterpreter(this)).interpret((UpdateModify) obj, graph);
		} else {
			System.out.println(obj.getClass());
			throw new Exception("Unknown type!");
		}
		
		

		/*QuadAcc insertAcc = query.getInsertAcc();
		(new InsertQuadAccInterpreter()).interpret(insertAcc, graph);

		QuadAcc deleteAcc = query;
		(new DeleteQuadAccInterpreter()).interpret(deleteAcc, graph);
		
		Element wherePattern = query.getWherePattern();
		(new QueryPatternInterpreter()).interpret(wherePattern, graph);*/
	}

}
