/* Name   : Alex Reid
 * User   : AReid
 *Descrip.: This class contains the Tour data type, as well as supplying methods such as draw, show, size, and distance
 *          to give the NearestInsertion and SmallestInsertion classes full functionality. The nearest neighbor and 
 *          smallest increase heuristic methods have also been submitted.
 *          
 *          Performance Results:
 *          N          NearestInsertion(seconds)     SmallestInsertion(seconds)   Respective Ratios
 *         1K             .016                             .016                     - -
 *         2K             .024                             .04                     1.5, 2.5
 *         4K             .072                             .128                    3,    3.2
 *         8K             .356                             .488                    4.94, 3.812
 *         16K           1.6                              1.916                    4.49, 3.93
 *         32K           3.2                              8.249                    4.43, 4.31
 *         64K          44.86                            48.04                     6.33, 5.82
 *         
 *         
 *         Results indicated a N^2 order of growth.
 *         T = aN^2
 * 
 */

public class Tour {
	
	private class Node
	{
		private Point p;
		private Node next;
	}
	private Node first;
	
	// Creates an empty tour
	public Tour()
	{
		first =  new Node();
		first.p = null;
	}
	
	// Creates a 4 point tour a->b->c->d->a
	public Tour(Point a, Point b, Point c, Point d)
	{
		first = new Node();
		first.p = a;
		Node second = new Node();
		first.next = second;
		
		second.p = b;
		Node third = new Node();
		second.next = third;
		
		third.p = c;
		Node fourth = new Node();
		third.next = fourth;
		
		fourth.p = d;
		fourth.next = first;
	}
	
	// Prints the tour to standard output
	public void show()
	{
		Node current;
		if(first.p == null)
		{
			return;
		}
		for(current = first; current.next != first; current = current.next)
		{
			System.out.println(current.p.toString());
		}
		System.out.println(current.p.toString());
	}
	
	// Draws the tour to standard draw
	public void draw()
	{
		Node current;
		//Special condition code in case of an empty linked list
		if(first.p == null)
		{
			return;
		}		
		
		for(current = first; current.next != first; current = current.next)
		{
			current.p.drawTo(current.next.p);
		}
		current.p.drawTo(current.next.p);
	}
	
	 // Return the number of points on tour
	public int size()
	{
		int size = 0;
		if(first.p == null)
		{
			return size;
		}	
		
		for(Node current = first; current.next != first; current = current.next)
		{
			size++;
		}
		size++;
		
		return size;
	}
	
	// Return the total distance of the tour
	public double distance()
	{
		Node current;
		double sum = 0;
		
		if(first.p == null)
		{
			return sum;
		}	
		
		for(current = first; current.next != first; current = current.next)
		{
			sum += current.p.distanceTo(current.next.p);
		}
		sum += current.p.distanceTo(current.next.p);
		
		return sum;
	}
	
	// Insert Point p using the nearest neighbor heuristic
	public void insertNearest(Point p)
	{
		//Special condition code in case of an empty linked list.
		if(first.p == null)
		{
			first.p = p;
			first.next = first;
			return;
		}
		
		double min = Double.POSITIVE_INFINITY;
		Node minNode = new Node();
		Node current = first;
		
		do{
			if(current.p.distanceTo(p) < min)
			{
				min = current.p.distanceTo(p);
				minNode = current;		
			}	
			current = current.next;			
		}while(current != first);
		{
			Node newNeighbor = new Node();
			newNeighbor.p = p;
			newNeighbor.next = minNode.next;
			minNode.next = newNeighbor;	
		}
	}
	
	 // Insert Point p using the smallest increase heuristic
	public void insertSmallest(Point p)
	{
		double OGDist  = 0;
		double newDist = 0;
		double distanceDiff;
		Node minNode = new Node();
		double smallestIncrease = Double.POSITIVE_INFINITY;
		Node current = first;
		
		//Special condition code in case of an empty list.
		if(first.p == null)
		{
			first.p = p;
			first.next = first;
			return;
		}
		
		do{
			OGDist  = current.p.distanceTo(current.next.p);
			newDist = current.p.distanceTo(p) + p.distanceTo(current.next.p);
			distanceDiff = newDist - OGDist;
			
			if(distanceDiff < smallestIncrease)
			{
					smallestIncrease = distanceDiff;
					minNode = current;
			}	
			
			current = current.next;	
			
		}while(current != first);
		{
			
			Node newNeighbor = new Node();
			newNeighbor.p = p;
			newNeighbor.next = minNode.next;
			minNode.next = newNeighbor;	
		}
	}
		
	//Some code to test methods during development
	public static void main(String [] args)
	{
		Point a = new Point(0.5, 0.6);
		Point b = new Point(0.25, 0.3);
		Point c = new Point(0.8, 0.2);
		Point d = new Point(0.6, 0.8);
		
		Tour tour = new Tour(a, b, c, d);
		System.out.println(tour);
		System.out.println("size = " + tour.size() + ", distance = " + tour.distance());
		tour.show();
		tour.draw();
		
	}
	
	

}
