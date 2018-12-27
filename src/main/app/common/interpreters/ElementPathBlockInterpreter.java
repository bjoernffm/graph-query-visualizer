package main.app.common.interpreters;

import java.util.List;

import org.apache.jena.sparql.core.PathBlock;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.path.P_Alt;
import org.apache.jena.sparql.path.P_FixedLength;
import org.apache.jena.sparql.path.P_Inverse;
import org.apache.jena.sparql.path.P_Link;
import org.apache.jena.sparql.path.P_Mod;
import org.apache.jena.sparql.path.P_NegPropSet;
import org.apache.jena.sparql.path.P_OneOrMore1;
import org.apache.jena.sparql.path.P_Path0;
import org.apache.jena.sparql.path.P_ReverseLink;
import org.apache.jena.sparql.path.P_Seq;
import org.apache.jena.sparql.path.P_ZeroOrMore1;
import org.apache.jena.sparql.path.P_ZeroOrOne;
import org.apache.jena.sparql.path.Path;
import org.apache.jena.sparql.syntax.ElementPathBlock;

import main.app.common.misc.KnowledgeContainer;
import main.app.dot.Edge;
import main.app.dot.Graph;
import main.app.dot.Node;
import main.app.dot.objects.ConllNode;
import main.app.dot.objects.EntityNode;

public class ElementPathBlockInterpreter extends AbstractInterpreter implements Interpreter {

	public ElementPathBlockInterpreter(AbstractInterpreter interpreter) {
		super(interpreter);
	}

	public void interpret(Object obj, Graph graph) throws Exception
	{
		if (obj.getClass() != ElementPathBlock.class) {
			throw new Exception(ElementPathBlock.class+" needed as Object. Given: "+obj.getClass());
		}
		
		ElementPathBlock element = (ElementPathBlock) obj;
		PathBlock pathBlock = (PathBlock) element.getPattern();

		for(int i = 0; i < pathBlock.size(); i++) {
			TriplePath el = pathBlock.get(i);

			Node fromNode;
			Node toNode;

			org.apache.jena.graph.Node subject = el.getSubject();
			org.apache.jena.graph.Node predicate = el.getPredicate();
			org.apache.jena.graph.Node object = el.getObject();
			
			if (predicate != null && predicate.toString().equals("http://ufal.mff.cuni.cz/conll2009-st/task-description.html#ID")) {
				ConllNode node = new ConllNode(this.resolveNodeName(subject));
				node.setConllId(object.toString());
				graph.addNode(node);
			} else {
				// Interpret the subject
				fromNode = new EntityNode(this.resolveNodeName(subject));
				fromNode.setTooltip(subject.toString());
				if (!subject.isVariable()) {
					fromNode.setShape("box");
				}
	
				// Interpret the object
				toNode = new EntityNode(this.resolveNodeName(object));			
				toNode.setTooltip(object.toString());
				if (!object.isVariable()) {
					toNode.setShape("box");
				}
				
				// Interpret the path
				Edge edge = new Edge();
				edge.setFrom(fromNode);
				edge.setTo(toNode);
				if (el.isTriple()) {
					edge.setLabel(this.resolveNodeName(predicate));
					edge.setLabeltooltip(predicate.toString());
				} else {
					edge.setLabel(this.resolvePath(el.getPath()));
					edge.setLabeltooltip(el.getPath().toString());
				}
				
				graph.addNode(fromNode);
				graph.addNode(toNode);
				graph.addEdge(edge);
			}
		}
	}
	
	public String resolvePath(Path path) throws Exception
	{
		String retString = "";
		
		if (path instanceof P_Seq) {
			P_Seq tmpPath = (P_Seq) path;
			retString = this.resolvePath(tmpPath.getLeft())+" / "+this.resolvePath(tmpPath.getRight());
		} else if (path instanceof P_Inverse) {
			P_Inverse tmpPath = (P_Inverse) path;
			retString = "^"+this.resolvePath(tmpPath.getSubPath());
		} else if (path instanceof P_Alt) {
			P_Alt tmpPath = (P_Alt) path;
			retString = this.resolvePath(tmpPath.getLeft())+"|"+this.resolvePath(tmpPath.getRight());
		} else if (path instanceof P_ZeroOrMore1) {
			P_ZeroOrMore1 tmpPath = (P_ZeroOrMore1) path;
			retString = this.resolvePath(tmpPath.getSubPath())+"*";
		} else if (path instanceof P_OneOrMore1) {
			P_OneOrMore1 tmpPath = (P_OneOrMore1) path;
			retString = this.resolvePath(tmpPath.getSubPath())+"+";
		} else if (path instanceof P_ZeroOrOne) {
			P_ZeroOrOne tmpPath = (P_ZeroOrOne) path;
			retString = this.resolvePath(tmpPath.getSubPath())+"?";
		} else if (path instanceof P_NegPropSet) {
			List<P_Path0> tmpList = ((P_NegPropSet) path).getNodes();

			P_Path0 tmpPath = tmpList.get(0);
			retString = "!("+this.resolvePath(tmpPath);
			for(int i = 1; i < tmpList.size(); i++) {
				tmpPath = tmpList.get(i);
				retString += "|"+this.resolvePath(tmpPath);
			}
			retString += ")";
		} else if (path instanceof P_Mod) {
			P_Mod tmpPath = (P_Mod) path;
			retString = this.resolvePath(tmpPath.getSubPath());
			if (tmpPath.isFixedLength()) {
				retString += "{"+tmpPath.getFixedLength()+"}";
			} else {
				retString += "{";
				if (tmpPath.getMin() >= 0) {
					retString += tmpPath.getMin();
				}
				retString += ",";
				if (tmpPath.getMax() >= 0) {
					retString += tmpPath.getMax();
				}
				retString += "}";
			}
		} else if (path instanceof P_FixedLength) {
			P_FixedLength tmpPath = (P_FixedLength) path;
			retString = this.resolvePath(tmpPath.getSubPath());
			retString += "{"+tmpPath.getCount()+"}";
		} else if (path instanceof P_Link) {
			P_Link tmpPath = (P_Link) path;
			retString = this.resolveNodeName(tmpPath.getNode());
		} else if (path instanceof P_ReverseLink) {
			P_ReverseLink tmpPath = (P_ReverseLink) path;
			retString = "^"+this.resolveNodeName(tmpPath.getNode());
		} else {
			throw new Exception("Unknown type: "+path.getClass());
		}
		
		return retString;
	}
}
