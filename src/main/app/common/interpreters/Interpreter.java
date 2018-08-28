package main.app.common.interpreters;

import main.app.dot.Graph;

public interface Interpreter {
	void interpret(Object obj, Graph graph) throws Exception;
}
