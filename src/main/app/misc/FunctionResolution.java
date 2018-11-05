package main.app.misc;

import java.util.ArrayList;

import org.apache.jena.sparql.expr.ExprVar;

public class FunctionResolution {
	protected String name = "";
	protected ArrayList<ExprVar> mentionedVars = new ArrayList<ExprVar>();
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public ArrayList<ExprVar> getMentionedVars()
	{
		return mentionedVars;
	}
	
	public void addMentionedVar(ExprVar var)
	{
		this.mentionedVars.add(var);
	}
	
	public void synchronizeMentionedVars(FunctionResolution functionResolution)
	{
		ArrayList<ExprVar> list = functionResolution.getMentionedVars();
		
		for(int i = 0; i < list.size(); i++) {
			this.addMentionedVar(list.get(i));
		}
	}
}
