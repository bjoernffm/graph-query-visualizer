import Common.SimpleQueryVisualizer;

public class Main {

	public static void main(String[] args) {
		String string = "SELECT ?title WHERE { <http://example.org/book/book1> <http://purl.org/dc/elements/1.1/title> ?title . }";
		SimpleQueryVisualizer qp = new SimpleQueryVisualizer();
		qp.visualize(string);
	}

}
