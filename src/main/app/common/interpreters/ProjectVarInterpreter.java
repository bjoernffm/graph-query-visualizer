package main.app.common.interpreters;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.expr.E_Str;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprAggregator;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.aggregate.Aggregator;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.EntityNode;

public class ProjectVarInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != VarExprList.class) {
			throw new Exception(VarExprList.class+" needed as Object. Given: "+obj.getClass());
		}
		
		// get general list of vars and expressions
		VarExprList project = (VarExprList) obj;
		
		// get only the selected vars without expressions
		List<Var> projectVars = project.getVars();
		// list of all aggregation expressions
		Map<Var, Expr> projectExpressions = project.getExprs();
		
		// now iterate through all project vars and check for possible aggregations
		for(int i = 0; i < projectVars.size(); i++) {
			Var var = projectVars.get(i);

			Node entityNode = new EntityNode("?"+var.getName());
			entityNode.setShape("doublecircle");
			graph.addNode(entityNode);
			
			// check for possible aggregation expression
			if (projectExpressions.containsKey(var)) {
				// check for aggregators (used e.g. with group by)
				if (projectExpressions.get(var) instanceof ExprAggregator) {
					ExprAggregator projectExpression = (ExprAggregator) projectExpressions.get(var);
		
					Aggregator aggregator = projectExpression.getAggregator();
					ExprList expressionList = aggregator.getExprList();
					
					Set<Var> mentionedVars = expressionList.getVarsMentioned();
					for(Var mentionedVar: mentionedVars) {
						Node varNode = new EntityNode(mentionedVar.toString());
						graph.addNode(varNode);
						
						Edge edge = new Edge();
						edge.setLabel(aggregator.toString());
						edge.setFrom(varNode);
						edge.setTo(entityNode);
						graph.addEdge(edge);
					}
				} else if (projectExpressions.get(var) instanceof E_Str) {
					E_Str projectExpression = (E_Str) projectExpressions.get(var);
					//System.out.println(expression.ge);
					
					Set<Var> mentionedVars = projectExpression.getVarsMentioned();
					for(Var mentionedVar: mentionedVars) {
						Node varNode = new EntityNode(mentionedVar.toString());
						graph.addNode(varNode);
						
						Edge edge = new Edge();
						// TODO check for better expression to show in the graph -> (str ?link) looks stupid
						edge.setLabel(projectExpression.getFunction().toString());
						edge.setFrom(varNode);
						edge.setTo(entityNode);
						graph.addEdge(edge);
					}
				} else {
					System.out.println(projectExpressions.get(var));
					throw new Exception("Stop here - unimplemented functionality");
				}
			}
		}
	}

}
