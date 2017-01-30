/*************************************************************************
 *  YOU DO NOT NEED TO MODIFY THIS FILE
 *
 *  Compilation:  javac SmallestInsertion.java
 *  Execution:    java SmallestInsertion < file.txt
 *  Dependencies: Tour.java Point.java StdIn.java StdDraw.java
 *
 *  Run smallest insertion heuristic for traveling salesperson problem
 *  and plot results.
 *
 *  % java SmallestInsertion < tsp1000.txt
 *
 *  Optional integer command-line argument specifies animation delay in ms  
 *  % java SmallestInsertion 10 < tsp1000.txt
 *  
 *************************************************************************/

public class SmallestInsertion 
{

    public static void main(String[] args) 
    {

        // check for optional command-line argument that turns on animation
        int delay = -1;
        if (args.length > 0)
            delay = Integer.parseInt(args[0]);

        // get dimensions
        int w = StdIn.readInt();
        int h = StdIn.readInt();
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);

        // turn on animation mode
        StdDraw.show(0);

        // run smallest insertion heuristic
        Tour tour = new Tour();
        while (!StdIn.isEmpty()) 
        {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tour.insertSmallest(p);

            if (delay > -1)
            {
                StdDraw.clear();
                tour.draw();
                StdDraw.text(100, 0, "" + tour.distance());
                StdDraw.show(delay);
            }
        }

        // draw to standard draw 
        tour.draw();
        StdDraw.show(0);

        // print tour to standard output
        System.out.println("Tour distance = " + tour.distance());
        tour.show();
    }

}
