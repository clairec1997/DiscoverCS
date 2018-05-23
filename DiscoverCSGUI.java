import javax.swing.*;

/**
 * Puts all components of DiscoverCS! GUI together
 *
 * @author (Claire Cannatti)
 * @version (5/13/2018)
 */
public class DiscoverCSGUI
{
   //-----------------------------------------------------------------
   //  Sets up a frame containing a tabbed pane. The panel on each
   //  tab demonstrates a different layout manager.
   //-----------------------------------------------------------------
   public static void main (String[] args)
   {
      CourseList pastCourses = new CourseList();
      CourseOfferings csCourses = new CourseOfferings();
      
      JFrame frame = new JFrame ("DiscoverCS");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      JTabbedPane tp = new JTabbedPane();
      
      tp.addTab("Past", new PastPanel(pastCourses, csCourses));
      tp.addTab ("Present", new PresentPanel(pastCourses, csCourses));
      tp.addTab ("Future", new FuturePanel(pastCourses, csCourses));
      tp.addTab ("Recs", new RecommendationsPanel(pastCourses));
      
      frame.getContentPane().add(tp);

      frame.pack();
      frame.setVisible(true);
   }
}