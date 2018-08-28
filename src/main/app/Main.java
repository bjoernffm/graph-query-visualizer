package main.app;

import main.app.common.DotVisualizer;
//import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        /*Server svr = new Server();
        svr.start();*/
    	
    	DotVisualizer sqv = new DotVisualizer(
            //"PREFIX foaf: <http://xmlns.com/foaf/0.1/> PREFIX rss:  <http://purl.org/rss/1.0/> PREFIX dc:   <http://purl.org/dc/elements/1.1/> SELECT ?title ?known_name (str(?link) AS ?linkoo) FROM <http://planetrdf.com/index.rdf> FROM NAMED <phil-foaf.rdf> WHERE { ?item dc:creator ?known_name . ?item rss:title ?title . ?item rss:link ?link . ?item dc:date ?date . } ORDER BY DESC(?date) LIMIT 10"
    		"PREFIX wd: <http://www.wikidata.org/entity/> PREFIX wdt: <http://www.wikidata.org/prop/direct/> PREFIX schema: <http://schema.org/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> select distinct (max(?url) as ?titleLink) ?titleLinkLabel ?publication (min(?date) as ?issued) where { values ( ?author ?language ) { ( wd:Q36519983 \"en\" ) } ?work wdt:P50 ?author . ?work wdt:P1476 ?title . ?work wdt:P953 ?url . ?work wdt:P1433 ?pub . ?pub rdfs:label ?pubLang . filter(lang(?pubLang) = ?language) ?work wdt:P577 ?dateTime . bind(substr(str(?dateTime), 0, 11) as ?date) bind(str(?title) as ?titleLinkLabel) bind(str(?pubLang) as ?publication) } group by ?titleLink ?titleLinkLabel ?publication order by desc(?issued)"
    		//"PREFIX nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> SELECT ?word ?class WHERE { {SELECT ?word (count(?next) as ?ordinal) WHERE { ?word a nif:Word . ?word nif:nextWord* ?next . } group by ?word } ?word nif:has ?class }"
        );
    	try {
			sqv.visualize();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}