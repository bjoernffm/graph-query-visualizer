package main.app;

import main.app.common.DotVisualizer;
//import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        /*Server svr = new Server();
        svr.start();*/
    	
    	DotVisualizer sqv = new DotVisualizer(
    		"PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT ?title ?price WHERE   { ?x ns:price+/ns:lowestprice ?price . ?x ns:weight 100 . FILTER (?price < ?priceToAvoid) ?x dc:title ?title . }"
    	);
    	/*
    	DotVisualizer sqv = new DotVisualizer(
            "PREFIX wd: <http://www.wikidata.org/entity/> PREFIX wdt: <http://www.wikidata.org/prop/direct/> PREFIX schema: <http://schema.org/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> select distinct (max(?url) as ?titleLink) ?titleLinkLabel ?publication (min(?date) as ?issued) where {  values ( ?author ?language ) {    ( wd:Q36519983 \"en\" )  }  ?work wdt:P50 ?author .  ?work wdt:P1476 ?title .  ?work wdt:P953 ?url .  ?work wdt:P1433 ?pub .  ?pub rdfs:label ?pubLang .  filter(lang(?pubLang) = ?language)  ?work wdt:P577 ?dateTime .  bind(substr(str(?dateTime), 0, 11) as ?date)  bind(str(?title) as ?titleLinkLabel)  bind(str(?pubLang) as ?publication) } group by ?titleLink ?titleLinkLabel ?publication order by desc(?issued) LIMIT 10"
        );
        */
    	/*DotVisualizer sqv = new DotVisualizer(
            "PREFIX nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> SELECT ?ID ?WORD ?LEMMA ?UPOS ?POS ?FEAT ?HEAD ?EDGE ?DEPS ?MISC { SELECT ?ordinal (group_concat(?IDs;separator=\"|\") as ?ID) (group_concat(?WORDs;separator=\"|\") as ?WORD) (group_concat(?LEMMAs;separator=\"|\") as ?LEMMA) (group_concat(?UPOSs;separator=\"|\") as ?UPOS) (group_concat(?POSs;separator=\"|\") as ?POS) (group_concat(?FEATs;separator=\"|\") as ?FEAT) (group_concat(?HEADs;separator=\"|\") as ?HEAD) (group_concat(?EDGEs;separator=\"|\") as ?EDGE) (group_concat(?DEPSs;separator=\"|\") as ?DEPS) (group_concat(?MISCs;separator=\"|\") as ?MISC) WHERE { ?word a nif:Word . { SELECT ?word (count(?next) as ?ordinal) WHERE { ?word a nif:Word . ?word nif:nextWord* ?next . } group by ?word } OPTIONAL{?word conll:ID ?IDs .} . OPTIONAL{?word conll:WORD ?WORDs .} . OPTIONAL{?word conll:LEMMA ?LEMMAs .} . OPTIONAL{?word conll:UPOS ?UPOSs .} . OPTIONAL{?word conll:POS ?POSs .} . OPTIONAL{?word conll:FEAT ?FEATs .} . OPTIONAL { ?word conll:HEAD ?headurl . bind(strafter(strafter(str(?headurl),\"#s\"), \"_\") as ?HEADs) . } . OPTIONAL{?word conll:EDGE ?EDGEs .} . OPTIONAL{?word conll:DEPS ?DEPSs .} . OPTIONAL{?word conll:MISC ?MISCs .} . } group by ?word ?ordinal order by desc(?ordinal) }"
        );*/
    	try {
			sqv.visualize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}