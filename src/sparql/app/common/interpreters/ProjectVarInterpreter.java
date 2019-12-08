package sparql.app.common.interpreters;

import java.util.HashMap;
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
import org.apache.jena.sparql.expr.nodevalue.NodeValueString;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Node;
import sparql.app.dot.objects.EntityNode;
import sparql.app.dot.objects.SelectNode;

public class ProjectVarInterpreter extends AbstractInterpreter implements Interpreter {

	public ProjectVarInterpreter(AbstractInterpreter interpreter)
	{
		super(interpreter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		List<Var> projectVars;
		Map<Var, Expr> projectExpressions;

		if (obj.getClass() == VarExprList.class) {
			// get general list of vars and expressions
			VarExprList project = (VarExprList) obj;
			// get only the selected vars without expressions
			projectVars = project.getVars();
			// list of all aggregation expressions
			projectExpressions = project.getExprs();
		} else if (obj instanceof List) {
			// get only the selected vars without expressions
			projectVars = (List<Var>) obj;
			// list of all aggregation expressions
			projectExpressions = new HashMap<Var, Expr>();
		} else {
			throw new Exception(VarExprList.class+" needed as Object. Given: "+obj.getClass());
		}
		
		// now iterate through all project vars and check for possible aggregations
		for(int i = 0; i < projectVars.size(); i++) {
			Var var = projectVars.get(i);

			Node selectNode = new SelectNode("?"+var.getName());
			selectNode.setNodeType(var);
			selectNode.setTooltip(var.toString());
			selectNode.setShape("doublecircle");
			graph.addNode(selectNode);
			
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
						varNode.setNodeType(mentionedVar);
						graph.addNode(varNode);

						Edge edge = new Edge();
						edge.setLabel(aggregator.toString());
						edge.setLabeltooltip(aggregator.toString()+" AS ?"+var.getName());
						edge.setFrom(varNode);
						edge.setTo(selectNode);
						graph.addEdge(edge);
					}
				} else if (projectExpressions.get(var) instanceof E_Str) {
					E_Str projectExpression = (E_Str) projectExpressions.get(var);
					//System.out.println(projectExpression);
					
					Set<Var> mentionedVars = projectExpression.getVarsMentioned();
					for(Var mentionedVar: mentionedVars) {
						Node varNode = new EntityNode(mentionedVar.toString());
						varNode.setNodeType(mentionedVar);
						graph.addNode(varNode);
						
						Edge edge = new Edge();
						// TODO check for better expression to show in the graph -> (str ?link) looks stupid
						edge.setLabel(projectExpression.getFunction().toString());
						edge.setFrom(varNode);
						edge.setTo(selectNode);
						graph.addEdge(edge);
					}
				} else if (projectExpressions.get(var) instanceof NodeValueString) {
					NodeValueString value = (NodeValueString) projectExpressions.get(var);

					Node varNode = new EntityNode(value.asQuotedString());
					graph.addNode(varNode);
					
					Edge edge = new Edge();

					edge.setLabel("AS");
					edge.setLabeltooltip(value.asQuotedString()+" AS ?"+var.getName());
					edge.setFrom(varNode);
					edge.setTo(selectNode);
					graph.addEdge(edge);
				} else {
					System.out.println(projectExpressions.get(var).getClass());
					System.out.println(projectExpressions.get(var));
					throw new Exception("Stop here - unimplemented functionality");
				}
			}
		}
	}

}
