import java.io.IOException;

public class TestPrim {
	public static void main(String [] args) {
		String vertexFile = args[0]; 
		String edgeFile = args[1]; 
		String start = args[2]; 
		MapReader myReader = new MapReader(vertexFile, edgeFile); 
		
		//try/catch block for IOExceptions
		try {
			@SuppressWarnings("static-access")
			Graph myGraph = myReader.readGraph(vertexFile, edgeFile);
			myGraph.computeEuclideanCosts(); 
			Graph spanningTree = myGraph.getMinimumSpanningTree(start);
	        DisplayGraph display = new DisplayGraph(spanningTree);
	        display.setVisible(true);
		} catch (IOException e) {
			System.out.println("Sorry, I couldn't find one or both of those files."); 
			e.printStackTrace();
		}
	}
}
