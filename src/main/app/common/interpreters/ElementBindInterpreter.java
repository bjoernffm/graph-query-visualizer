package main.app.common.interpreters;

import java.util.Set;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.syntax.ElementBind;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.EntityNode;

public class ElementBindInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementBindInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementBind.class) {
			throw new Exception(ElementBind.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementBind element = (ElementBind) obj;
		Expr expression = element.getExpr();
		Var var = element.getVar();

		Node entityNode = new EntityNode("?"+var.getVarName());
		entityNode.setNodeType(var);
		graph.addNode(entityNode);
		
		Set<Var> mentionedVars = expression.getVarsMentioned();
		for(Var mentionedVar: mentionedVars) {
			Node varNode = new EntityNode(mentionedVar.toString());
			varNode.setNodeType(mentionedVar);
			graph.addNode(varNode);
			
			Edge edge = new Edge();
			//edge.setLabel("BIND");
			edge.setLabel(this.beautifyExpression(element.toString()));
			//this.resolveFunctionName(expression);
			edge.setLabeltooltip(element.toString());
			edge.setFrom(varNode);
			edge.setTo(entityNode);
			graph.addEdge(edge);
		}
	}

}
