package main.app;

import main.app.common.DotVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        //svr.start();
    	
    	DotVisualizer sqv = new DotVisualizer(
    		"DELETE { GRAPH <http://example/bookStore> { ?a ?b ?c } } INSERT { GRAPH <http://example/bookStore> { ?x ?y ?z } } USING <http://example/bookStore> WHERE { ?a ?g ?z }"
    		//"PREFIX foaf:  <http://xmlns.com/foaf/0.1/> DELETE WHERE { ?person foaf:givenName 'Fred'; ?property      ?value }"
    		//"PREFIX dc: <http://purl.org/dc/elements/1.1/> DELETE DATA { <http://example/book2> dc:title \"David Copperfield\" ; dc:creator \"Edmund Wells\" . }"
    		//"PREFIX dc: <http://purl.org/dc/elements/1.1/> PREFIX ns: <http://example.org/ns#> INSERT DATA { GRAPH <http://example/bookStore> { <http://example/book1>  ns:price  42 } GRAPH <http://example/bookStore2> { <http://example/book1>  ns:price  42 } <http://example/book1> dc:title \"A new book\" ; dc:creator \"A.N.Other\" . }"
        );
    	try {
			String ret = sqv.visualize();
			//System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}