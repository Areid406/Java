// Dungeon 2 main program.
// 
// Usage reading via standard input:
//   % java DungeonGame2 < d3.txt
// 
// Usage reading via file input:
//   % java DungeonGame2 d3.txt
//
// Usage reading via file input with a high score file and player name:
//   % java DungeonGame2 d3.txt scores.txt Keith
//
// Do NOT modify this file.

import java.awt.*;
import java.io.*;

public class DungeonGame2
{
    // Helper method that waits for the player to press space.
    // Consumes the pressed key.
    private static void waitForSpace()
    {
        char ch = '\0';
        do
        {
            if (StdDraw.hasNextKeyTyped())
                ch = StdDraw.nextKeyTyped();
            else
                StdDraw.show(20);
        }
        while (ch != ' ');
    }
    
    // Helper method that displays the high score list.
    // winnerIndex is the 0-based index of the newly added winner (shown in red).
    private static void displayHighScores(HighScores scores, int winnerIndex)
    {
        // Reset to the normal coordinate system
        StdDraw.setScale(0.0, 1.0);
        
        StdDraw.show(0);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 0.5, 1.5);
        
        StdDraw.setFont(new Font("Courier", Font.BOLD, 32));
        StdDraw.setPenColor(StdDraw.RED);        
        StdDraw.text(0.5, 0.95, "HIGH SCORES");
        
        StdDraw.setFont(new Font("Courier", Font.BOLD, 24));
        StdDraw.setPenColor(StdDraw.GREEN);
        
        // Display the top scores
        double y = 0.85;
        for (int i = 0; i < scores.getSize(); i++)
        {
            if (i == winnerIndex)
                StdDraw.setPenColor(StdDraw.RED);
            else
                StdDraw.setPenColor(StdDraw.GREEN);
            
            String line = String.format("%2d  %-12s %5.1f", i + 1, scores.getName(i), scores.getTime(i));
            StdDraw.textLeft(0.1, y, line);
            y -= 0.05;
        }                        
        StdDraw.show(0);        
    }
    
    public static void main(String [] args)
    {
        final int HIGH_SCORE_SIZE = 10;
        
        Config config = new Config();

        Dungeon dungeon = null;
        if (args.length == 0)
        {
            dungeon = DungeonFactory.createFromStdIn(config);
        }
        else
        {
            try
            {
                dungeon = DungeonFactory.createFromFile(config, args[0]);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Failed to open dungeon file: " + args[0]);
                return;
            }
        }

        // Set the canvas size based on the size of the dungeon
        int width = dungeon.getWidth();
        int height = dungeon.getHeight();
        StdDraw.setCanvasSize(config.cellPixels() * width, config.cellPixels() * height);

        HighScores scores = null;

        // Check if we have a high score file and a player name given
        if (args.length >= 3)
        {
            scores = new HighScores(args[1], HIGH_SCORE_SIZE);
            displayHighScores(scores, -1);
            waitForSpace();
        }
                        
        // Prepare the standard draw window, without a border for pixel perfect drawing
        StdDraw.setXscale(0.0, config.cellPixels() * width);
        StdDraw.setYscale(0.0, config.cellPixels() * height);

        StdDraw.setFont(new Font("Courier", Font.BOLD, 24));
                
        StdDraw.show(0);
        dungeon.draw();
        StdDraw.show(0);

        Action action = Action.INVALID;
        do
        {
            StdDraw.clear();
            if (StdDraw.hasNextKeyTyped())
            {               
                action = config.mapKeyToAction(StdDraw.nextKeyTyped());
                dungeon.execute(action);
            }
            dungeon.draw();
            StdDraw.show(20);
        } while ((dungeon.getCount(Item.GEM) > 0) && (action != Action.QUIT));
        
        String message;
        int winnerIndex = -1;
        
        if (dungeon.getCount(Item.GEM) > 0)
        {
            StdDraw.setPenColor(StdDraw.RED);
            message = "Loser!";
        }
        else
        {
            StdDraw.setPenColor(StdDraw.GREEN);
            message = "Winner!";       
            
            if (scores != null)
            {
                winnerIndex = scores.addWinner(args[2], dungeon.getElapsedTime());
                if (!scores.save())
                    System.out.println("Failed to write high score file!");
            }
        }
        StdDraw.setFont(new Font("Courier", Font.BOLD, 96));
        StdDraw.text(config.cellPixels() * width * 0.5,
                     config.cellPixels() * height * 0.5,
                     message);
        StdDraw.show(0);
        waitForSpace();
        
        // Display high scores at the end of the game as well
        if (scores != null)
            displayHighScores(scores, winnerIndex);                

    }   
}
