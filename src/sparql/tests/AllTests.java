package sparql.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sparql.tests.common.interpreters.ElementBindInterpreterTest;
import sparql.tests.common.interpreters.ElementDataInterpreterTest;
import sparql.tests.common.interpreters.ElementFilterInterpreterTest;
import sparql.tests.common.interpreters.ElementNamedGraphInterpreterTest;
import sparql.tests.common.interpreters.ElementOptionalInterpreterTest;
import sparql.tests.common.interpreters.LimitInterpreterTest;
import sparql.tests.common.interpreters.OffsetInterpreterTest;
import sparql.tests.common.interpreters.OrderByInterpreterTest;
import sparql.tests.common.interpreters.ProjectVarInterpreterTest;
import sparql.tests.common.interpreters.UpdateModifyInterpreterTest;
import sparql.tests.dot.EdgeTest;
import sparql.tests.dot.GraphTest;
import sparql.tests.dot.MultipleNodesInSubgraphsTest;
import sparql.tests.dot.NodeTest;
import sparql.tests.dot.ObjectTests;
import sparql.tests.dot.SubgraphTest;
import sparql.tests.dot.interpreters.InterpreterTest;

@RunWith(Suite.class)
@SuiteClasses({
	EdgeTest.class,
	GraphTest.class,
	SubgraphTest.class,
	NodeTest.class,
	MultipleNodesInSubgraphsTest.class,
	ObjectTests.class,
	InterpreterTest.class,
	
	LimitInterpreterTest.class,
	OffsetInterpreterTest.class,
	ElementDataInterpreterTest.class,
	ElementNamedGraphInterpreterTest.class,
	ProjectVarInterpreterTest.class,
	ElementFilterInterpreterTest.class,
	ElementBindInterpreterTest.class,
	ElementOptionalInterpreterTest.class,
	OrderByInterpreterTest.class,
	UpdateModifyInterpreterTest.class
})
public class AllTests {

}
