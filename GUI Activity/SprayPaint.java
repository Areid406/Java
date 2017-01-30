/*
 * Programming activity: GUIs, JPanel and mouse events
 * 
 * Part 1: Create a JFrame application containing a JPanel that allows the 
 * use to draw with a big colored circle centered at the mouse.  
 * 
 * Left button changes the current drawing color.
 * Right button clears the screen.
 * 
 * Part 2: Once mouse-based interaction is working, add code that allows
 * the radius of the spray paint to be increased and decreased using the
 * '+' and '-' keys.  HINT: don't forget to make your panel focusable!
 * 
 * LEVEL 3: Add additional drawing mode of your own invention, ideas:
 * 1) Make it possible to toggle the spray paint on or off
 * 2) Support different shaped nozzles (e.g. square instead of circle)
 * 3) Allow selection of different colors from a set using the number keys
 * 4) ...
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SprayPaint 
{   
    public void go()
    {
        JFrame frame = new JFrame("Spray paint");
        
        // TODO 1: Create the JPanel and add it to the JFrame
        JPanel thingy = new JPanel();

        

        // TODO 2: In order to respond to keyboard event, set the panel to be focusable
        thingy.setFocusable(true);

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public static void main(String [] args)
    {
        SprayPaint paint = new SprayPaint();
        paint.go();
    }

}
