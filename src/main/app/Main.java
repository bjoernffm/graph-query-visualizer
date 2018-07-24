package main.app;

import main.app.common.SimpleQueryVisualizer;
//import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        /*Server svr = new Server();
        svr.start();*/
    	
    	/*SimpleQueryVisualizer sqv = new SimpleQueryVisualizer(
    		"PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT ?title ?price WHERE   { ?x ns:price ?price . ?x ns:weight 100 . FILTER (?price < ?priceToAvoid) ?x dc:title ?title . }"
    	);*/
    	SimpleQueryVisualizer sqv = new SimpleQueryVisualizer(
        	"PREFIX wd: <http://www.wikidata.org/entity/> PREFIX wdt: <http://www.wikidata.org/prop/direct/> PREFIX schema: <http://schema.org/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> select distinct (max(?url) as ?titleLink) ?titleLinkLabel ?publication (min(?date) as ?issued) where {  values ( ?author ?language ) {    ( wd:Q36519983 \"en\" )  }  ?work wdt:P50 ?author .  ?work wdt:P1476 ?title .  ?work wdt:P953 ?url .  ?work wdt:P1433 ?pub .  ?pub rdfs:label ?pubLang .  filter(lang(?pubLang) = ?language)  ?work wdt:P577 ?dateTime .  bind(substr(str(?dateTime), 0, 11) as ?date)  bind(str(?title) as ?titleLinkLabel)  bind(str(?pubLang) as ?publication) } group by ?titleLink ?titleLinkLabel ?publication order by desc(?issued)"
        );
    	sqv.visualize();
    }
}