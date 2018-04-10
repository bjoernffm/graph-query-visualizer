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
            map.put("message", "Hello World!");

            // The hello.jade template file is in the resources/templates directory
            String tpl = this.render(map, "index");
            return tpl;
            // return "works!";
    	});
    }
    
    public String render(Map<String, String> model, String templatePath) {
        return new JadeTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
