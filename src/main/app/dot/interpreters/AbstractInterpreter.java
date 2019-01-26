package main.app.dot.interpreters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.app.dot.Edge;
import main.app.dot.Node;
import main.app.dot.utils.ObjectFactory;


public abstract class AbstractInterpreter
{
	protected Map<String, Node> nodeMap = new HashMap<>();
	protected Map<String, Edge> edgeMap = new HashMap<>();

	public AbstractInterpreter(String dot)
	{		
		Scanner scanner = new Scanner(dot); 
        while (scanner.hasNext()) {
        	if (scanner.hasNext("subgraph")) {
        		scanner.next();
        		//System.out.println("Subgraph: "+scanner.next());
        	} else if (scanner.hasNext("\\\"[a-zA-z_0-9]+\\\"")) {
        		String arg1 = "unknown";
        		String arg2 = scanner.next();
        		String arg3 = scanner.next();

        		if (arg3.equals("->")) {
        			arg1 = "edge";
        			arg2 += ","+scanner.next();
        			arg3 = "";
        		} else {
        			arg1 = "node";
        		}
        		
        		while(true) {
        			arg3 += scanner.next();
        			if (arg3.endsWith("]")) {
        				break;
        			}
        		}

    			arg2 = arg2.replace("\"", "");
    			
        		if (arg1.equals("node")) {
        			try {
						Node node = ObjectFactory.getNode(arg2, arg3);
						this.nodeMap.put(arg2, node);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		} else if (arg1.equals("edge")) {
        			Edge edge;
					try {
						Node node1 = new Node("1");
						Node node2 = new Node("2");
						edge = ObjectFactory.getEdge(arg2, arg3);
						edge.setFrom(node1);
						edge.setTo(node2);
	        			this.edgeMap.put(arg2, edge);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	} else {
        		//System.out.println(scanner.next());
        		scanner.next();
        	}
        }
        
        scanner.close(); 
	}
}
