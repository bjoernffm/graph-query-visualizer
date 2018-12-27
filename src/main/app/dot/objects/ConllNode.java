package main.app.dot.objects;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import main.app.dot.Node;

public class ConllNode extends Node {
	
	protected String conllId = "";
	protected String conllForm = "";
	protected String conllLemma = "";
	protected String conllUpos = "";
	protected String conllXpos = "";
	protected String conllFeats = "";
	protected String conllHead = "";
	protected String conllDeprel = "";
	protected String conllDeps = "";
	protected String conllMisc = "";

	public ConllNode(String id) throws UnsupportedEncodingException {
		super(id);
		
		this.setFillcolor("dodgerblue");
		this.setStyle("filled");
		this.setShape("box");
	}
	
	public String toDot()
	{
		ArrayList<String> labelList = new ArrayList<>();
		labelList.add("CONLL WORD\\n\\n");

		if (!this.conllId.trim().equals("")) {
			labelList.add("ID: "+this.conllId.trim()+"\\l");
		}
		if (!this.conllForm.trim().equals("")) {
			labelList.add("FORM: "+this.conllForm.trim()+"\\l");
		}
		if (!this.conllLemma.trim().equals("")) {
			labelList.add("LEMMA: "+this.conllLemma.trim()+"\\l");
		}
		if (!this.conllUpos.trim().equals("")) {
			labelList.add("UPOS: "+this.conllUpos.trim()+"\\l");
		}
		if (!this.conllXpos.trim().equals("")) {
			labelList.add("XPOS: "+this.conllXpos.trim()+"\\l");
		}
		if (!this.conllFeats.trim().equals("")) {
			labelList.add("FEATS: "+this.conllFeats.trim()+"\\l");
		}
		if (!this.conllHead.trim().equals("")) {
			labelList.add("HEAD: "+this.conllHead.trim()+"\\l");
		}
		if (!this.conllDeprel.trim().equals("")) {
			labelList.add("DEPREL: "+this.conllDeprel.trim()+"\\l");
		}
		if (!this.conllDeps.trim().equals("")) {
			labelList.add("DEPS: "+this.conllDeps.trim()+"\\l");
		}
		if (!this.conllMisc.trim().equals("")) {
			labelList.add("MISC: "+this.conllMisc.trim()+"\\l");
		}
		
		this.setLabel(String.join("", labelList));
		
		return super.toDot();
	}

	public String toString()
	{
		return this.toDot();
	}

	public void setConllId(String conllId) {
		this.conllId = conllId;
	}

	public void setConllForm(String conllForm) {
		this.conllForm = conllForm;
	}

	public void setConllLemma(String conllLemma) {
		this.conllLemma = conllLemma;
	}

	public void setConllUpos(String conllUpos) {
		this.conllUpos = conllUpos;
	}

	public void setConllXpos(String conllXpos) {
		this.conllXpos = conllXpos;
	}

	public void setConllFeats(String conllFeats) {
		this.conllFeats = conllFeats;
	}

	public void setConllHead(String conllHead) {
		this.conllHead = conllHead;
	}

	public void setConllDeprel(String conllDeprel) {
		this.conllDeprel = conllDeprel;
	}

	public void setConllDeps(String conllDeps) {
		this.conllDeps = conllDeps;
	}

	public void setConllMisc(String conllMisc) {
		this.conllMisc = conllMisc;
	}
}
