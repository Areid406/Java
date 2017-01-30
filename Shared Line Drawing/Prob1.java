
import java.util.*;
public class Prob1
{
	public static void foo1(String	[]	args)
	{
		Arrays.sort(args);
		System.out.print(args[0]);
	} 				
	public static void foo2(String	[]	args)
	{
		for (String	s	:	args)
			System.out.print(s.length()	+	"	");
	}
	public static void main(String	[]	args)
	{
		if (args.length %	2	==	0)
			foo1(args);
		else
			foo2(args);								
	}
}


