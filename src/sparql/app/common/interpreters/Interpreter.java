package sparql.app.common.interpreters;

import sparql.app.dot.Graph;

public interface Interpreter {
	void interpret(Object obj, Graph graph) throws Exception;
}
