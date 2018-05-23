import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javafoundations.ArrayIterator;
import javafoundations.LinkedQueue;
import javafoundations.LinkedStack;
import java.util.*;
import java.io.*;

/** DO NOT MODIFY THE CONSTRUCTORS AND THE FIRST METHOD ******************
 * The methods you will implement follow below.
 *  @author CS230 Staff (of the three first methods)
 *  @author Maura Shea n
 *  @version 2018.04.19
 */

public class AdjListsGraphPlus<T> extends AdjListsGraph<T> implements GraphPlus<T> {

    /**
     * Construct an empty graph.
     */
    public AdjListsGraphPlus() 
    {
        super();
    }

    /**
     * Construct a graph with the same vertices and edges as the given original.
     * @param original
     */
    public AdjListsGraphPlus(AdjListsGraphPlus<T> original) 
    {
        super(original);
    }

    public AdjListsGraphPlus(AdjListsGraph<T> original) 
    {
        super(original);
    }

    /**
     * DO NOT MODIFY!
     * Read a TGF file and create an AdjListsGraphPlus<String> from it.
     * 
     * @param tgfFile  The TGF file to read
     * @return A graph created from the TGF file
     * @throws FileNotFoundException if TGF file is not found.
     */
    public static AdjListsGraphPlus<String> fromTGF(String tgfFile) throws FileNotFoundException
    {
        AdjListsGraph<String> g = AdjListsGraphFromFile(tgfFile);
        AdjListsGraphPlus<String> gPlus = new AdjListsGraphPlus(g);
        return gPlus;
    }

    /**** IMPLEMENT THE METHODS BELOW ****/

    /****************************************************************** 
     * Creates a new graph that has all the same vertices and arcs as the original.
     * 
     * @return The new graph.
     ******************************************************************/
    public GraphPlus<T> clone() 
    {
        // Create a new graph from the original 
        GraphPlus<T> graphClone = new AdjListsGraphPlus(this);

        return graphClone;
    }

    /******************************************************************
     * Checks if a vertex is a sink (points to no other vertex)
     * 
     * @param vertex: the potential sink vertex
     * @return true if the vertex is a sink, false if it is not.
     * @throws IllegalArgumentException if given vertex is not in graph
     ******************************************************************/
    public boolean isSink(T vertex) 
    {
        // Checks if the vertex exists
        if(!this.containsVertex(vertex))
        {
            throw new IllegalArgumentException();
        }

        // Get the index of the vertex in vertices. 
        int index = vertices.indexOf(vertex);

        // Check if there are any arcs for the vertex
        if(arcs.get(index).size() == 0)
        {
            return true; // return true if there are no arcs 
        }

        return false; // return false otherwise 
    }

    /******************************************************************
     * Retrieves the vertices that are sinks. 
     * 
     * @return all the sinks in a linked list.
     ******************************************************************/
    public LinkedList<T> allSinks() 
    {
        // Create a LinkedList to hold the sinks 
        LinkedList<T> sinksList = new LinkedList<T>();

        // Loop through all the vertices
        for (int i = 0; i < vertices.size(); i++)
        {
            // Get the current vertex 
            T currentVertex = vertices.get(i);

            // Check if the current vertex is a sink
            if (isSink(currentVertex))
            {
                // Add the current vertex to sinksList if it is a sink
                sinksList.add(currentVertex);
            }
        }

        return sinksList;
    }

    /******************************************************************
     * Checks if a vertex is a source (no vertex points to it)
     * 
     * @param  vertex  The potential source vertex.
     * @return True if the vertex is a source, false if it is not.
     * @throws IllegalArgumentException if given vertex is not in graph.
     ******************************************************************/
    public boolean isSource(T vertex) 
    {
        // Check if the vertex exists
        if(!this.containsVertex(vertex))
        {
            throw new IllegalArgumentException();
        }

        // Loop through each arc 
        for (int i = 0; i < arcs.size(); i++)
        {
            // Check whether the vertex is in the current arc
            if (arcs.get(i).contains(vertex))
            {
                return false; // The vertex is not a source
            }
        }

        return true; // The vertex is a source
        //throw new UnsupportedOperationException();

    }

    /******************************************************************
     * Retrieves the vertices that are sources
     * 
     * @return All the sources in a linked list
     ******************************************************************/
    public LinkedList<T> allSources() 
    {
        // Create a LinkedList to hold the sources 
        LinkedList<T> sourceList = new LinkedList<T>();

        // Loop through all the vertices
        for (int i = 0; i < vertices.size(); i++)
        {
            // Get the current vertex
            T currentVertex = vertices.get(i);

            // Check if the vertex is a source
            if (isSource(currentVertex))
            {
                // Add the current vertex to sourceList if it is a source
                sourceList.add(currentVertex); 
            }
        }

        return sourceList;
    }

