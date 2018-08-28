package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.syntax.Element;

import main.app.dot.Graph;

public class QueryInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != Query.class) {
			throw new Exception(Query.class+" needed as Object. Given: "+obj.getClass());
		}
		
		Query query = (Query) obj;
		
		Element queryPattern = query.getQueryPattern();
		(new QueryPatternInterpreter()).interpret(queryPattern, graph);
		
		/**
		 * adding nodes for "group by"
		 */
		VarExprList groupByVarExpressions = query.getGroupBy();
		if (groupByVarExpressions != null && !groupByVarExpressions.isEmpty()) {
			(new GroupByInterpreter()).interpret(groupByVarExpressions, graph);
		}
		
		/**
		 * adding nodes for "order by"
		 */
		List<SortCondition> sortConditions = query.getOrderBy();
		if (sortConditions != null && !sortConditions.isEmpty()) {
			(new OrderByInterpreter()).interpret(sortConditions, graph);
		}
		
		/**
		 * adding nodes for "limit"
		 */
		long limit = query.getLimit();
		if (limit >= 0) {
			(new LimitInterpreter()).interpret(limit, graph);
		}
		
		/**
		 * aggregate project- and mentioned-vars
		 */
		VarExprList project = query.getProject();
		if (project != null && !project.isEmpty()) {
			(new ProjectVarInterpreter()).interpret(project, graph);
		}
	}

}
