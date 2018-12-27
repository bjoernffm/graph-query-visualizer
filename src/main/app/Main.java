package main.app;

import java.io.UnsupportedEncodingException;
import java.util.List;

import main.app.common.misc.FunctionBeautifier;
import main.app.common.visualizers.DotVisualizer;
import main.app.dot.objects.ConllNode;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        svr.start();
    	
    	DotVisualizer sqv = new DotVisualizer(
    		//"PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> SELECT ?a { ?a conll:ID ?id. ?a conll:FORM ?form }"
    		"PREFIX nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> SELECT ?ID ?WORD ?LEMMA ?UPOS ?POS ?FEAT ?HEAD ?EDGE ?DEPS ?MISC { SELECT  ?ordinal (group_concat(?IDs;separator=\"|\") as ?ID) (group_concat(?WORDs;separator=\"|\") as ?WORD) (group_concat(?LEMMAs;separator=\"|\") as ?LEMMA) (group_concat(?UPOSs;separator=\"|\") as ?UPOS) (group_concat(?POSs;separator=\"|\") as ?POS) (group_concat(?FEATs;separator=\"|\") as ?FEAT) (group_concat(?HEADs;separator=\"|\") as ?HEAD) (group_concat(?EDGEs;separator=\"|\") as ?EDGE) (group_concat(?DEPSs;separator=\"|\") as ?DEPS) (group_concat(?MISCs;separator=\"|\") as ?MISC) WHERE { ?word a nif:Word . { SELECT ?word (count(?next) as ?ordinal) WHERE { ?word a nif:Word . ?word nif:nextWord* ?next . } group by ?word } OPTIONAL{?word conll:ID ?IDs .} . OPTIONAL{?word conll:WORD ?WORDs .} . OPTIONAL{?word conll:LEMMA ?LEMMAs .} . OPTIONAL{?word conll:UPOS ?UPOSs .} . OPTIONAL{?word conll:POS ?POSs .} . OPTIONAL{?word conll:FEAT ?FEATs .} . OPTIONAL { ?word conll:HEAD ?headurl . bind(strafter(strafter(str(?headurl),\"#s\"), \"_\") as ?HEADs) . } . OPTIONAL{?word conll:EDGE ?EDGEs .} . OPTIONAL{?word conll:DEPS ?DEPSs .} . OPTIONAL{?word conll:MISC ?MISCs .} . } group by ?word ?ordinal order by desc(?ordinal) }"
    			
    		//"WITH <http://example/bookStore> DELETE { ?a ?b ?c } INSERT { ?x ?y ?z } WHERE { ?x ?q ?a }"
    		//"DELETE { GRAPH <http://example/bookStore> { ?a ?b ?c } } INSERT { GRAPH <http://example/bookStore> { ?x ?y ?z } } USING <http://example/bookStore> WHERE { ?a ?g ?z }"
    		//"PREFIX foaf:  <http://xmlns.com/foaf/0.1/> DELETE WHERE { ?person foaf:givenName 'Fred'; ?property      ?value }"
    		//"PREFIX dc: <http://purl.org/dc/elements/1.1/> DELETE DATA { <http://example/book2> dc:title \"David Copperfield\" ; dc:creator \"Edmund Wells\" . }"
    		//"PREFIX dc: <http://purl.org/dc/elements/1.1/> PREFIX ns: <http://example.org/ns#> INSERT DATA { GRAPH <http://example/bookStore> { <http://example/book1>  ns:price  42 } GRAPH <http://example/bookStore2> { <http://example/book1>  ns:price  42 } <http://example/book1> dc:title \"A new book\" ; dc:creator \"A.N.Other\" . }"
        );
    	
    	//FunctionBeautifier.beautify("BIND( IRI(REPLACE( STR(?predicate),\"prop/direct/\",\"entity/\" )) AS ?p)");
    	
    	try {
			List<String> ret = sqv.visualize();
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}