    /******************************************************************
     * Checks if a vertex is isolated - 
     * i.e., no vertices point to it and it points to no vertices.
     * 
     * @param  vertex  The vertex to check for isolation.
     * @return True if the vertex is isolated, false if it is not.
     * @throws IllegalArgumentException if given vertex is not in graph.
     ******************************************************************/
    public boolean isIsolated(T vertex) 
    {
        // Check if the vertex exists
        if(!this.containsVertex(vertex))
        {
            throw new IllegalArgumentException();
        } 

        // Check if the vertex IS a sink and IS NOT a source
        if ((isSink(vertex)) && (isSource(vertex)))
        {
            return true; // Return true iff the vertex is isolated.
        }

        return false; // the vertex is not isolated. 

        //throw new UnsupportedOperationException();
    }

    /******************************************************************
     * Returns a LinkedList contining a DEPTH first search traversal,  
     * starting at the given vertex. 
     * If the vertex is not valid, it returns an empty list.
     * 
     * @param  vertex  The starting vertex. 
     * @return  A linked list with the vertices in depth-first order.
     *****************************************************************/
    public LinkedList<T> dfsTraversal(T vertex) 
    {
        ArrayIterator<T> iter = dfsTraversalIter(vertex);

        LinkedList<T> depthFirst = new LinkedList<T>();

        while(iter.hasNext()){
            depthFirst.add(iter.next());
        }

        return depthFirst;
    }

    /******************************************************************
     * Returns an iterator contining a DEPTH first search traversal 
     * starting at the given vertex. 
     * If the vertex is not valid, it returns an empty iterator.
     * 
     * @param  vertex  The starting vertex. 
     * @return An ArrayIterator<T> with the vertices in depth-first order
     *****************************************************************/
    public ArrayIterator<T> dfsTraversalIter(T vertex)
    {
        //get index of starting vertex
        int startIndex = vertices.indexOf(vertex);
        
        //initialize stack to keep track of vertices traversed
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        
        //intialize iterator
        ArrayIterator<T> iterator = new ArrayIterator<T>();
        
        int numVertices = this.getNumVertices();
        
        //array of booleans to keep track of vertices visited
        boolean[] visited = new boolean[numVertices];
        
        if (!this.containsVertex(vertex)){
            return iterator;
        }
        
        for (int i = 0; i < numVertices; i++){
            visited[i] = false;
        }
        
        traversalStack.push(startIndex);
        iterator.add(vertices.get(startIndex));
        
        visited[startIndex] = true;
        
        boolean found;
        int currentVertexIndex;
        while (!traversalStack.isEmpty())
        {
            currentVertexIndex = traversalStack.peek();
            found = false;
            
            for (int i = 0; i < numVertices && !found; i++){
                T nextVertex = vertices.get(i);
                if (arcs.get(currentVertexIndex).contains(nextVertex) && !visited[i])
                {
                    traversalStack.push(i);
                    iterator.add(vertices.get(i));
                    visited[i] = true;
                    found = true;
                }
            }
            
            if (!found && !traversalStack.isEmpty()){
                traversalStack.pop();
            }
        }
        
        return iterator;
    }

    /******************************************************************
     * Returns a LinkedList contining a BREADTH first search traversal 
     * starting at the given vertex.
     * If the vertex is not valid, it returns an empty list. 
     * 
     * @param  vertex  The starting vertex.  
     * @return A linked list with the vertices in breadth-first order.
     *****************************************************************/
    public LinkedList<T> bfsTraversal(T vertex)
    {
        ArrayIterator<T> iter = bfsTraversalIter(vertex);

        LinkedList<T> breadthFirst = new LinkedList<T>();
        
        while(iter.hasNext()){
            breadthFirst.add(iter.next());
        }

        return breadthFirst;
    }

    /******************************************************************
     * Returns an iterator contining a BREADTH first search traversal 
     * starting at the given vertex. 
     * If the vertex is not valid, it returns an empty iterator.
     *
     * @param  vertex  The starting vertex. 
     * @return An ArrayIterator<T> with the vertices in breadth-first order.
     *****************************************************************/
    public ArrayIterator<T> bfsTraversalIter(T vertex) 
    {
        //get index of starting vertex
        int startIndex = vertices.indexOf(vertex);

        //initialize a LinkedQueue to keep track of vertices traversed
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();

        //initialize iterator
        ArrayIterator<T> iterator = new ArrayIterator<T>();

        //return empty iterator if vertex is invalid
        if (!this.containsVertex(vertex)){
            System.out.println("vertex not valid");
            
            return iterator;
            
        }

        int numVertices = this.getNumVertices();

        //array of booleans to keep track of vertices visited
        boolean[] visited = new boolean[numVertices];

        //populate visted w/ all false at first since no vertices have yet been visited
        for (int i = 0; i < numVertices; i++){
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        int currentVertexIndex;
        while (!traversalQueue.isEmpty())
        {
            currentVertexIndex = traversalQueue.dequeue();
            iterator.add(vertices.get(currentVertexIndex));

            for (int i = 0; i < numVertices; i++){
                T nextVertex = vertices.get(i);
                if (arcs.get(currentVertexIndex).contains(nextVertex) && !visited[i]){
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return iterator;
    }
    
    public Vector<T> getVertices(){
        return vertices;
    }
   
}
