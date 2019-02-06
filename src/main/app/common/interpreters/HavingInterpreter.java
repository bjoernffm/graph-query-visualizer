package main.app.common.interpreters;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.sparql.expr.Expr;

import main.app.dot.Graph;
import main.app.dot.objects.AggregateNode;

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
		graph.addNode(havingNode);
		
		String havingString = "HAVING\\n---------\\n";

		List<Expr> list = (List<Expr>) obj;
		for(int i = 0; i < list.size(); i++) {
			Expr expression = list.get(i);
			System.out.println(expression.getFunction());
			
			/*FunctionResolution fr = this.resolveFunctionName(expression);
			ArrayList<ExprVar> mentionedVarList = fr.getMentionedVars();
			
			havingString += "* "+fr.getName()+"\\l";
			
			for(int j = 0; j < mentionedVarList.size(); j++) {
				Node varNode = new EntityNode(mentionedVarList.get(j).toString());
				graph.addNode(varNode);
				
				Edge edge = new Edge();
				edge.setArrowhead("dot");
				edge.setFrom(havingNode);
				edge.setTo(varNode);
				edge.setStyle("dotted");
				graph.addEdge(edge);
			}*/
		}
		
		havingNode.setLabel(havingString);
	}
}
