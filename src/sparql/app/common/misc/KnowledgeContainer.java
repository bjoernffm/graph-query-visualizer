package sparql.app.common.misc;

import org.apache.jena.shared.PrefixMapping;

public class KnowledgeContainer {
	protected PrefixMapping prefixMap;

	public PrefixMapping getPrefixMap() {
		return prefixMap;
	}

	public void setPrefixMap(PrefixMapping prefixMap) {
		this.prefixMap = prefixMap;
	}
}
