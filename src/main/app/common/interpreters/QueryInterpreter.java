package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.syntax.Element;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Graph;

public class QueryInterpreter extends AbstractInterpreter implements Interpreter {

	public QueryInterpreter(AbstractInterpreter interpreter)
	{
		super(interpreter);
	}
	
	public QueryInterpreter(KnowledgeContainer knowledgeContainer)
	{
		super(null);
		this.knowledgeContainer = knowledgeContainer;
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != Query.class) {
			throw new Exception(Query.class+" needed as Object. Given: "+obj.getClass());
		}
		
		Query query = (Query) obj;
		
		Element queryPattern = query.getQueryPattern();
		(new QueryPatternInterpreter(this)).interpret(queryPattern, graph);
		
		/**
		 * adding nodes for "group by"
		 */
		VarExprList groupByVarExpressions = query.getGroupBy();
		if (groupByVarExpressions != null && !groupByVarExpressions.isEmpty()) {
			(new GroupByInterpreter(this)).interpret(groupByVarExpressions, graph);
		}
		
		/**
		 * adding nodes for "order by"
		 */
		List<SortCondition> sortConditions = query.getOrderBy();
		if (sortConditions != null && !sortConditions.isEmpty()) {
			(new OrderByInterpreter(this)).interpret(sortConditions, graph);
		}

		/**
		 * adding nodes for "having"
		 */
		List<Expr> havingExpressions = query.getHavingExprs();
		if (havingExpressions != null && !havingExpressions.isEmpty()) {
			(new HavingInterpreter(this)).interpret(havingExpressions, graph);
		}
		
		/**
		 * adding nodes for "limit"
		 */
		long limit = query.getLimit();
		if (limit >= 0) {
			(new LimitInterpreter(this)).interpret(limit, graph);
		}
		
		/**
		 * adding nodes for "offset"
		 */
		long offset = query.getOffset();
		if (offset >= 0) {
			(new OffsetInterpreter(this)).interpret(offset, graph);
		}
		
		/**
		 * aggregate project- and mentioned-vars
		 */
		VarExprList project = query.getProject();
		if (project != null && !project.isEmpty()) {
			(new ProjectVarInterpreter(this)).interpret(project, graph);
		}
	}

}
