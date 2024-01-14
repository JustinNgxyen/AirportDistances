//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// A class that implements the ADT directed graph. 
//

import java.util.Iterator;

public class DirectedGraph<T extends Comparable<? super T>> implements GraphInterface<T> 
{
	
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;
	
	public DirectedGraph()
	{
		vertices = new HashedDictionary<>();
		edgeCount = 0;
	}
	
	/** Adds a given vertex to this graph.
	 @param vertexLabel An object that labels the new vertex and is
	 distinct from the labels of current vertices.
	 @return True if the vertex is added, or false if not. */
	public boolean addVertex(T vertexLabel)
	{
		VertexInterface<T> addOutcome = null;
		if(vertices.getValue(vertexLabel) == null)
		{
			addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
			return addOutcome == null;
		}
		return addOutcome == null;
	}
	
	/** Adds an unweighted edge between two given distinct vertices
	 that are currently in this graph. The desired edge must not
	 already be in the graph. In a directed graph, the edge points
	 toward the second vertex given.
	 @param begin An object that labels the origin vertex of the edge.
	 @param end An object, distinct from begin, that labels the end
	 vertex of the edge.
	 @return True if the edge is added, or false if not. */
	public boolean addEdge(T begin, T end)
	{
		return addEdge(begin, end, 0);
	}
	
	/** Adds a weighted edge between two given distinct vertices that
	 are currently in this graph. The desired edge must not already
	 be in the graph. In a directed graph, the edge points toward
	 the second vertex given.
	 @param begin An object that labels the origin vertex of the edge.
	 @param end An object, distinct from begin, that labels the end
	 vertex of the edge.
	 @param edgeWeight The real value of the edge's weight.
	 @return True if the edge is added, or false if not. */
	public boolean addEdge(T begin, T end, double edgeWeight)
	{
		boolean result = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if((beginVertex != null) && (endVertex != null))
			result = beginVertex.connect(endVertex, edgeWeight);
		if(result)
			edgeCount++;
		
		return result;
	}
	
	/** Sees whether an edge exists between two given vertices.
	 @param begin An object that labels the origin vertex of the edge.
	 @param end An object that labels the end vertex of the edge.
	 @return True if an edge exists. */
	public boolean hasEdge(T begin, T end)
	{
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if((beginVertex != null) && (endVertex != null))
		{
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
			
			while(!found && neighbors.hasNext())
			{
				VertexInterface<T> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
					found = true;
			}
		}
		
		return found;
	}
	
	/** Sees whether this graph is empty.
	 @return True if the graph is empty. */
	public boolean isEmpty()
	{
		return vertices.isEmpty();
	}
	
	/** Removes all vertices and edges from this graph
	resulting in an empty graph. */
	public void clear()
	{
		vertices.clear();
		edgeCount = 0;
	}
	
	/** Gets the number of vertices in this graph.
	 @return The number of vertices in the graph. */
	public int getNumberOfVertices()
	{
		return vertices.getSize();
	}
	
	/** Gets the number of edges in this graph.
	 @return The number of edges in the graph. */
	public int getNumberOfEdges()
	{
		return edgeCount;
	}
	
	/*Sets the fields visited, previousVertex, and cost to their initial
	values. To do so, the method uses one of the iterators declared in the interface DictionaryInterface. */
	protected void resetVertices()
	{
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		
		while(vertexIterator.hasNext())
		{
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		}
	}
	
	
	public PriorityQueueInterface<T> getBreadthFirstTraversal(T origin)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	public int getShortestPath(T begin, T end, StackInterface<T> path)
	{
		throw new UnsupportedOperationException("Not implemented.");
	} 
	
	/** Finds the least-cost path between two given vertices in this graph.
	 @param begin An object that labels the path's origin vertex.
	 @param end An object that labels the path's destination vertex.
	 @param path A stack of labels that is empty initially;
	 at the completion of the method, this stack contains
	 the labels of the vertices along the cheapest path;
	 the label of the origin vertex is at the top, and
	 the label of the destination vertex is at the bottom
	 @return The cost of the cheapest path. */
	public double getCheapestPath(T begin, T end, StackInterface<T> path)
	{
		resetVertices();
		boolean done = false;
		PriorityQueueInterface<EntryPQ> priorityQueue = new PriorityQueue<>();
		VertexInterface<T> originVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
        priorityQueue.add(new EntryPQ(originVertex, 0, null));
		
		while (!done && !priorityQueue.isEmpty())
		{
            EntryPQ frontEntry = priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex();
			if(!frontVertex.isVisited())
			{
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());
				
				if(frontVertex.equals(endVertex))
					done = true;
				else
				{
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
					Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
                    while(neighbors.hasNext())
                    {
                        VertexInterface<T> nextNeighbor = neighbors.next();
						Double weightOfEdgeToNeighbor = edgeWeights.next();
                        
	                    if(!nextNeighbor.isVisited())
	                    {
	                    	double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
	                        priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));  
	                    }
						
                    }
				}
			}
		}
		
		double pathCost = endVertex.getCost();
		path.push(endVertex.getLabel());
		VertexInterface<T> vertex = endVertex;
		while (vertex.hasPredecessor())
		{
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}
		return pathCost;
	}
	
	public StackInterface<T> getTopologicalOrder()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	public PriorityQueueInterface<T> getDepthFirstTraversal(T origin)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	/* Private class EntryPQ used for representing entries in the priority queue 
	during the graph traversal.*/
	private class EntryPQ implements Comparable<EntryPQ> {
		
		private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost;

        public EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        }

        public VertexInterface<T> getVertex() {
            return vertex;
        }

        public VertexInterface<T> getPredecessor() {
            return previousVertex;
        }
        
        public double getCost() {
            return cost;
        }

        @Override
        public int compareTo(EntryPQ other) {
            return Double.compare(this.cost, other.cost);
        }
        
        @Override
        public String toString()
		{
			return vertex.toString() + " " + cost;
		}
    }
	


}
