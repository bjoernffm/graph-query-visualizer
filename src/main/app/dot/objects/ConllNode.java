package main.app.dot.objects;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.app.dot.Node;
import main.app.dot.Subgraph;

public class ConllNode extends Node {
	
	protected Map<String, String> conllList = new HashMap<String, String>();

	public ConllNode(String id) throws UnsupportedEncodingException {
		super(id);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("box");
	}
	
	public String toDot()
	{
		ArrayList<String> labelList = new ArrayList<>();
		labelList.add("CONLL WORD "+this.getLabel()+"\\n\\n");

		for (Entry<String, String> entry: this.conllList.entrySet()) {
			labelList.add(entry.getKey()+": "+entry.getValue()+"\\l");
		}
		
		this.setLabel(String.join("", labelList));
		
		return super.toDot();
	}

	public String toString()
	{
		return this.toDot();
	}

	public void set(String key, String value) {
		this.conllList.put(key.trim(), value.trim());
	}
	
	public Map<String, String> getConllList()
	{
		return this.conllList;
	}
	
	public void migrate(ConllNode node)
	{
		Map<String, String> list = node.getConllList();
		for (Entry<String, String> entry: list.entrySet()) {
			this.set(entry.getKey(), entry.getValue());
		}
	}
}
