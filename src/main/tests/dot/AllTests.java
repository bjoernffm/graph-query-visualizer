package main.tests.dot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EdgeTest.class, GraphTest.class, NodeTest.class })
public class AllTests {

}
