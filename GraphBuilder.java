import java.io.*;
import java.util.*;

/* When we want to create a graph of some specific object type,
 * we need to extend this abstract class. The child class will have to 
 * provide implementation for the "createOneThing()" method.
 *
 * Version 2017.11.13 v.1.0 
 * Fixed tgf 1-based vs Vector 0-based discrepancy; 
 * changed name to addArcIndex(int, int) to clarify signature
 *
 * */
abstract class GraphBuilder<T> {
  
  /*
   * To be overriden in the extension of this class 
   * (like the PersonGraphBuilder).
   * It will create and return an object of the specific 
   * type the graph will contain,       from the input string s.
   * */
  abstract T createOneThing(String s);
  
  /*
   * Reads from the input .tgf file, line by line.
   * Creates the vertex objects, and adds them to the graph.
   * Then, adds the connections between the vertices.
   * 
   * PRECONDITION: the input file is in .tgf format
   * */
  public AdjListsGraphPlus<T> build (String fileName) {
     AdjListsGraphPlus<T> g = new AdjListsGraphPlus<T>();
    //open the file
    try{ // to read from the tgf file
      Scanner scanner = new Scanner(new File(fileName));
      while ( !scanner.next().equals("#")) {
        String line = "";
        line = scanner.nextLine().trim();
        T thing = createOneThing(line);
        g.addVertex(thing); 
      }
      //read arcs
      while (scanner.hasNext()){
        int from = scanner.nextInt();
        int to = scanner.nextInt();
        g.addArc(from, to);
        //g.addArc(from-1, to-1); // Compensate tfg starting at index 1, new method name to clarify not the same as in Graph Interface
      }
      scanner.close();
    } catch (IOException ex) {
      System.out.println(fileName + " ***ERROR*** The file was not found: " + ex);
    }
    return g;
  }
  
}