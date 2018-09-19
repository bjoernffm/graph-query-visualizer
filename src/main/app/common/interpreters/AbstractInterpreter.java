package main.app.common.interpreters;

public abstract class AbstractInterpreter implements Interpreter {
	protected boolean optional = false;
	
	public Interpreter setOptional(boolean optional)
	{
		this.optional = optional;
		return this;
	}
	
	public Interpreter setOptional()
	{
		return this.setOptional(true);
	}
	
	public boolean getOptional()
	{
		return this.optional;
	}
}
