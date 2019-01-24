package main.app.dot.interpreters;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public abstract class AbstractInterpreter {

	public AbstractInterpreter(String dot)
	{		
		Scanner scanner = new Scanner(dot); 
        while (scanner.hasNext()) {
        	if (scanner.hasNext("subgraph")) {
        		scanner.next();
        		System.out.println("Subgraph: "+scanner.next());
        	} else if (scanner.hasNext("\\\"[a-zA-z_0-9]+\\\"")) {
        		String arg1 = "unknown";
        		String arg2 = scanner.next();
        		String arg3 = scanner.next();

        		if (arg3.equals("->")) {
        			arg1 = "line";
        			arg2 += " -> "+scanner.next();
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

        		System.out.println(arg1);
        		System.out.println(arg2);
        		System.out.println(arg3);
        	} else {
        		//System.out.println(scanner.next());
        		scanner.next();
        	}
        }
        
        scanner.close(); 
	}
}
