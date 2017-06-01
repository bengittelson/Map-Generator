import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Set;
import java.lang.Math;

@SuppressWarnings("unused")
public class Graph {

	// Keep a fast index to nodes in the map
	protected Map<String, Vertex> vertices;
	// keep track of the edges
	protected LinkedList<Edge> edges;

	/**
	 * Construct an empty Graph.
	 */
	public Graph() {
		vertices = new HashMap<String, Vertex>();
		edges = new LinkedList<Edge>();
	}

	public void addVertex(String name) {
		Vertex v = new Vertex(name);
		addVertex(v);
	}

	public void addVertex(Vertex v) {
		if (vertices.containsKey(v.name))
			throw new IllegalArgumentException(
					"Cannot create new vertex with existing name.");
		vertices.put(v.name, v);
	}

	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	public Vertex getVertex(String s) {
		return vertices.get(s);
	}

	/**
	 * Add a new edge from u to v. Create new nodes if these nodes don't exist
	 * yet. This method permits adding multiple edges between the same nodes.
	 * 
	 * @param u
	 *            the source vertex.
	 * @param w
	 *            the target vertex.
	 */
	public void addEdge(String nameU, String nameV) {
		if (!vertices.containsKey(nameU))
			addVertex(nameU);
		if (!vertices.containsKey(nameV))
			addVertex(nameV);
		Vertex sourceVertex = vertices.get(nameU);
		Vertex targetVertex = vertices.get(nameV);
		Edge newEdge = new Edge(sourceVertex, targetVertex, 1.0);
		sourceVertex.addEdge(newEdge);

		// added the code below:
		edges.add(newEdge);
	}

	/**
     * 
     */
	public void printAdjacencyList() {
		for (String u : vertices.keySet()) {
			StringBuilder sb = new StringBuilder();
			sb.append(u);
			sb.append(" -> [ ");
			for (Edge e : vertices.get(u).getEdges()) {
				sb.append(e.targetVertex.name);
				sb.append("(");
				sb.append(e.cost);
				sb.append(") ");
			}
			sb.append("]");
			System.out.println(sb.toString());
		}
	}

	void addEdge(String s, String t, Double cost) {
		if (!vertices.containsKey(s))
			addVertex(s);
		if (!vertices.containsKey(t))
			addVertex(t);
		Vertex sourceVertex = vertices.get(s);
		Vertex targetVertex = vertices.get(t);
		Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
		sourceVertex.addEdge(newEdge);
		// added the code below:
		edges.add(newEdge);
	}

	// add the same edge in both directions
	public void addUndirectedEdge(String s, String t, Double cost) {
		addEdge(s, t, cost);
		addEdge(t, s, cost);
	}

	public void computeEuclideanCosts() {
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);

