import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Creates FuturePanel where user can select future courses they want to take and see the necessary
 * prerequisites.
 *
 * @author (Christina Chen, Zoe Allen, Claire Cannatti)
 * @version (5/15/2018)
 */
public class FuturePanel extends JPanel
{
    // instance variables
    private CourseList pastCourses;
    private CourseList futures;
    private LinkedList<JRadioButton> futureLabels;
    private CourseOfferings csCourses;
    private JLabel prereqs;
    private FuturePanel tab;
    ArrayList<JRadioButton> buttonList;
    private LinkedList<JLabel> prerequisites;
    private JPanel preqs;
    private JButton tryAgain;
    
    /**
     * Constructor for objects of class PastPanel
     */
    public FuturePanel(CourseList past, CourseOfferings courses) 
    {
        setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
        setPreferredSize (new Dimension(700, 900));

        pastCourses = past;
        csCourses = courses;
        prerequisites = new LinkedList<JLabel>();
        
        JLabel intro = new JLabel("Now we'll show you what you");
        JLabel intro2 = new JLabel("need to take for each course you're");
        JLabel intro3 = new JLabel("not currently eligible for!");
        JButton showMe = new JButton("SHOW ME");
        
        
        showMe.addActionListener(new OpenTabListener());
        
        intro.setFont (new Font ("Verdana", Font.BOLD, 30));
        intro2.setFont (new Font ("Verdana", Font.BOLD, 30));
        intro3.setFont (new Font ("Verdana", Font.BOLD, 30));
        
        intro.setAlignmentX (Component.CENTER_ALIGNMENT);
        intro2.setAlignmentX (Component.CENTER_ALIGNMENT);
        intro3.setAlignmentX (Component.CENTER_ALIGNMENT);
        showMe.setAlignmentX (Component.CENTER_ALIGNMENT);
        
        //add all elements to panel
        add (intro);
        add (intro2);
        add (intro3);
        add (Box.createRigidArea (new Dimension (0, 10)));
        add (showMe);
        preqs = new JPanel();
       
    }
    
    private class OpenTabListener implements ActionListener{ //resets the tab with updated content upon pressing showMe
        public void actionPerformed(ActionEvent e) {
            removeAll();

            JLabel header = new JLabel("What prerequisites do you need?");
            JLabel subhead = new JLabel("pick a course you want to take");  
            JLabel subhead2 = new JLabel("that you can't take immediately");
            JLabel subhead3 = new JLabel("and we'll tell you what prerequisites");
            JLabel subhead4 = new JLabel("you need to fulfill first");
            
            JLabel medium = new JLabel("200-Levels");
            JLabel hard = new JLabel("300-Levels");
            
            header.setFont (new Font ("Verdana", Font.BOLD, 36));
            subhead.setFont (new Font ("Verdana", Font.PLAIN, 24));
            subhead2.setFont (new Font ("Verdana", Font.PLAIN, 24));
            subhead3.setFont (new Font ("Verdana", Font.PLAIN, 24));
            subhead4.setFont (new Font ("Verdana", Font.PLAIN, 24));
            
            medium.setFont (new Font ("Verdana", Font.BOLD, 20));
            hard.setFont (new Font ("Verdana", Font.BOLD, 20));
            
            header.setAlignmentX (Component.CENTER_ALIGNMENT);
            subhead.setAlignmentX (Component.CENTER_ALIGNMENT);
            subhead2.setAlignmentX (Component.CENTER_ALIGNMENT);
            subhead3.setAlignmentX (Component.CENTER_ALIGNMENT);
            subhead4.setAlignmentX (Component.CENTER_ALIGNMENT);
            medium.setAlignmentX (Component.CENTER_ALIGNMENT);
            hard.setAlignmentX (Component.CENTER_ALIGNMENT);
    
            add (Box.createRigidArea (new Dimension (0, 10)));
            add (header); 

            add (Box.createRigidArea (new Dimension (0, 10)));
            add (subhead);
            add (subhead2);
            add (subhead3);
            add (subhead4);
            add (Box.createRigidArea (new Dimension (0, 10)));
            
            futures = csCourses.getFutures(pastCourses);
            int index = 0;
            
            ButtonGroup futureGroup = new ButtonGroup();
            buttonList = new ArrayList<JRadioButton>();
            
            FutureCourseListener listener = new FutureCourseListener();

            for(int i = 0; i < futures.size(); i++){
                //create label for radio buttons
                String courseLabel = futures.get(i).toString();
                
                //create button with courseLabel
                JRadioButton button = new JRadioButton(courseLabel);
                button.setFont (new Font ("Verdana", Font.PLAIN, 16));
                button.setAlignmentX (Component.CENTER_ALIGNMENT);
                
                //add button to list of radio buttons
                buttonList.add(button);
                
                //add the same listener to each button
                button.addActionListener(listener);
                
                //add button to same button group so only one can be selected out of the group
                futureGroup.add (buttonList.get(i));
                
                //add button just created to panel
                add(buttonList.get(i));
            }
            
            //submit button
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ButtonListener());
            add (Box.createRigidArea (new Dimension (0, 10)));
            submitButton.setAlignmentX (Component.CENTER_ALIGNMENT);
            add(submitButton);
        }
        
        public void focusLost(FocusEvent e){
            removeAll();
        }
        
    }
    
    private class FutureCourseListener implements ActionListener //occurs when radio button is selected
    {
        public void actionPerformed (ActionEvent event){
            Object source = event.getSource();
            
            //gets the index of the specific button that was selected
            int index = buttonList.indexOf(source);
            
            add (Box.createRigidArea (new Dimension (0, 10)));
            
            prerequisites = new LinkedList<JLabel>();
            
            //gets the prerequisites of the course that corresponds to the radio button selecte
            CourseList pres = csCourses.getPrereqs(futures.get(index), pastCourses);
            
            for (int i = 0; i < pres.size();i++){
                JLabel newPre = new JLabel(pres.get(i).toString());
                newPre.setFont (new Font ("Verdana", Font.PLAIN, 20));
                newPre.setAlignmentX (Component.CENTER_ALIGNMENT);
                newPre.setVisible(true);
                prerequisites.add (newPre);
            }
      }
      
    }
    
    private class ButtonListener implements ActionListener{//occurs after submit is clicked
          public void actionPerformed(ActionEvent e){
            removeAll();
            revalidate();
            repaint();
            
            JLabel header = new JLabel("You need to take:");

            header.setFont(new Font ("Verdana", Font.PLAIN, 36));

            header.setAlignmentX (Component.CENTER_ALIGNMENT);

            
            add(header); 
              for (int i=0; i<prerequisites.size(); i++){
                add (prerequisites.get(i));
            }
            
            //try again button is added
            tryAgain = new JButton ("Try Again?");
            tryAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
            tryAgain.addActionListener(new TryAgainListener());
            add (Box.createRigidArea (new Dimension (0, 20)));
            add (tryAgain);
            
        }
    }
   private class TryAgainListener implements ActionListener //occurs when try again button is pressed
   {
      public void actionPerformed (ActionEvent event){

          removeAll();
          revalidate();
          repaint();
          
          FuturePanel resetPanel = new FuturePanel(pastCourses,csCourses);
          add(resetPanel);

      }
   }
}