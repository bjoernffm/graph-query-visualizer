package main.app;

import main.app.common.SimpleQueryVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        /*Server svr = new Server();
        svr.start();*/
    	
    	SimpleQueryVisualizer sqv = new SimpleQueryVisualizer();
    	sqv.visualize("PREFIX foaf:   <http://xmlns.com/foaf/0.1/> SELECT ?mbox WHERE { ?x foaf:name \"Johnny Lee Outlaw\" . ?x foaf:mbox ?mbox }");
    }
}