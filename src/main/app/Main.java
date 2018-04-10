package main.app;

import main.app.common.SimpleQueryVisualizer;
import main.app.http.Server;

public class Main {
    public static void main(String[] args) {
        Server svr = new Server();
        svr.start();
    }
}