package main.app.common.interpreters;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.expr.Expr;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.AggregateNode;
import main.app.dot.objects.EntityNode;

public class OrderByInterpreter extends AbstractInterpreter implements Interpreter {

	public OrderByInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ArrayList.class) {
			throw new Exception(ArrayList.class+" needed as Object. Given: "+obj.getClass());
		}
		
		List<SortCondition> sortConditions = (ArrayList<SortCondition>) obj;
		
		Node orderByNode = new AggregateNode("orderby_"+this.hashCode());
		graph.addNode(orderByNode);
		String orderByString = "ORDER BY\\n---------\\n";
		
		for(SortCondition sortCondition: sortConditions) {
			Expr expression = sortCondition.getExpression();
			
			orderByString += "* "+expression.toString();
			if (sortCondition.getDirection() == Query.ORDER_ASCENDING) {
				orderByString += " ASC\\l";
			} else if (sortCondition.getDirection() == Query.ORDER_DESCENDING) {
				orderByString += " DESC\\l";
			}
			
			if (expression.isVariable()) {
				Node varNode = new EntityNode("?"+expression.getVarName());

				Edge edge = new Edge();
				edge.setArrowhead("dot");
				edge.setFrom(orderByNode);
				edge.setTo(varNode);
				edge.setStyle("dotted");
				
				graph.addNode(varNode);
				graph.addEdge(edge);
			}
		}
		
		orderByNode.setLabel(orderByString);
	}

}
