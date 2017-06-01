import java.io.IOException;


public class TestDijkstra {
	public static void main(String [] args) {
		String vertexFile = args[0]; 
		String edgeFile = args[1]; 
		String city1 = args[2]; 
		String city2 = args[3]; 
		MapReader myReader = new MapReader(vertexFile, edgeFile); 
		
		//try/catch block for IOExceptions below
		try {
			@SuppressWarnings("static-access")
			Graph myGraph = myReader.readGraph(vertexFile, edgeFile);
			myGraph.computeEuclideanCosts(); 

			Graph shortestPath = myGraph.getWeightedShortestPath(city1, city2);
	        DisplayGraph display = new DisplayGraph(shortestPath);
	        display.setVisible(true);
	        //uncomment the line below to see the adjacency list
			//myGraph.printAdjacencyList(); 
		} catch (IOException e) {
			System.out.println("Sorry, I couldn't find one or both of those files."); 
			e.printStackTrace();
		} 
	}
}
