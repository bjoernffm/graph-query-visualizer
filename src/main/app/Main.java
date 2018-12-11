package main.app;

import main.app.common.DotVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        //svr.start();
    	
    	DotVisualizer sqv = new DotVisualizer(
    		"PREFIX nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#>PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> select ?sentID (group_concat(?word) as ?sentence) where { { select ?sentID ?ID ?word where { ?sX_X a nif:Word . ?sX_X conll:WORD ?word . BIND(xsd:integer(replace(str(?sX_X), \".*s\\\\d+_(\\\\d+)$\", \"$1\")) AS ?ID) BIND(xsd:integer(replace(str(?sX_X), \".*s(\\\\d+)_\\\\d+$\", \"$1\")) AS ?sentID) } order by ?sentID ?ID } } group by ?sentID order by ?sentID"
    		//"WITH <http://example/bookStore> DELETE { ?a ?b ?c } INSERT { ?x ?y ?z } WHERE { ?x ?y ?z }"
    		//"DELETE { GRAPH <http://example/bookStore> { ?a ?b ?c } } INSERT { GRAPH <http://example/bookStore> { ?x ?y ?z } } USING <http://example/bookStore> WHERE { ?a ?g ?z }"
    		//"PREFIX foaf:  <http://xmlns.com/foaf/0.1/> DELETE WHERE { ?person foaf:givenName 'Fred'; ?property      ?value }"
    		//"PREFIX dc: <http://purl.org/dc/elements/1.1/> DELETE DATA { <http://example/book2> dc:title \"David Copperfield\" ; dc:creator \"Edmund Wells\" . }"
    		//"PREFIX dc: <http://purl.org/dc/elements/1.1/> PREFIX ns: <http://example.org/ns#> INSERT DATA { GRAPH <http://example/bookStore> { <http://example/book1>  ns:price  42 } GRAPH <http://example/bookStore2> { <http://example/book1>  ns:price  42 } <http://example/book1> dc:title \"A new book\" ; dc:creator \"A.N.Other\" . }"
        );
    	try {
			String ret = sqv.visualize();
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}