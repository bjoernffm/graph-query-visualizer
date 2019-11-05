package sparql.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.core.VarExprList;

import sparql.app.dot.Edge;
import sparql.app.dot.Graph;
import sparql.app.dot.Node;
import sparql.app.dot.objects.AggregateNode;
import sparql.app.dot.objects.EntityNode;

public class GroupByInterpreter extends AbstractInterpreter implements Interpreter {

	public GroupByInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	@Override
	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != VarExprList.class) {
			throw new Exception(VarExprList.class+" needed as Object. Given: "+obj.getClass());
		}
		
		VarExprList groupByVarExpressions = (VarExprList) obj;
		List<Var> groupByVars = groupByVarExpressions.getVars();

		Node groupByNode = new AggregateNode("groupby_"+this.hashCode());
		graph.addNode(groupByNode);
		
		String groupByString = "GROUP BY\\n---------\\n";
		
		for(Var groupByVar: groupByVars) {
			Node varNode = new EntityNode("?"+groupByVar.getName());
			varNode.setNodeType(groupByVar);
			graph.addNode(varNode);
			
			groupByString += "* ?"+groupByVar.getName()+"\\l";

			Edge edge = new Edge();
			edge.setArrowhead("dot");
			edge.setFrom(groupByNode);
			edge.setTo(varNode);
			edge.setStyle("dotted");
			graph.addEdge(edge);
		}
		
		groupByNode.setLabel(groupByString);
	}

}
