package main.app;

import java.util.List;

import main.app.common.visualizers.DotVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        //svr.start();
    	
    	DotVisualizer sqv = new DotVisualizer(
    		"PREFIX foaf: <http://xmlns.com/foaf/0.1/> SELECT ?age WHERE { ?person foaf:name \"Max Mustermann\" . ?person foaf:age ?age }"
    		//"PREFIX foaf: <http://xmlns.com/foaf/0.1/> PREFIX rss:  <http://purl.org/rss/1.0/> PREFIX dc:   <http://purl.org/dc/elements/1.1/> SELECT ?title ?known_name ?link FROM <http://planetrdf.com/index.rdf> FROM NAMED <phil-foaf.rdf> WHERE { GRAPH <phil-foaf.rdf> { ?me foaf:name \"Phil McCarthy\" . ?me foaf:knows ?known_person . ?known_person foaf:name ?known_name . } . ?item dc:creator ?known_name . ?item rss:title ?title . ?item rss:link ?link . ?item dc:date ?date. } ORDER BY DESC(?date) LIMIT 10"
    		//"PREFIX  :     <http://people.example/> SELECT  ?y ?name WHERE { :alice :knows ?y { SELECT  ?y (MIN(?n) AS ?name) WHERE { ?y :name ?n } GROUP BY ?y } }"
    		//"PREFIX syn: <http://www.acsu.buffalo.edu/~rrgpage/rrg.html#syn_> prefix conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> prefix nif:   <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> prefix terms: <http://purl.org/acoli/open-ie/> prefix powla: <http://purl.org/powla/powla.owl#> INSERT { ?t syn:has ?x. } WHERE { ?t a syn:TEXT; syn:has ?s. ?x syn:head+ ?h2. MINUS { [] syn:head|syn:has ?x } };"
    		//"PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT  ?title ?price {  ?x ns:price ?p . ?x ns:discount ?discount BIND (?p*(1-?discount) AS ?price) FILTER(?price < 20) ?x dc:title ?title . }"
    		//"INSERT { ?a ?b ?c } WHERE { { ?a ?b ?c FILTER(contains(str(?a),'http://purl.org/olia') && !contains(str(?a),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?b),'http://purl.org/olia') && !contains(str(?b),'http://purl.org/olia/olia.owl')) } UNION { ?a ?b ?c FILTER(contains(str(?c),'http://purl.org/olia') && !contains(str(?c),'http://purl.org/olia/olia.owl')) } }"
    		//"PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> SELECT ?a { ?b conll:HEAD ?a. ?b conll:ID \"1\". ?b conll:POS \"NN\" }"
    		//"PREFIX nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> SELECT ?ID ?WORD ?LEMMA ?UPOS ?POS ?FEAT ?HEAD ?EDGE ?DEPS ?MISC { SELECT  ?ordinal (group_concat(?IDs;separator=\"|\") as ?ID) (group_concat(?WORDs;separator=\"|\") as ?WORD) (group_concat(?LEMMAs;separator=\"|\") as ?LEMMA) (group_concat(?UPOSs;separator=\"|\") as ?UPOS) (group_concat(?POSs;separator=\"|\") as ?POS) (group_concat(?FEATs;separator=\"|\") as ?FEAT) (group_concat(?HEADs;separator=\"|\") as ?HEAD) (group_concat(?EDGEs;separator=\"|\") as ?EDGE) (group_concat(?DEPSs;separator=\"|\") as ?DEPS) (group_concat(?MISCs;separator=\"|\") as ?MISC) WHERE { ?word a nif:Word . { SELECT ?word (count(?next) as ?ordinal) WHERE { ?word a nif:Word . ?word nif:nextWord* ?next . } group by ?word } OPTIONAL{?word conll:ID ?IDs .} . OPTIONAL{?word conll:WORD ?WORDs .} . OPTIONAL{?word conll:LEMMA ?LEMMAs .} . OPTIONAL{?word conll:UPOS ?UPOSs .} . OPTIONAL{?word conll:POS ?POSs .} . OPTIONAL{?word conll:FEAT ?FEATs .} . OPTIONAL { ?word conll:HEAD ?headurl . bind(strafter(strafter(str(?headurl),\"#s\"), \"_\") as ?HEADs) . } . OPTIONAL{?word conll:EDGE ?EDGEs .} . OPTIONAL{?word conll:DEPS ?DEPSs .} . OPTIONAL{?word conll:MISC ?MISCs .} . } group by ?word ?ordinal order by desc(?ordinal) }"
    			
    		//"PREFIX : <http://data.example/> SELECT (group_concat(?size;separator=\"|\") as ?asize) WHERE { ?x :size ?size } GROUP BY ?x HAVING ((MAX(?size) > 50) && (MIN(?size) < 300))"
        );
    	
    	//FunctionBeautifier.beautify("BIND( IRI(REPLACE( STR(?predicate),\"prop/direct/\",\"entity/\" )) AS ?p)");
    	
    	try {
    		//sqv.disableClarificationEdges();
    		sqv.setSubgraphDepth(3);
			List<String> ret = sqv.visualize();
			System.out.println(ret.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}