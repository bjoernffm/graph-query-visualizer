package sparql.app.common.interpreters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Node;
import sparql.app.dot.objects.AggregateNode;
import sparql.app.dot.objects.EntityNode;

public class HavingInterpreter extends AbstractInterpreter implements Interpreter {

	public HavingInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ArrayList.class) {
			throw new Exception(ArrayList.class+" needed as Object. Given: "+obj.getClass());
		}

		AggregateNode havingNode = new AggregateNode("having_"+this.hashCode());
		graph.getParent().addNode(havingNode);
		
		String havingString = "HAVING\\n---------\\n";

		@SuppressWarnings("unchecked")
		List<Expr> list = (List<Expr>) obj;
		for(int i = 0; i < list.size(); i++) {
			Expr expression = list.get(i);			
			
			Iterator<Var> mentionedVarList = expression.getVarsMentioned().iterator();
			
			havingString += expression.toString()+"\\l";
			
			Var mentionedVar;
			while(mentionedVarList.hasNext()) {
				mentionedVar = mentionedVarList.next();
				Node varNode = new EntityNode(mentionedVar.toString());
				graph.addNode(varNode);
				
				Edge edge = new Edge();
				edge.setArrowhead("dot");
				edge.setFrom(havingNode);
				edge.setTo(varNode);
				edge.setStyle("dotted");
				graph.addEdge(edge);
			}
			havingNode.setLabel(havingString);
		}
	}
}