			// retrieve the appropriate vertices
			Vertex source = e.sourceVertex;
			Vertex target = e.targetVertex;
			double x = (source.posX - target.posX);
			double y = (source.posY - target.posY);
			double EuCost = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
			e.cost = EuCost;
		}
	}

	public void doBfs(String s) {
		Queue<Vertex> searchQueue = new LinkedList<Vertex>();
		Set<String> KeySet = vertices.keySet();
		for (String k : KeySet) {
			// set costs to 0, visited flags to false, and previous pointers to
			// null
			vertices.get(k).pathCost = Integer.MAX_VALUE;
			vertices.get(k).weightedCost = Integer.MAX_VALUE;
			vertices.get(k).visited = false;
			vertices.get(k).previousPointer = null;
		}

		Vertex start = vertices.get(s);
		searchQueue.add(start);
		start.visited = true;
		int cost = 0;

		// iterate through the Queue
		while (searchQueue.size() > 0) {
			Vertex u = searchQueue.poll();
			for (int i = 0; i < u.getEdges().size(); i++) {
				Vertex currentVertex = u.getEdges().get(i).targetVertex;
				if (currentVertex.visited == false) {
					cost++;
					currentVertex.pathCost = cost;
					currentVertex.visited = true;
					currentVertex.previousPointer = u;
					start.vertices.put(currentVertex, currentVertex.pathCost);
					searchQueue.add(currentVertex);
				}
			}
		}
	}

	public Graph getUnweightedShortestPath(String s, String t) {
		Graph pathGraph = new Graph();
		doBfs(s);
		Vertex currentVertex = vertices.get(t);

		// iterate through the vertices
		while (currentVertex != vertices.get(s)) {
			if (currentVertex.equals(vertices.get(t))) {
				Vertex putVertex = new Vertex(currentVertex.name,
						currentVertex.posX, currentVertex.posY);
				pathGraph.addVertex(putVertex);
			}
			Vertex putVertex = new Vertex(currentVertex.previousPointer.name,
					currentVertex.previousPointer.posX,
					currentVertex.previousPointer.posY);

			pathGraph.addVertex(putVertex);
			pathGraph.addEdge(currentVertex.previousPointer.toString(),
					currentVertex.toString());
			currentVertex = currentVertex.previousPointer;
		}

		Vertex lastVertex = new Vertex(vertices.get(s).name,
				vertices.get(s).posX, vertices.get(s).posY);
		return pathGraph;
	}

	public void doDijkstra(String s) {
		Queue<Vertex> myPQueue = new PriorityQueue<Vertex>();

		// set v.cost to infinity v.visited to false, and v.previousPointer to
		// null for each vertex
		Set<String> KeySet = vertices.keySet();
		for (String k : KeySet) {
			vertices.get(k).pathCost = Integer.MAX_VALUE;
			vertices.get(k).weightedCost = Integer.MAX_VALUE;
			vertices.get(k).visited = false;
			vertices.get(k).previousPointer = null;
		}

		Vertex start = vertices.get(s);
		start.weightedCost = 0;
		start.visited = true;
		myPQueue.add(start);

		// iterate until the searchQueue is empty
		while (myPQueue.size() > 0) {
			Vertex u = myPQueue.poll();
			u.visited = true;
			for (int i = 0; i < u.getEdges().size(); i++) {
				Vertex currentVertex = u.getEdges().get(i).targetVertex;
				if (!currentVertex.visited) {

					// compare costs
					if (u.weightedCost + u.getEdges().get(i).cost < currentVertex.weightedCost) {
						currentVertex.weightedCost = u.weightedCost
								+ u.getEdges().get(i).cost;
						currentVertex.previousPointer = u;
					}
					myPQueue.add(currentVertex);
				}
			}
		}
	}

	// Duluth to Denver produces different weighted and unweighted shortest
	// paths.
	// The unweighted path is Duluth --> Helena --> Denver, and the weighted
	// shortest path is Duluth --> Omaha --> Denver
	public Graph getWeightedShortestPath(String s, String t) {
		Graph pathGraph = new Graph();
		doDijkstra(s);
		Vertex currentVertex = vertices.get(t);
		while (currentVertex != vertices.get(s)) {
			// special case for the first vertex so that its coordinates are
			// correct
			if (currentVertex.equals(vertices.get(t))) {
				Vertex putVertex = new Vertex(currentVertex.name,
						currentVertex.posX, currentVertex.posY);
				pathGraph.addVertex(putVertex);
			}

			// make new vertices with the same x pos and y pos as the originals
			Vertex putVertex = new Vertex(currentVertex.previousPointer.name,
					currentVertex.previousPointer.posX,
					currentVertex.previousPointer.posY);
			pathGraph.addVertex(putVertex);
			pathGraph.addEdge(currentVertex.previousPointer.toString(),
					currentVertex.toString());
			currentVertex = currentVertex.previousPointer;
		}
		return pathGraph;
	}

	public void doPrim(String s) {
		Queue<Vertex> myPrimQueue = new PriorityQueue<Vertex>();

		// set the pathCost, weightedCost, visited, and previousPointer
		// variables to infinity, false, and null
		Set<String> KeySet = vertices.keySet();
		for (String k : KeySet) {
			vertices.get(k).pathCost = Integer.MAX_VALUE;
			vertices.get(k).weightedCost = Integer.MAX_VALUE;
			vertices.get(k).visited = false;
			vertices.get(k).previousPointer = null;
		}

		Vertex primStart = vertices.get(s);
		primStart.weightedCost = 0;
		primStart.visited = true;
		myPrimQueue.add(primStart);

		// iterate until the queue is empty
		while (myPrimQueue.size() > 0) {
			Vertex uPrim = myPrimQueue.poll();
			uPrim.visited = true;
			for (int i = 0; i < uPrim.getEdges().size(); i++) {

				// retrieve the current vertex
				Vertex currentVertex = uPrim.getEdges().get(i).targetVertex;
				if (!currentVertex.visited) {
					double cost1 = uPrim.getEdges().get(i).cost;
					double cost2 = currentVertex.weightedCost;

					// compare costs
					if (cost1 < cost2) {
						currentVertex.weightedCost = uPrim.getEdges().get(i).cost;
						currentVertex.previousPointer = uPrim;
					}
					myPrimQueue.add(currentVertex);
				}
			}
		}
	}

	public Graph getMinimumSpanningTree(String s) {
		doPrim(s);
		Graph SpanningTree = new Graph();
		Set<String> KeySet = this.vertices.keySet();
		for (String k : KeySet) {
			// create a new vertex with same name/position as the original
			Vertex newVertex = new Vertex(k, vertices.get(k).posX,
					vertices.get(k).posY);
			SpanningTree.addVertex(newVertex);
		}

		// iterate through the key set and check for back pointers
		for (String j : KeySet) {
			if (vertices.get(j).previousPointer != null) {
				SpanningTree.addUndirectedEdge(vertices.get(j).toString(),
						vertices.get(j).previousPointer.toString(), 1.0);
			}
		}
		return SpanningTree;
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		g.addVertex(new Vertex("v0", 0, 0));
		g.addVertex(new Vertex("v1", 0, 1));
		g.addVertex(new Vertex("v2", 1, 0));
		g.addVertex(new Vertex("v3", 1, 1));
		g.addVertex(new Vertex("v4", 0, 2));

		g.addEdge("v0", "v1");
		g.addEdge("v1", "v2");
		g.addEdge("v2", "v3");
		g.addEdge("v3", "v0");
		g.addUndirectedEdge("v0", "v2", 2.0);
		g.addEdge("v1", "v3", 1.0);
		g.addEdge("v1", "v4", 1.0);
		g.addEdge("v4", "v2", 3.0);

		g.printAdjacencyList();

		DisplayGraph display = new DisplayGraph(g);
		display.setVisible(true);
	}

}
