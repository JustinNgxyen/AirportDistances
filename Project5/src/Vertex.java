//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// A class that defines the properties of a vertex in a graph. 
//

import java.util.Iterator;
import java.util.*;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> 
{
	
	private T label;
	private List<Edge> edgeList;
	private boolean visited;
	private VertexInterface<T> previousVertex;
	private double cost;
	
	public Vertex(T vertexLabel)
	{
		label = vertexLabel;
		edgeList = new ArrayList<>();
		visited = false;
		previousVertex = null;
		cost = 0;
	}
	
	/** Connects this vertex and a given vertex with an unweighted
	edge. The two vertices cannot be the same, and must not
	already have this edge between them. In a directed graph,
	the edge points toward the given vertex.
	@param endVertex A vertex in the graph that ends the edge.
	@return True if the edge is added, or false if not. */
	public boolean connect(VertexInterface<T> endVertex)
	{
		return connect(endVertex, 0);
	}
	
	/** Connects this vertex and a given vertex with a weighted edge.
	The two vertices cannot be the same, and must not already
	have this edge between them. In a directed graph, the edge
	points toward the given vertex.
	@param endVertex A vertex in the graph that ends the edge.
	@param edgeWeight A real-valued edge weight, if any.
	@return True if the edge is added, or false if not. */
	public boolean connect(VertexInterface<T> endVertex, double edgeWeight)
	{
		boolean result = false;
		
		if(!this.equals(endVertex))
		{
			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;
			
			while(!duplicateEdge && neighbors.hasNext())
			{
				VertexInterface<T> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
					duplicateEdge = true;
			}
			
			if(!duplicateEdge)
			{
				edgeList.add(new Edge(endVertex, edgeWeight));
				result = true;
			}
		}
		
		return result;
	}
	
	/** Creates an iterator of this vertex's neighbors by following
	all edges that begin at this vertex.
	@return An iterator of the neighboring vertices of this vertex. */
	public Iterator<VertexInterface<T>> getNeighborIterator()
	{
		return new NeighborIterator();
	}
	
	/** Sees whether this vertex has at least one neighbor.
	@return True if the vertex has a neighbor. */
	public boolean hasNeighbor()
	{
		return !edgeList.isEmpty();
	}
	
	/** Gets an unvisited neighbor, if any, of this vertex.
	@return Either a vertex that is an unvisited neighbor or null
	if no such neighbor exists. */
	public VertexInterface<T> getUnvisitedNeighbor()
	{
		VertexInterface<T> result = null;
		
		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
		while(neighbors.hasNext() && (result == null))
		{
			VertexInterface<T> nextNeighbor = neighbors.next();
			if(!nextNeighbor.isVisited())
				result = nextNeighbor;
		}
		
		return result;
	}
	
	/** Gets this vertex's label.
	@return The object that labels the vertex. */
	public T getLabel()
	{
		return label;
	}
	
	/** Gets the recorded predecessor of this vertex.
	@return Either this vertex's predecessor or null if no predecessor
	was recorded. */
	public VertexInterface<T> getPredecessor()
	{
		return previousVertex;
		
	}
	
	/** Sees whether a predecessor was recorded for this vertex.
	@return True if a predecessor was recorded. */
	public boolean hasPredecessor()
	{
		if(previousVertex == null)
			return false;
		else
			return true;
	}
	
	/** Records the previous vertex on a path to this vertex.
	@param predecessor The vertex previous to this one along a path. */
	public void setPredecessor(VertexInterface<T> predecessor)
	{
		previousVertex = predecessor;
	}
	
	/** Checks whether a vertex has been visited. */
	public boolean isVisited()
	{
		return visited == true;
	}
	
	/** Records the cost of a path to this vertex.
	@param newCost The cost of the path. */
	public void setCost(double newCost)
	{
		cost = newCost;
	}
	
	/** Gets the recorded cost of the path to this vertex.
	@return The cost of the path. */
	public double getCost()
	{
		return cost;
	}
	
	/** Creates an iterator of the weights of the edges to this
	vertex's neighbors.
	@return An iterator of edge weights for edges to neighbors of this vertex. */
	public Iterator<Double> getWeightIterator()
	{
		return new WeightIterator();
	}

	/** Marks this vertex as visited. */
	public void visit()
	{
		visited = true;
	}
	
	/** Removes this vertex's visited mark. */
	public void unvisit()
	{
		visited = false;
	}
	
	/** Determines whether two vertices are equal if their
	labels are equal. */
	@Override
	public boolean equals(Object other)
	{
		boolean result;
		if ((other == null) || (getClass() != other.getClass()))
			result = false;
		else
		{
			@SuppressWarnings("unchecked")
			Vertex<T> otherVertex = (Vertex<T>)other;
			result = label.equals(otherVertex.label);
		} // end if
		return result;
	}
	
	/* A private class that sets and retrieves an edge's weight and the vertex that ends it. */
	protected class Edge
	{
		private VertexInterface<T> vertex; 
		private double weight;
		
		protected Edge(VertexInterface<T> endVertex, double edgeWeight)
		{
			vertex = endVertex;
			weight = edgeWeight;
		} 
		protected Edge(VertexInterface<T> endVertex)
		{
			vertex = endVertex;
			weight = 0;
		} 
		protected VertexInterface<T> getEndVertex()
		{
			return vertex;
		} 
		protected double getWeight()
		{
			return weight;
		} 
	}
	
	/* A private class that traverse the edges in the vertex’s
	adjacency list. Then, using Edge’s method getEndVertex, next accesses the neighboring vertex and returns it. */
	private class NeighborIterator implements Iterator<VertexInterface<T>> 
	{
		private Iterator<Edge> edges;
		
		private NeighborIterator()
		{
			edges = edgeList.iterator();
		}
		
		public boolean hasNext()
		{
			return edges.hasNext();
		}
		
		public VertexInterface<T> next()
		{
			VertexInterface<T> nextNeighbor = null;
			
			if(edges.hasNext())
			{
				Edge edgeToNextNeighbor = edges.next();
				nextNeighbor = edgeToNextNeighbor.getEndVertex();
			}
			else
				throw new NoSuchElementException();
			
			return nextNeighbor;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
	}
	
	/* A private class that traverse the weights in the vertex’s
	adjacency list. Then, using Edge’s method getWeight, next accesses the neighboring edge's weight and returns it. */
	private class WeightIterator implements Iterator<Double>
	{
		private Iterator<Edge> edges;
		
		private WeightIterator()
		{
			edges = edgeList.iterator();
		}
		
		public boolean hasNext()
		{
			return edges.hasNext();
		}
		
		public Double next()
		{
			Double weight = null;

            if (edges.hasNext()) {
                Edge edgeWithWeight = edges.next();
                weight = edgeWithWeight.getWeight();
            } else {
                throw new NoSuchElementException();
            }

            return weight;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
	}

}
