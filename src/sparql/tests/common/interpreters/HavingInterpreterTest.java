package sparql.tests.common.interpreters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import sparql.app.common.interpreters.HavingInterpreter;
import sparql.app.common.interpreters.QueryInterpreter;
import sparql.app.common.misc.KnowledgeContainer;
import sparql.app.common.visualizers.DotVisualizer;

public class HavingInterpreterTest {

	@Test
	public void test1() {
		KnowledgeContainer kc = new KnowledgeContainer();
		QueryInterpreter qi = new QueryInterpreter(kc);
		HavingInterpreter interpreter = new HavingInterpreter(qi);
		assertEquals(8, interpreter.getUUID().length());
	}

	@Test
	public void test2() throws Exception {
		DotVisualizer sqv = new DotVisualizer("PREFIX : <http://data.example/> SELECT (AVG(?size) AS ?asize) WHERE { ?x :size ?size } GROUP BY ?x HAVING(AVG(?size) > 10)");
		List<String> ret = sqv.visualize();
		System.out.println(ret.get(0));
	}

}
