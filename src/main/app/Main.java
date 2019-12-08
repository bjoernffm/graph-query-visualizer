package main.app;

import sparql.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        svr.start();
    }
}