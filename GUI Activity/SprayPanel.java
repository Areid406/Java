import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

// TODO 2: Specify that the SprayPanel class also listens for keyboard events
public class SprayPanel extends JPanel implements MouseListener, MouseMotionListener
{
    // Inner class, stores data about a single painted location
    public class Spray
    {
        Color color;
        int x;
        int y;
        int radius;
    }

    // Track all the locations that have currently been sprayed
    private ArrayList<Spray> sprays = new ArrayList<Spray>();

    // Current radius of the spray paint
    private int radius = 30;

    // The set of colors we can potentially draw with
    private final Color [] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.WHITE, Color.BLACK, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.YELLOW, Color.PINK};

    // Index of color we are currently drawing with
    private int currentColor = 0;

    // Default constructor for the panel
    public SprayPanel(MouseEvent e)
    {
        // TODO 1: Make the panel listen for the motion of the mouse
    	
    	
        
        // TODO 1: Make the panel listen for mouse events such as clicks
        
        // TODO 2: Make the panel listen for keyboard events

    }

    // Do some drawing on the panel
    public void paintComponent(Graphics g)        
    {
        for (Spray spray: sprays)
        {
            // Draw a circle in the current color centered at the mouse location   
            
            // TODO 1: Set the color of the drawing pen
            g.setColor(spray.color);
            
            // TODO 1: Draw the paint using fillOval()
            g.fillOval(spray.x - spray.radius / 2, 
                    spray.y - spray.radius / 2, 
                    spray.radius,
                    spray.radius);
        }
    }

    // Update the current mouse position and force an update of the drawing panel
    public void mouseMoved(MouseEvent event)
    {
        Spray spray = new Spray();
        
        // TODO 1: Set the (x, y) location of the paint to the mouse location
        spray.x = 0;               
        spray.y = 0;

        // Also store the current spray radius and color since it can change
        spray.radius = radius;
        spray.color = COLORS[currentColor];
        
        // TODO 1: Add the spray data to the ArrayList sprays instance variable
        
        // Force paintComponent to run so we see the new paint
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent event) {}        

    @Override
    public void mouseClicked(MouseEvent event) {}
    
    @Override
    public void mouseEntered(MouseEvent event) {}
    
    @Override
    public void mouseExited(MouseEvent event)  {}
    
    @Override
    public void mousePressed(MouseEvent event) {}

    // Change the color to a new random color whenever the mouse is released.
    // Force an update of the drawing panel.
    @Override
    public void mouseReleased(MouseEvent event)
    {
        // TODO 1: Change true to check instead if the mouse event was the left button
        if (true)
        {
            // TODO 1: Change to the next color in the COLORS array
            currentColor = 0;
        }
        else
        {
            // Clear all the previous spray paint locations
            sprays.clear();
        }

        // TODO 1: make the panel draw itself again

    }

}
