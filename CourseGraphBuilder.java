import java.io.*;
import java.util.*;

 /**
 * Used to create a graph of Course objects. Extends GraphBuilder, implements
 * createOneThing for reading course objects from a tgf file. Course string must
 * be structured as follows: dept; number; title; semester;
 * 
 *
 * @author (Christina Chen)
 * @version (5/6/2018)
 */
  public class CourseGraphBuilder extends GraphBuilder<Course>{
  /* creates a Course out of the input string, and
   * returns it */
  public Course createOneThing(String s) {
    //scan string into components to create course object
    
    Scanner scan = new Scanner(s).useDelimiter("\\s*;\\s*");
    String courseDept = scan.next();
    int courseNum = scan.nextInt();
    String courseTitle = scan.next();
    String courseSem = scan.next();

    //create course object
    Course c = new Course(courseDept, courseNum, courseTitle, courseSem);
    
    return c; 
  }
}
