import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Creates PastPanel where user inputs past courses using checkboxes
 *
 * @author (Claire Cannatti, Christina Chen)
 * @version (5/14/2018)
 */
public class PastPanel extends JPanel
{
    private LinkedList<JCheckBox> courseCheckboxes;
    private CourseList allCourses;
    private CourseList pastCourses;
    private CourseOfferings csCourses;
    
    /**
     * Constructor for objects of class PastPanel
     * 
     * @param past CourseList of past courses
     * @param courses CourseOfferings
     */
    public PastPanel(CourseList past, CourseOfferings courses) 
    {
        setPreferredSize (new Dimension(700, 900));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        pastCourses = past;
        csCourses = courses;
        
        //create header/title elements
        JLabel header = new JLabel("Welcome to DiscoverCS!");
        JLabel subhead = new JLabel("select the courses you've already taken"); 
        JLabel subhead2 = new JLabel(" and then submit at the bottom");
        
        JLabel easiesTitle = new JLabel("100-Levels");
        JLabel mediumTitle = new JLabel("200-Levels");
        JLabel hardTitle = new JLabel("300-Levels");

        //create submit button
        JButton submit = new JButton ("SUBMIT");
        
        header.setFont (new Font ("Verdana", Font.BOLD, 36));
        subhead.setFont (new Font ("Verdana", Font.PLAIN, 24));
        subhead2.setFont (new Font ("Verdana", Font.PLAIN, 24));
        
        easiesTitle.setFont (new Font ("Verdana", Font.BOLD, 20));
        mediumTitle.setFont (new Font ("Verdana", Font.BOLD, 20));
        hardTitle.setFont (new Font ("Verdana", Font.BOLD, 20));
        
        header.setAlignmentX (Component.CENTER_ALIGNMENT);
        subhead.setAlignmentX (Component.CENTER_ALIGNMENT);
        subhead2.setAlignmentX (Component.CENTER_ALIGNMENT);
         
        easiesTitle.setAlignmentX (Component.CENTER_ALIGNMENT);
        mediumTitle.setAlignmentX (Component.CENTER_ALIGNMENT);
        hardTitle.setAlignmentX (Component.CENTER_ALIGNMENT);
        submit.setAlignmentX (Component.CENTER_ALIGNMENT);
        

        add (header);  
        add (subhead);
        add (subhead2);
        
        add (Box.createRigidArea (new Dimension (0, 10)));
        add (easiesTitle);
        
        allCourses = csCourses.getAllCourses();
        courseCheckboxes = new LinkedList<JCheckBox>();
        int index = 0;
        
        while (allCourses.get(index).getCourseNumber() < 200){
            //create checkbox for each 100-level course
            JCheckBox courseBox = new JCheckBox(allCourses.get(index).toString());
            courseBox.setFont (new Font ("Verdana", Font.PLAIN, 16));
            courseBox.setAlignmentX (Component.CENTER_ALIGNMENT);
            
            //add checkbox to panel
            add (courseBox);
            
            //add checkbox to list of checkboxes
            courseCheckboxes.add(courseBox);
            index ++;
        }
        
        add (Box.createRigidArea (new Dimension (0, 5)));
        add (mediumTitle);
        
        while (allCourses.get(index).getCourseNumber() < 300){
            //create checkbox for each 200-level course
            JCheckBox courseBox = new JCheckBox(allCourses.get(index).toString());
            
            courseBox.setFont (new Font ("Verdana", Font.PLAIN, 16));
            courseBox.setAlignmentX (Component.CENTER_ALIGNMENT);
            
            //add checkbox to panel
            add (courseBox);
            
            //add checkbox to list of checkboxes
            courseCheckboxes.add(courseBox);
            index ++;
        }
        
        add (Box.createRigidArea (new Dimension (0, 5)));
        add (hardTitle);
        
        while (index < allCourses.size()){
            //create checkbox for each 300-level course
            JCheckBox courseBox = new JCheckBox(allCourses.get(index).toString());
            
            courseBox.setFont (new Font ("Verdana", Font.PLAIN, 16));
            courseBox.setAlignmentX (Component.CENTER_ALIGNMENT);
            
            //add checkbox to panel
            add (courseBox);
            
            //add checkbox to list of checkboxes
            courseCheckboxes.add(courseBox);
            index ++;
        }
        
        add (Box.createRigidArea (new Dimension (0, 10)));
        add (submit);
        submit.addActionListener(new ButtonListener());
   
    }
   
   private class ButtonListener implements ActionListener //occurs when submit button is pressed
   {
      public void actionPerformed (ActionEvent event)
      {
          //sees what checkboxes are selected
          for (int i = 0; i < courseCheckboxes.size(); i++){
              if (courseCheckboxes.get(i).isSelected()){
                pastCourses.add(allCourses.get(i));
                }
            }
          removeAll();
          revalidate();
          repaint();
          
          //feedback to prompt user to continue on to the next panels
          JLabel success = new JLabel("Courses added! Proceed to next tabs.");
          success.setFont (new Font ("Verdana", Font.PLAIN, 16));
          success.setAlignmentX (Component.CENTER_ALIGNMENT);
          add (success);

      }
   }
   
}
