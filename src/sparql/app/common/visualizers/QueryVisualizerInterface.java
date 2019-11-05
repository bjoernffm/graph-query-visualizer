package sparql.app.common.visualizers;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface QueryVisualizerInterface {
	public List<String> visualize() throws UnsupportedEncodingException, Exception;
}
