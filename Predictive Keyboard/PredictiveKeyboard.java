// Name        : Alexander Reid
// Username    : AReid
// Description : Graphic output of the Predictive Keyboard program, shows words typed and most probable matches, as well
//             : as keeping a running tally of words typed by hitting return or space.
//             : Return selects the given probable match and add to the running total sentence.
//             : Space simply adds the currently typed word to the running total sentence.

import java.awt.Font;
import java.io.*;
import java.util.*;

public class PredictiveKeyboard 
{	
	public static void main(String [] args)
	{
		final int windowHeight = 256;
		final int windowWidth = 1024;
		String prefix = "";
		String prediction = "";
		String total = "";
		String validKeys = "abcdefghijklmnopqrstuvwxyz\'";
		WordPredictor trainData = new WordPredictor(); 

		StdDraw.setCanvasSize(windowWidth, windowHeight);
		StdDraw.setXscale(0, windowWidth);
		StdDraw.setYscale(0, windowHeight);


		//Trains and builds on each word read in
		for(String s : args)
		{
			trainData.train(s);
			trainData.build();
		}	

		while(true)
		{
			StdDraw.clear();

			if(StdDraw.hasNextKeyTyped())
			{
				char key = StdDraw.nextKeyTyped();

				for(int i = 0; i < validKeys.length(); i++)
				{
					//if a valid key is typed it is added to the prefix and sets a prediction for a probable match, or does nothing
					if(i == validKeys.indexOf(key))
					{
						prefix += key;
						if(trainData.getBest(prefix) != null)
							prediction = trainData.getBest(prefix).getWord();
						else
							prediction = "";
					}	
				}

				//If the user hits return and a prediction is currently available, output the predicted word and reset the current word to blank.
				//If the user hits return and a prediction is not available, do nothing.
				if(key == '\n')
				{
					if(trainData.getBest(prefix) != null){
						prediction = trainData.getBest(prefix).getWord();

						if(prediction != null)
						{	
							trainData.trainWord(prediction);
							trainData.build();
							total += prediction + " ";
							prefix = "";
							prediction = "";
						}
					}	
				}
				//If the user hits backspace, delete one letter from the currently typed word. If the current word is empty, don't do anything (i.e. you can't edit words once they are output).
				//If the user hits a key besides a-z, single apostrophe, return, or backspace: do nothing.
				if(key == '\b' && !prefix.equals(""))
				{
					prefix = prefix.substring(0, prefix.length() -1 );

					if(trainData.getBest(prefix) != null)
						prediction = trainData.getBest(prefix).getWord();
					else
						prediction = "";
				}	
				//If the user types a space and the current word is not blank, output the current word to the bottom and reset the current word to blank.
				//If the user types a space and the current word is blank, do nothing.
				if(key == ' ')
				{
					trainData.trainWord(prefix);
					trainData.build();
					total += prefix + " ";
					prefix = "";
					prediction = "";
				}
			}

			StdDraw.setFont(new Font("Consolas",Font.PLAIN, 24));
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(windowWidth / 2, windowHeight / 2, prefix);

			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.text(windowWidth / 2, windowHeight / 2.5, prediction);

			StdDraw.setFont(new Font("Consolas", Font.PLAIN, 16));
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.textRight(windowWidth / 1.1, windowHeight / 3, total);

			StdDraw.show(10);
		}

	}
}
