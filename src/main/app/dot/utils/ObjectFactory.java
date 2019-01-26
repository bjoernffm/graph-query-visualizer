package main.app.dot.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.app.dot.Node;
import main.app.dot.objects.EntityNode;
import main.app.dot.objects.SelectNode;

public class ObjectFactory {
	public static Node getNode(String id, String attributes) throws Exception
	{
		System.out.println(id);
		Map<String, String> attributeMap = parseAttributes(attributes);
		return getNode(id, attributeMap);
	}
	
	public static Node getNode(String id, Map<String, String> attributeMap) throws Exception
	{
		Node node = null;
		
		if (attributeMap.containsKey("dottype")) {
			if (attributeMap.get("dottype").equals("EntityNode")) {
				node = new EntityNode(id);
			} else if (attributeMap.get("dottype").equals("SelectNode")) {
				node = new SelectNode(id);
			}
		} else {
			node = new Node(id);
		}
		
		node = assignAttributes(node, attributeMap);
		
		return node;
	}
	
	public static Node assignAttributes (Node node, Map<String, String> attributeMap) throws Exception
	{
		// prepare map containing the available methods
		Map<String, Integer> methodMap = new HashMap<String, Integer>();
		
		Method[] methods = node.getClass().getMethods();
		for(int i = 0; i < methods.length; i++) {
			if (!methods[i].getName().startsWith("get")) {
				methodMap.put(methods[i].getName(), 1);
			}
		}
		
		for (Entry<String, String> entry: attributeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			if (key.equals("dottype")) {
				// ignore
			} else if (key.equals("fillcolor") && methodMap.containsKey("setFillcolor")) {
				node.setFillcolor(value);
			} else if (key.equals("shape") && methodMap.containsKey("setShape")) {
				node.setShape(value);
			} else if (key.equals("tooltip") && methodMap.containsKey("setTooltip")) {
				node.setTooltip(value);
			} else if (key.equals("style") && methodMap.containsKey("setStyle")) {
				node.setStyle(value);
			} else if (key.equals("label") && methodMap.containsKey("setLabel")) {
				node.setLabel(value);
			} else {
				throw new Exception("Attribute "+key+" is unknown or class "+node.getClass()+" is unable setter method");
			}
		}

		return node;
	}
	
	public static Map<String, String> parseAttributes(String attributes)
	{
		if (attributes.startsWith("[")) {
			attributes = attributes.substring(1);
		}
		if (attributes.endsWith("]")) {
			attributes = attributes.substring(0, attributes.length()-1);
		}

		Map<String, String> map = new HashMap<String, String>();
		String[] list = attributes.split(",");
		
		for(int i = 0; i < list.length; i++) {
			String[] pair = list[i].split("=");
			if (pair[1].startsWith("\"")) {
				pair[1] = pair[1].substring(1);
			}
			if (pair[1].endsWith("\"")) {
				pair[1] = pair[1].substring(0, pair[1].length()-1);
			}
			map.put(pair[0], pair[1]);
		}

		return map;
	}
}
