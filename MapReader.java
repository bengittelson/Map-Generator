import java.io.*;

public class MapReader {

	@SuppressWarnings("unused")
	private String vFile;
	@SuppressWarnings("unused")
	private String iFile;

	public MapReader(String vertices, String edges) {
		vFile = vertices;
		iFile = edges;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String vFile = args[0];
		String iFile = args[1];
		MapReader myReader = new MapReader(vFile, iFile);
		Graph myGraph;
		
		//try/catch block for IOExceptions
		try {
			myGraph = myReader.readGraph(vFile, iFile);
			DisplayGraph display = new DisplayGraph(myGraph);
			display.setVisible(true);
			myGraph.computeEuclideanCosts();
			myGraph.printAdjacencyList();

		} catch (IOException e) {
			System.out
					.println("Sorry, I couldn't find and read one of your files.");
		}

	}
	
	static Graph readGraph(String vertexfile, String edgefile)
			throws IOException {
		Graph myGraph = new Graph();
		BufferedReader inVertices = new BufferedReader(new FileReader(
				vertexfile));
		
		//read in the vertices
		while (inVertices.ready()) {
			String currentLine = inVertices.readLine();
			String[] lineSplit = currentLine.split(",");
			Vertex addVertex = new Vertex(lineSplit[0],
					Integer.parseInt(lineSplit[1]),
					Integer.parseInt(lineSplit[2]));
			myGraph.addVertex(addVertex);
		}
		inVertices.close();

		//read in the edges
		BufferedReader inEdges = new BufferedReader(new FileReader(edgefile));
		while (inEdges.ready()) {
			String currentLine = inEdges.readLine();
			String[] lineSplit = currentLine.split(",");
			myGraph.addUndirectedEdge(lineSplit[0], lineSplit[1], 1.0);
		}
		inEdges.close();
		return myGraph;
	}

}
