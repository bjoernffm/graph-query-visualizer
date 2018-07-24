package main.app.common.interpreters;

import main.app.common.DotVisualizer;

public interface Interpreter {
	void interpret(Object obj, DotVisualizer visualizer) throws Exception;
}
