import javafoundations.*;
import java.io.*;
import java.util.*;

/**
 * CourseOfferings contains a AdjListsGraph<Course> of all of the Wellesley CS Department's course offferings.
 * The graph is directed, meaning that prerequisites of a course have an arc to that course.
 * 
 *
 * @author (Christina Chen & Zoe Allen)
 * @version (5/6/2018)
 */
public class CourseOfferings
{
    //instance variables
    private AdjListsGraphPlus<Course> courseGraph;

    /**
     * Constructor for objects of class CourseOfferings
     * 
     * Uses CourseGraphBuilder to build graph from courses
     * 
     * Reads from .tgf file courseOfferings.tgf
     * 
     */
    public CourseOfferings()
    {
        //initialize instance variable by building a graph of Courses
        CourseGraphBuilder cgb = new CourseGraphBuilder();
        
        courseGraph = cgb.build("courseOfferings.tgf");
    }
    
    /**
     * Gets the courseGraph instance variable
     * 
     * @return courseGraph
     */
    public AdjListsGraphPlus<Course> getGraph(){
        return courseGraph;
    }
    
    /**
     * gets Courses that user is eligible for every Course in a CourseList, without repeats.
     * 
     * @param userCourses list of courses to get successors of
     * @return CourseList of all Successors of userCourses
     */
    public CourseList getAllSuccessors(CourseList userCourses){
        CourseList sucList = new CourseList();
        
        //add 100-level courses that have not been selected, since they can always immediately be taken
        for (int i = 0; i <= 3; i++){
            if (!(userCourses.contains(courseGraph.getVertex(i)))){
                sucList.add(courseGraph.getVertex(i));
            }
        }
        
        //add successors of every course in course list without repeating
        for (int i = 0; i < userCourses.size(); i ++){
            LinkedList<Course> courseSucs = courseGraph.getSuccessors(userCourses.get(i));
            for (int j = 0; j < courseSucs.size(); j++){
                if (!(sucList.contains(courseSucs.get(j))) && 
                !(userCourses.contains(courseSucs.get(j)))){
                    sucList.addCourse(courseSucs.get(j));
                }
            }
        }
        
       
        return sucList;
    }
    
    /**
     * gets all courses from courseGraph in sorted order
     * @return CourseList of all courses in sorted order
     */
    public CourseList getAllCourses(){
        Vector<Course> verts = courseGraph.getVertices();
        
        Course introCourse = verts.elementAt(0);
        LinkedList<Course> firstTraversal = courseGraph.bfsTraversal(introCourse);
        
        CourseList sortedCourses = new CourseList();
        for (int i = 0; i< firstTraversal.size(); i++){
            sortedCourses.addCourse(firstTraversal.get(i));
        }
        
        //adds other root courses that bfs traversal cannot reach
        sortedCourses.addCourse(verts.elementAt(1));
        sortedCourses.addCourse(verts.elementAt(2));
        return sortedCourses;
    }
    
    /**
     * gets all courses that user cannot take immediately using list of previously taken courses
     * 
     * @param userCourses CourseList to go through
     * @return Courselist of future classes
     */
    public CourseList getFutures(CourseList userCourses){
          CourseList allCourses = this.getAllCourses();
          CourseList futureList = new CourseList();
          
          //goes through all courses, and only adds ones that have not been previously taken and can immediately be taken
          for (int i = 0; i < allCourses.size(); i++){
              if (!(userCourses.hasTaken(allCourses.get(i))) && 
              !(this.getAllSuccessors(userCourses).contains(allCourses.get(i)))){
                  futureList.addCourse(allCourses.get(i));
                }
            }
          
            return futureList;
    }
    
    /**
     * gets untaken required prerequisites of a future course.
     * 
     * @param c Course to get prerequisites for
     * @param userCourses CourseList of courses user has taken in the past
     * 
     * @return CourseList of prerequisites to be taken
     */
    public CourseList getPrereqs (Course c, CourseList userCourses){
        CourseList finalPreds = new CourseList();
        Vector<Course> allCourses = courseGraph.getVertices();
        
        int index = allCourses.indexOf(c);

        LinkedList<Course> predList = courseGraph.getPredecessors(courseGraph.getVertex(index));

        do{
            Course goalCourse = predList.get(0);
            
            if (!(courseGraph.isSink(goalCourse))){
                //add course to finalPreds
                if(!(finalPreds.contains(goalCourse)) && !(userCourses.contains(goalCourse))){

                    finalPreds.addCourse(goalCourse);

                }
                
                index = allCourses.indexOf(goalCourse);
                LinkedList<Course> tempPreds = courseGraph.getPredecessors(courseGraph.getVertex(index));
                
                for (int i = 0; i < tempPreds.size(); i++){
                    if (!(predList.contains(tempPreds.get(i)))){
                        predList.add(tempPreds.get(i));
                    }
                }

                predList.remove(0);
            }
            else {
                
                if (!(finalPreds.contains(goalCourse)) && !(userCourses.contains(goalCourse))){
                    finalPreds.addCourse(goalCourse);
                }
                predList.remove(0);
            }

        } while (!(predList.isEmpty()));
        
        //removes 100-levels
        for (int i = 0; i < 3; i++){

            if (finalPreds.contains(getAllCourses().get(i))){
                finalPreds.remove(0);
            }
        }
        
        return finalPreds;
    }
    
    
    /**
     * main method to test the class
     */
    public static void main(String[] args){
        CourseOfferings co1 = new CourseOfferings();
        
        CourseList test1 = new CourseList();

        Course c1 = co1.getGraph().getVertex(1);
        Course c2 = co1.getGraph().getVertex(3);
        Course c3 = co1.getGraph().getVertex(20);
        Course c4 = co1.getGraph().getVertex(24);
        System.out.println("User already took " + c1 + "\n" + c2);
        
        test1.addCourse(c1); test1.addCourse(c2);
        
        //CourseList successors = co1.getAllSuccessors(test1);
        //CourseList futures = co1.getFutures(test1);
        CourseList prereqs1 = co1.getPrereqs (c4, test1);
        System.out.println("Test 1:\n" + c4 + "'s prereqs: \n" + prereqs1);
        
        //CourseList prereqs2 = co1.getPrereqs (c4, test1);
        //System.out.println("Test 2:\n" +c4 + "'s prereqs: \n" + prereqs2);
        //System.out.println(futures);
    }
}
