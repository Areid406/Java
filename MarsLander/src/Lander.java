//Name			  : Alex Reid
//Username		  : AReid	
//Description	  : A Mars lander game where the object is to land on the pad,
//				  : without coming in too fast or being over the edge.

import java.awt.Font; 

public class Lander 
{
	public static void main(String[] args)
	{	
		//Holy variables!
		int    levelWidth     	 = StdIn.readInt();
		int    levelHeight    	 = StdIn.readInt();
		String levelPicture   	 = StdIn.readString();
		String normalShip 	  	 = StdIn.readString(); 
		String bottomThruster 	 = StdIn.readString(); 
		String rightThruster   	 = StdIn.readString(); 
		String leftThruster  	 = StdIn.readString(); 
		String shipLanded     	 = StdIn.readString(); 
		String shipCrashed    	 = StdIn.readString(); 
		int    cent2Edge      	 = StdIn.readInt();
		int    cent2Bottom       = StdIn.readInt();
		double posX         	 = StdIn.readDouble();
		double posY 		  	 = StdIn.readDouble();
		int    startFuel 	  	 = StdIn.readInt();
		String thrustSound 	  	 = StdIn.readString();
		String goodLand  	  	 = StdIn.readString();
		String badLand 		  	 = StdIn.readString();
		double gravity 		  	 = StdIn.readDouble();
		double notDeathVel 	   	 = StdIn.readDouble();
		double thrust   	     = StdIn.readDouble();
		int    padCenter	  	 = StdIn.readInt();
		int    padCent2Edge      = StdIn.readInt();
		double velX				 = 0;
		double velY				 = 0;


		//Sets the game level image and scale.
		StdDraw.setCanvasSize(levelWidth, levelHeight);
		StdDraw.setXscale(0, levelWidth);
		StdDraw.setYscale(0, levelHeight);
		

		while(true)
		{
			//Level-map creation.
			StdDraw.clear();
			StdDraw.picture(levelWidth / 2, levelHeight / 2, levelPicture);
			StdDraw.filledRectangle(padCenter, 0, padCent2Edge, 5 );
			
			//My conditions for a successful landing.
			if((posX + cent2Edge <= padCenter + padCent2Edge) && (posX - cent2Edge >= padCenter - padCent2Edge) 
				&& (posY <= cent2Bottom) && (-velY <= notDeathVel))
			{
				StdDraw.picture(posX, posY, shipLanded);
				StdAudio.play(goodLand);
				StdDraw.show();
				break;
			
			//Conditions for a crash landing.	
			}else if(posY <= cent2Bottom)
			{
				StdDraw.picture(posX, posY, shipCrashed);
				StdAudio.play(badLand);
				StdDraw.show();
				break;
			}

			//During normal gameplay a dwindling fuel counter is displayed.
		
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setFont(new Font("SansSerif", Font.BOLD, 18));
			StdDraw.text(0, 495 , (" " + startFuel)  );

			//Where the bulk of my gameplay actually happens. Each key displays corresponding thrusters and decrements fuel.
			if(StdDraw.hasNextKeyTyped() && startFuel > 0)
			{
				char ch = StdDraw.nextKeyTyped();
				if(ch == 'a')
				{
					velX += -thrust;
					StdDraw.picture(posX, posY, leftThruster);
					StdAudio.play(thrustSound);
					startFuel--;
				}
				else if (ch == 'd')
				{
					velX += thrust;
					StdDraw.picture(posX, posY, rightThruster);
					StdAudio.play(thrustSound);
					startFuel--;
				}
				else if (ch == 'w')
				{	
					velY += thrust;
					StdDraw.picture(posX, posY, bottomThruster);
					StdAudio.play(thrustSound);
					startFuel--;

				}else
				{
					// If no keys are pressed, a normal ship is still displayed.
					StdDraw.picture(posX, posY, normalShip);
				}				
			}else
			{
				//During normal gameplay a normal ship is displayed.
				StdDraw.picture(posX, posY, normalShip);
			}
			
			// Applies gravity to the ship and updates its position.
			velY += gravity;
			posX += velX;
			posY += velY;
			StdDraw.show(100);
		}
	}
}
