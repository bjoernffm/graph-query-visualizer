package main.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import main.tests.dot.EdgeTest;
import main.tests.dot.GraphTest;
import main.tests.dot.MultipleNodesInSubgraphsTest;
import main.tests.dot.NodeTest;
import main.tests.dot.ObjectTests;
import main.tests.dot.SubgraphTest;
import main.tests.dot.interpreters.InterpreterTest;

@RunWith(Suite.class)
@SuiteClasses({
	EdgeTest.class,
	GraphTest.class,
	SubgraphTest.class,
	NodeTest.class,
	MultipleNodesInSubgraphsTest.class,
	ObjectTests.class,
	InterpreterTest.class
})
public class AllTests {

}
