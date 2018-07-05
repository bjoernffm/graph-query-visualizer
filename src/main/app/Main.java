package main.app;

import main.app.common.SimpleQueryVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        /*Server svr = new Server();
        svr.start();*/
    	SimpleQueryVisualizer sqv = new SimpleQueryVisualizer(
    		"PREFIX  dc:  <http://purl.org/dc/elements/1.1/> PREFIX  ns:  <http://example.org/ns#> SELECT ?title ?price WHERE   { ?x ns:price ?price . FILTER (?price < ?priceToAvoid) ?x dc:title ?title . }"
    	);
    	sqv.visualize();
    }
}