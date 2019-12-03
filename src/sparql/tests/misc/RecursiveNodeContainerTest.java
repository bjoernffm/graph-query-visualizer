package sparql.tests.misc;

import static org.junit.Assert.*;

import org.junit.Test;

import sparql.app.dot.Node;
import sparql.app.misc.RecursiveNodeContainer;

public class RecursiveNodeContainerTest {

	@Test
	public void test() {
		Node node = new Node("test");
		
		RecursiveNodeContainer rnc = new RecursiveNodeContainer(4, node);
		assertEquals(rnc.getLevel(), 4);
		assertEquals(rnc.getNode(), node);

		rnc.setLevel(3);
		rnc.setNode(null);
		
		assertEquals(rnc.getLevel(), 3);
		assertEquals(rnc.getNode(), null);
	}

}
