import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javafoundations.LinkedBinaryTree;
import java.util.*;
/**
 * Creates RecommendationsPanel with interactive Decision Tree.
 *
 * @author(Zoe Allen, Christina Chen, Claire Cannatti)
 * @version (5/15/2018)
 */
public class RecommendationsPanel extends JPanel
{
    // instance variables
    CourseList userCourses;
    String currentString;
    String finalAnswer, finalAnswer2;
    
    CourseSuggestion suggestionTree;
    JButton yes, no, tryAgain;
    JLabel subhead, subhead2;
    LinkedBinaryTree<String> current;
    LinkedList<JLabel> coursesToTake;
    
    /**
     * Constructor for objects of class PastPanel
     * 
     * @param CourseList of past courses
     */
    public RecommendationsPanel(CourseList past) 
    {
        suggestionTree = new CourseSuggestion(userCourses);
        userCourses = past;
        
        //setting layout
        setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
        setPreferredSize (new Dimension(700, 1000));
        
        //pointer for tree and contents of tree
        current = suggestionTree.getTree();
        currentString = (current.getRootElement());
        
        //header
        JLabel header = new JLabel("What should you take next?");
        
        //question
        subhead = new JLabel(currentString); 
        subhead2 = new JLabel();
        
        //yes and no buttons
        yes = new JButton ("YES");
        no = new JButton ("NO");
        
        
        header.setFont (new Font ("Verdana", Font.BOLD, 36));
        subhead.setFont (new Font ("Verdana", Font.PLAIN, 24));
        subhead2.setFont (new Font ("Verdana", Font.PLAIN, 24));
       
        header.setAlignmentX (Component.CENTER_ALIGNMENT);
        subhead.setAlignmentX (Component.CENTER_ALIGNMENT);
        subhead2.setAlignmentX (Component.CENTER_ALIGNMENT);
        yes.setAlignmentX (Component.CENTER_ALIGNMENT);
        no.setAlignmentX (Component.CENTER_ALIGNMENT);
        
        //adds listeners to yes and no buttons
        yes.addActionListener(new ButtonListener());
        no.addActionListener(new ButtonListener());
        
        //add elements to panel
        add (Box.createRigidArea (new Dimension (0, 10)));
        add (header); 
        add (Box.createRigidArea (new Dimension (0, 10)));
        add (subhead);
        add (subhead2);
        add (Box.createRigidArea (new Dimension (0, 20)));
        add (yes);
        add (Box.createRigidArea (new Dimension (0, 5)));
        add (no);
    }
    
    
    private class ButtonListener implements ActionListener
   {
      public void actionPerformed (ActionEvent event)
      { 
        //if tree is not down to the final question, continue going through the tree
          if (current.size() >= 3){

            Object source = event.getSource();
            
            //goes left or right depending on user answer
            if (source == no)
                 current = current.getLeft();
            else
                 current = current.getRight();
            
            //update currentString to be the next question
            currentString = (current.getRootElement());
            
            //set the text on the panel to be the next question
            subhead.setText(currentString);             
        } 
        
        //if the tree is down to the final answer
        if (current.size() == 1){
            remove (yes);
            remove (no);
            
            // find resulting string's corresponding Linked List and then convert that linked list back to a string
            LinkedList<Course> suggestionList = suggestionTree.convertToLL((current.getRootElement()));
            System.out.println("root: " + current.getRootElement());
            
            coursesToTake = new LinkedList<JLabel>();
            int listSize = suggestionList.size();
            
            for (int i = 0; i < suggestionList.size(); i++){
                if (!(userCourses.hasTaken(suggestionList.get(i)))){
                    String s = suggestionList.get(i).toString();
                    JLabel rec = new JLabel(s);
                    rec.setFont (new Font ("Verdana", Font.PLAIN, 16));
                    rec.setAlignmentX(Component.CENTER_ALIGNMENT);
                    coursesToTake.add(rec);
                }
                else {
                    suggestionList.remove(i);
                    i --;
                }
            }
            
            // if the LL is empty, all courses in that category have been taken
            if (suggestionList.isEmpty()){
                finalAnswer = "You have taken every course offered in this category.";
            }  
            else{
                finalAnswer = "Based on your responses to the questions, ";
                finalAnswer2 = "you should take:";
            }
            subhead.setText(finalAnswer);
            subhead2.setText(finalAnswer2);
            
            
            for (int i = 0; i < coursesToTake.size(); i++){
                add(coursesToTake.get(i));
            }
            
            //add a tryAgain button so the user can repeat the process
            tryAgain = new JButton ("Try Again?");
            tryAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
            tryAgain.addActionListener(new TryAgainListener());
            add (Box.createRigidArea (new Dimension (0, 20)));
            add (tryAgain);
          }
        }
   }
   
   private class TryAgainListener implements ActionListener
   {
      public void actionPerformed (ActionEvent event){
          //removes all elements from the panel
          removeAll();
          revalidate();
          repaint();
          
          RecommendationsPanel resetPanel = new RecommendationsPanel(userCourses);
          add(resetPanel);

      }
   }
    
}