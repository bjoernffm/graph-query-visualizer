package main.app.common.interpreters;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprAggregator;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.aggregate.Aggregator;

import main.app.common.DotVisualizer;
import main.app.dot.Edge;
import main.app.dot.Node;
import main.app.dot.objects.EntityNode;

public class ProjectVarInterpreter implements Interpreter {

	@Override
	public void interpret(Object obj, DotVisualizer visualizer) throws Exception
	{
		if (obj.getClass() != VarExprList.class) {
			throw new Exception(VarExprList.class+" needed as Object. Given: "+obj.getClass());
		}
		
		VarExprList project = (VarExprList) obj;
		
		List<Var> projectVars = project.getVars();
		Map<Var, Expr> projectExpressions = project.getExprs();
		
		for(int i = 0; i < projectVars.size(); i++) {
			Var var = projectVars.get(i);

			Node entityNode = new EntityNode("?"+var.getName());
			entityNode.setShape("doublecircle");
			visualizer.getSubgraph().addNode(entityNode);
			
			if (projectExpressions.containsKey(var)) {
				ExprAggregator projectExpression = (ExprAggregator) projectExpressions.get(var);
	
				Aggregator aggregator = projectExpression.getAggregator();
				ExprList expressionList = aggregator.getExprList();
				//System.out.println(aggregator);
				
				Set<Var> mentionedVars = expressionList.getVarsMentioned();
				for(Var mentionedVar: mentionedVars) {
					Node varNode = new EntityNode(mentionedVar.toString());
					visualizer.getSubgraph().addNode(varNode);
					
					Edge edge = new Edge();
					edge.setLabel(aggregator.toString());
					edge.setFrom(varNode);
					edge.setTo(entityNode);
					visualizer.getSubgraph().addEdge(edge);
				}
			}
		}
	}

}
