import java.io.IOException;

public class TestBfs {
	public static void main(String[] args) {
		String vertexFile = args[0];
		String edgeFile = args[1];
		String city1 = args[2];
		String city2 = args[3];
		MapReader myReader = new MapReader(vertexFile, edgeFile);
		
		//read in the file and get the shortest path
		try {
			@SuppressWarnings("static-access")
			Graph myGraph = myReader.readGraph(vertexFile, edgeFile);
			Graph shortestPath = myGraph
					.getUnweightedShortestPath(city1, city2);
			DisplayGraph display = new DisplayGraph(shortestPath);
			display.setVisible(true);
			
		//throw an exception
		} catch (IOException e) {
			System.out
					.println("Sorry, I couldn't find one or both of those files.");
			e.printStackTrace();
		}

	}
}
