package main.app.dot;

abstract public class Object {
	protected String escape(String string)
	{
		return string.replace("\"", "\\\"");
	}
}
