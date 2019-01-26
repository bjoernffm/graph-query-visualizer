package main.app.common.interpreters;

import java.util.Set;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprFunction;
import org.apache.jena.sparql.syntax.ElementFilter;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.EntityNode;
import main.app.dot.objects.FilterNode;

public class ElementFilterInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementFilterInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception {

		if (obj.getClass() != ElementFilter.class) {
			throw new Exception(ElementFilter.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementFilter element = (ElementFilter) obj;
		Expr expression = element.getExpr();
		ExprFunction exFunction = expression.getFunction();
		
		if (element.getExpr().isFunction()) {
			Node filter = new FilterNode(exFunction.toString());
			filter.setLabel(this.beautifyExpression(element.toString()));
			graph.addNode(filter);
			
			Set<Var> mentionedVars = exFunction.getVarsMentioned();
			for(Var mentionedVar: mentionedVars) {
				Node filter2 = new EntityNode(this.resolveNodeName(mentionedVar));
				filter2.setNodeType(mentionedVar);
				graph.addNode(filter2);
				Edge edge = new Edge();
				edge.setArrowhead("dot");
				edge.setFrom(filter);
				edge.setTo(filter2);
				graph.addEdge(edge);
			}
		}
	}

}
