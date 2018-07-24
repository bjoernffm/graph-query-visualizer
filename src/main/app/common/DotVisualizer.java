package main.app.common;

import org.apache.jena.query.SortCondition;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementBind;
import org.apache.jena.sparql.syntax.ElementData;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;

import main.app.common.interpreters.ElementBindInterpreter;
import main.app.common.interpreters.ElementDataInterpreter;
import main.app.common.interpreters.ElementFilterInterpreter;
import main.app.common.interpreters.ElementPathBlockInterpreter;
import main.app.common.interpreters.GroupByInterpreter;
import main.app.common.interpreters.OrderByInterpreter;
import main.app.common.interpreters.ProjectVarInterpreter;
import main.app.dot.Graph;
import main.app.dot.Subgraph;

import org.apache.jena.sparql.syntax.ElementFilter;

import java.util.List;

final public class DotVisualizer extends QueryVisualizer implements QueryVisualizerInterface {
	
	Graph graph = new Graph();
	Subgraph subgraph = new Subgraph("cluster_1");

	public DotVisualizer(String query) {
		super(query);
	}

	public String visualize() throws Exception
	{
		//ArrayList<ExprAggregator> ret2 = (ArrayList) this.query.getAggregators();
		//System.out.println(ret2.getClass());
		
		//String ret = this.query.getBaseURI();
		//System.out.println(ret);
		
		//List<String> ret = this.query.getGraphURIs();
		//System.out.println(ret);
		
		//PrefixMappingImpl ret2 = (PrefixMappingImpl) this.query.getPrefixMapping();

		ElementGroup queryPattern = (ElementGroup) this.query.getQueryPattern();

		for(int i = 0; i < queryPattern.size(); i++) {
			Element el = queryPattern.get(i);
			if (el instanceof org.apache.jena.sparql.syntax.ElementPathBlock) {
				(new ElementPathBlockInterpreter()).interpret((ElementPathBlock) el, this);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementFilter) {
				(new ElementFilterInterpreter()).interpret((ElementFilter) el, this);
			} else if (el instanceof org.apache.jena.sparql.syntax.ElementData) {
				(new ElementDataInterpreter()).interpret((ElementData) el, this);
			}  else if (el instanceof org.apache.jena.sparql.syntax.ElementBind) {
				(new ElementBindInterpreter()).interpret((ElementBind) el, this);
			} else {
				System.out.println(el.getClass());
				System.out.println(el+"\n");
			}
		}
		
		/**
		 * adding nodes for "group by"
		 */
		VarExprList groupByVarExpressions = this.query.getGroupBy();
		(new GroupByInterpreter()).interpret(groupByVarExpressions, this);
		
		/**
		 * adding nodes for "order by"
		 */
		List<SortCondition> sortConditions = this.query.getOrderBy();
		(new OrderByInterpreter()).interpret(sortConditions, this);
		
		/**
		 * aggregate project- and mentioned-vars
		 */
		VarExprList project = this.query.getProject();
		(new ProjectVarInterpreter()).interpret(project, this);

		this.graph.addSubgraph(this.subgraph);
		
		System.out.println(this.graph);
		
		return "";
	}
	
	public Graph getGraph()
	{
		return this.graph;
	}
	
	public Subgraph getSubgraph()
	{
		return this.subgraph;
	}
}
