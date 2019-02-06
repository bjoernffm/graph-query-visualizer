package main.app.common.interpreters;

import java.util.Set;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.syntax.ElementBind;

import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.EntityNode;
import main.app.dot.objects.FakeEdgeNode;
import main.app.dot.objects.FilterNode;

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

		Node fakeNode = new FakeEdgeNode("BIND"+this.getUUID());
		fakeNode.setLabel("BIND");
		graph.addNode(fakeNode);
		
		Node descriptionNode = new FilterNode("describe"+this.getUUID());
		descriptionNode.setLabel(this.beautifyExpression(element.toString()));
		graph.addNode(descriptionNode);
		
		Edge edge1 = new Edge();	
		edge1.setFrom(fakeNode);
		edge1.setTo(entityNode);
		
		Edge edge2 = new Edge();
		edge2.setArrowhead("none");	
		edge2.setStyle("dashed");
		edge2.setFrom(descriptionNode);
		edge2.setTo(fakeNode);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		
		Set<Var> mentionedVars = expression.getVarsMentioned();

		for(Var mentionedVar: mentionedVars) {
			Node varNode = new EntityNode(mentionedVar.toString());
			varNode.setNodeType(mentionedVar);
			graph.addNode(varNode);
			
			Edge edge3 = new Edge();
			edge3.setArrowhead("none");
			edge3.setFrom(varNode);
			edge3.setTo(fakeNode);
			
			graph.addEdge(edge3);
		}
	}

}
