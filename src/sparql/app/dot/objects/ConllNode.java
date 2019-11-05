package sparql.app.dot.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sparql.app.dot.Node;

public class ConllNode extends Node {
	
	protected Map<String, String> conllList = new HashMap<String, String>();

	public ConllNode(String id) {
		super(id);
		
		this.setFillcolor("aliceblue");
		this.setStyle("filled");
		this.setShape("octagon");
	}
	
	public String toDot()
	{
		ArrayList<String> labelList = new ArrayList<>();
		labelList.add(this.getLabel()+"\\n\\n");

		for (Entry<String, String> entry: this.conllList.entrySet()) {
			labelList.add("conll:"+entry.getKey()+" "+entry.getValue()+"\\l");
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
