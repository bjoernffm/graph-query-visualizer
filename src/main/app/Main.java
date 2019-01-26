package main.app;

import java.util.List;

import main.app.common.visualizers.DotVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        svr.start();
    	
    	DotVisualizer sqv = new DotVisualizer(
    		//"INSERT { ?a ?b ?c } WHERE { { ?a ?b ?c FILTER(contains(str(?a),'http://purl.org/olia') && !contains(str(?a),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?b),'http://purl.org/olia') && !contains(str(?b),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?c),'http://purl.org/olia') && !contains(str(?c),'http://purl.org/olia/olia.owl')) } }"
    		//"PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> SELECT ?a { ?b conll:HEAD ?a. ?b conll:ID \"1\". ?b conll:POS \"NN\" }"
    		"PREFIX nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> SELECT ?ID ?WORD ?LEMMA ?UPOS ?POS ?FEAT ?HEAD ?EDGE ?DEPS ?MISC { SELECT  ?ordinal (group_concat(?IDs;separator=\"|\") as ?ID) (group_concat(?WORDs;separator=\"|\") as ?WORD) (group_concat(?LEMMAs;separator=\"|\") as ?LEMMA) (group_concat(?UPOSs;separator=\"|\") as ?UPOS) (group_concat(?POSs;separator=\"|\") as ?POS) (group_concat(?FEATs;separator=\"|\") as ?FEAT) (group_concat(?HEADs;separator=\"|\") as ?HEAD) (group_concat(?EDGEs;separator=\"|\") as ?EDGE) (group_concat(?DEPSs;separator=\"|\") as ?DEPS) (group_concat(?MISCs;separator=\"|\") as ?MISC) WHERE { ?word a nif:Word . { SELECT ?word (count(?next) as ?ordinal) WHERE { ?word a nif:Word . ?word nif:nextWord* ?next . } group by ?word } OPTIONAL{?word conll:ID ?IDs .} . OPTIONAL{?word conll:WORD ?WORDs .} . OPTIONAL{?word conll:LEMMA ?LEMMAs .} . OPTIONAL{?word conll:UPOS ?UPOSs .} . OPTIONAL{?word conll:POS ?POSs .} . OPTIONAL{?word conll:FEAT ?FEATs .} . OPTIONAL { ?word conll:HEAD ?headurl . bind(strafter(strafter(str(?headurl),\"#s\"), \"_\") as ?HEADs) . } . OPTIONAL{?word conll:EDGE ?EDGEs .} . OPTIONAL{?word conll:DEPS ?DEPSs .} . OPTIONAL{?word conll:MISC ?MISCs .} . } group by ?word ?ordinal order by desc(?ordinal) }"
    			
    		//"PREFIX : <http://data.example/> SELECT (group_concat(?size;separator=\"|\") as ?asize) WHERE { ?x :size ?size } GROUP BY ?x HAVING ((MAX(?size) > 50) && (MIN(?size) < 300))"
        );
    	
    	//FunctionBeautifier.beautify("BIND( IRI(REPLACE( STR(?predicate),\"prop/direct/\",\"entity/\" )) AS ?p)");
    	
    	try {
			List<String> ret = sqv.visualize();
			System.out.println(ret.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}