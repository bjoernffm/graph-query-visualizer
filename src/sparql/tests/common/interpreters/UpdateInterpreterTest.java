package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import org.junit.Test;

import sparql.app.common.interpreters.UpdateInterpreter;
import sparql.app.dot.Graph;

public class UpdateInterpreterTest {
	
	@Test
	public void fail() throws Exception {
		UpdateInterpreter interpreter = new UpdateInterpreter(null);
		Graph graph = new Graph("main");
		try {
			interpreter.interpret("Test", graph);
		} catch(Exception e) {
			assertEquals("class org.apache.jena.sparql.modify.request.UpdateModify needed as Object. Given: class java.lang.String", e.getMessage());
		}
	}

}
