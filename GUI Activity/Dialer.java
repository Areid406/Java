/*
 * Programming activity: GUIs: layout managers and event listeners
 * 
 * Complete the code to make a phone dialing application.
 * 
 * Part 1: Add/change code to layout the GUI widgets.
 * The "Number: " label should appear at the top of the GUI.
 * Below this, display 4 rows of buttons for the phone keys:
 *   1 2 3
 *   4 5 6
 *   7 8 9
 *   * 0 #
 *  
 * Part 2: Add/change code to make each button respond to clicks.
 * Make the click handler append to the label and also play a
 * DTMF audio tone for the key.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dialer implements ActionListener
{
    private JLabel number= new JLabel("Number: ");

    private static final String [] KEYS       = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#"};
    private static final String [] DTMF_AUDIO = {"Dtmf-1.wav",
        "Dtmf-2.wav",
        "Dtmf-3.wav",
        "Dtmf-4.wav",
        "Dtmf-5.wav",
        "Dtmf-6.wav",
        "Dtmf-7.wav",
        "Dtmf-8.wav",
        "Dtmf-9.wav",
        "Dtmf-star.wav",
        "Dtmf-0.wav",
    "Dtmf-pound.wav"};

    private JButton buttons[];

    public void actionPerformed(ActionEvent event)
    {
        for (int i = 0; i < buttons.length; i++)
        {
            // TODO 2: Change true to check if the source of the event is buttons[i]
            if (event.getSource() == buttons[i])
            {
                // TODO 2: Change "" to instead append the new digit on to the current text of the label
                number.setText(number.getText() + KEYS[i]);
                
                // TODO 2: Play the audio for the given button using StdAudio.play()
                StdAudio.play(DTMF_AUDIO[i]);

                
                return;
            }
        }
    }

    public void go()
    {
        JFrame frame = new JFrame("Phone dialer");
        
        buttons = new JButton[KEYS.length];
        for (int i = 0; i < KEYS.length; i++)
        {
            buttons[i] = new JButton(KEYS[i]);

            // TODO 2: Add this class as the listener for each button
            buttons[i].addActionListener(this);
            
        }

        // Add the number label to the north segment of the JFrame
        frame.getContentPane().add(number, BorderLayout.NORTH);

        // All the phone button are contained in a JPanel
        JPanel dialPad = new JPanel();

        // TODO 1: Call setLayout() on the dialPad JPanel to change
        // the layout manager to be a vertical BoxLayout.
        dialPad.setLayout(new BoxLayout(dialPad, BoxLayout.Y_AXIS));

        
        for (int i = 0; i < KEYS.length / 3; i++)
        {
            // Each row of the keypad is contained in its own JPanel
            JPanel row = new JPanel();

            // TODO 1: Change this row's layout manager to be a horizontal BoxLayout
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

            // TODO 1: Change the array indexes to get the proper keys in each row
            row.add(buttons[i * 3]);
            row.add(buttons[(i * 3) + 1]);
            row.add(buttons[(i * 3) + 2]);

            dialPad.add(row);
        }

        // TODO 1: Add the dialPad panel to the south segment of the JFrame
        frame.getContentPane().add(dialPad, BorderLayout.SOUTH);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 250);
        frame.setVisible(true);

    }

    public static void main(String [] args)
    {
        Dialer dialer = new Dialer();
        dialer.go();
    }
}
