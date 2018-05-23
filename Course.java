
/**
 * Represents a Course object.
 *
 * @author Claire Cannatti
 * @version 2018.4.29
 */
public class Course implements Comparable<Course>
{
    // instance variables
    private String dept;
    private int courseNum;
    private String name;
    private String semester;

    /**
     * Constructor for objects of class Course
     */
    public Course(String department, int courseNumber, String courseName, String sem)
    {
        //initialise instance variables
        dept = department;
        courseNum = courseNumber;
        name = courseName;
        semester = sem;
    }
    
    /**
     * returns the department of the course (usually will be CS)
     * 
     * @return Course department
     */
    public String getCourseDept(){
        return dept;
    }
    
    /**
     * returns the course number
     * 
     * @return Course number
     */
    public int getCourseNumber(){
        return courseNum;
    }
    
    /**
     * returns the course name
     * 
     * @return Course name
     */
    public String getCourseName(){
        return name;
    }
    
    /**
     * CompareTo method to compare courses to eachother.
     * Used to sort in CourseList
     * 
     * @param p - a Course
     * @return int - returns -1,1, or 0
     */
    public int compareTo(Course p){
        if (this.getCourseNumber() < p.getCourseNumber()) {
            return -1;
        } else if (this.getCourseNumber() > p.getCourseNumber()) {
            return 1;
        } else{
            return 0;
        }
    }
    
    /**
     * returns a string representation of the course
     * 
     * @return String representation of Course
     */
    public String toString(){
        return dept + " " + courseNum + ": " + name;
    }
    
}
