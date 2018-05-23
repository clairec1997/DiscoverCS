import java.util.*;

/**
 * CS 230 Final Project
 * @author Zoe Allen
 * @version 5/6/18
 * 
 * The CourseList class creates a list of Courses.
 */
public class CourseList extends LinkedList<Course>
{
    /**
     * Add Course in sorted order
     * 
     * @param c - a Course
     */
    public void addCourse(Course c){
        int insertIndex = this.size();
        
        while ((insertIndex > 0) && (c.compareTo(this.get(insertIndex -1)) < 0)){
            insertIndex --;
        }
        
        this.add(insertIndex, c);
    }
    
    /**
     * Check to see if the user has taken a given course
     * (Will use this method in Decision Tree)
     * 
     * @param c - a Course
     * @return boolean - true if the user has taken the course and false if not
     */
    public boolean hasTaken (Course c){
        for (int i = 0; i< this.size(); i++){
            if (this.get(i).compareTo(c) == 0){
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Returns a nicely formatted String
     * 
     * @return String - nicely formatted String
     */
    public String toString(){
        String s = "Courses in CourseList:\n";
        for (int i = 0; i < this.size(); i++){
            s += this.get(i) + "\n";
        }
        return s;
    }
    
    /**
     * Main method for testing 
     * Tests addCourse (in sorted order), removeCourse, and hasTaken
     */
    public static void main(String[] args){
        CourseList test1 = new CourseList();
        System.out.println("Test addCourse method (make sure courses are added in sorted order):");
        Course c1 = new Course("CS", 111, "Computer Programming and Problem Solving", "both");
        Course c2 = new Course("CS", 115, "Computing for the Socio-Techno Web", "both");
        Course c3 = new Course("CS", 220, "Human-Computer Interaction", "both");
        Course c4 = new Course("CS", 230, "Data Structures", "both");
        Course c5 = new Course("CS", 231, "Fundamental Algorithms", "both");
        Course c6 = new Course("CS", 235, "Languages and Automata", "both");
        Course c7 = new Course("CS", 240, "Foundations of Computer Systems with Laboratory", "both");
        
        
        test1.addCourse(c1); test1.addCourse(c3); test1.addCourse(c2); test1.addCourse(c7);
        test1.addCourse(c4); test1.addCourse(c6); test1.addCourse(c5);
        System.out.println(test1);
        
        System.out.println("\n\nTest removeCourse (remove 111, 230, 240):");
        test1.remove(c1); test1.remove(c4); test1.remove(c7);
        System.out.println(test1);
        
        System.out.println("\n\nTest hasTaken method (expected: true then false):");
        System.out.println(test1.hasTaken(c2));
        System.out.println(test1.hasTaken(c1));
        
    }
}
