package main.app.http;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

public class Server {
    public void start() {
    	port(8080);
    	staticFiles.location("/main/resources/public");
    	
    	get("/", (request, response) -> {
    		Map<String, String> map = new HashMap<>();
            //map.put("message", "Hello World!");

            return this.render(map, "index");
    	});
    	
    	path("/api", () -> {
    		post("/queries", (request, response) -> {
    		    return "digraph { 1 [label=\"http://example.org/book/book1\"]; 2 [label=\"?title\"]; 1 -> 2 [ label=\"http://purl.org/dc/elements/1.1/title\" ]; }";
    		});
    	});
    }
    
    public String render(Map<String, String> model, String templatePath) {
        return new JadeTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
