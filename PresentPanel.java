import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Creates PresentPanel where users can see courses they can take immediately
 *
 * @author (Claire Cannatti)
 * @version (5/15/2018)
 */
public class PresentPanel extends JPanel
{
    // instance variables
    private CourseList pastCourses;
    private CourseList nextCourses;
    private LinkedList<JLabel> nextLabels;
    private CourseOfferings csCourses;
    private JLabel subhead, subhead2, subhead3;
    
    /**
     * Constructor for objects of class PresentPanel
     * 
     * @param past CourseList of past courses
     * @param courses CourseOfferings of all courses
     */
    public PresentPanel(CourseList past, CourseOfferings courses) 
    {
        setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
        setPreferredSize (new Dimension(700, 900));
        
        pastCourses = past;
        csCourses = courses;
        
        JLabel intro = new JLabel("Now we'll show you what ");
        JLabel intro2 = new JLabel("courses you're eligible ");
        JLabel intro3 = new JLabel("for right now!");
        JButton showMe = new JButton("SHOW ME");
        showMe.addActionListener(new openTabListener());
        
        intro.setFont (new Font ("Verdana", Font.BOLD, 30));
        intro2.setFont (new Font ("Verdana", Font.BOLD, 30));
        intro3.setFont (new Font ("Verdana", Font.BOLD, 30));
        
        intro.setAlignmentX (Component.CENTER_ALIGNMENT);
        intro2.setAlignmentX (Component.CENTER_ALIGNMENT);
        intro3.setAlignmentX (Component.CENTER_ALIGNMENT);
        showMe.setAlignmentX (Component.CENTER_ALIGNMENT);

        add (intro);
        add(intro2);
        add(intro3);
        
        add (Box.createRigidArea (new Dimension (0, 10)));
        add (showMe);
    }
    
    private class openTabListener implements ActionListener{ //occurs when showMe is pressed
        public void actionPerformed(ActionEvent e){
            removeAll();
            
            System.out.println("Past courses:\n" + pastCourses);
    
            JLabel header = new JLabel("What can you take now?");
            add (Box.createRigidArea (new Dimension (0, 10)));
            add (header);  
            add (Box.createRigidArea (new Dimension (0, 10)));

            JLabel subhead = new JLabel("you said you've already taken ");
            add(subhead);
            
            //adds all pastCourses
            for (int i = 0; i<pastCourses.size(); i++){
                JLabel p = new JLabel( pastCourses.get(i).toString());
                p.setFont (new Font ("Verdana", Font.PLAIN, 24));
                p.setAlignmentX (Component.CENTER_ALIGNMENT);
                add (p);
            }
    
            
            subhead2 = new JLabel( "so next semester you can take");
            subhead3 = new JLabel("any of the following:");
            subhead2.setFont (new Font ("Verdana", Font.PLAIN, 24));
            subhead2.setAlignmentX (Component.CENTER_ALIGNMENT);
            subhead3.setFont (new Font ("Verdana", Font.PLAIN, 24));
            subhead3.setAlignmentX (Component.CENTER_ALIGNMENT);
            
            add(subhead2);
            add(subhead3);
            
            add (Box.createRigidArea (new Dimension (0, 20)));
            
            header.setFont (new Font ("Verdana", Font.BOLD, 36));
            subhead.setFont (new Font ("Verdana", Font.PLAIN, 24));
            
            header.setAlignmentX (Component.CENTER_ALIGNMENT);
            subhead.setAlignmentX (Component.CENTER_ALIGNMENT);
    
            nextCourses = csCourses.getAllSuccessors(pastCourses);

            nextLabels = new LinkedList<JLabel>();
            int index = 0;
            
            //add labels of nextCourses
             while (index < nextCourses.size()){
                JLabel course = new JLabel(nextCourses.get(index).toString());
                course.setFont (new Font ("Verdana", Font.PLAIN, 16));
                course.setAlignmentX (Component.CENTER_ALIGNMENT);
                add (course);
                nextLabels.add(course);
                index ++;
            }
        
        }

    }
}
