import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Name        : Alex Reid
// Username    : AReid
// Description : This class learns how to make word predictions based on being shown training data.
//             : It can learn from all the words in a file (via the train() method) or from a single individual word 
//             : (via the trainWord() method). 
public class WordPredictor 
{
	// The counts we use to train the probabilities in the unigram model
	private HashMap<String, Integer> wordToCount = new HashMap<String, Integer>();

	// A word prefix to dictionary entry map, lets us make the predictions
	private HashMap<String, Word> prefixToEntry = new HashMap<String, Word>(); 

	// Total count of all the words we've been trained on 
	private long total = 0;



	// Train on the specified file, this method can be called multiple
	// times in order to train on a set of files.  
	public void train(String trainingFile)
	{
		Scanner scanner = null;
		try
		{	
			scanner = new Scanner(new File(trainingFile));
			while(scanner.hasNext())
			{
				String word = scanner.next();
				trainWord(word);
			}	
			scanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Could not open training file: " + trainingFile);
		}
	}

	// Train on an instance of the given word.
	public void trainWord(String word)
	{
		//Normalizes the input text
		word = word.toLowerCase();
		word = word.replaceAll("[^a-z\']" , "");
		int wordCount = 1;
		
		//If the word is contained in the map, increase the instance of times the model trains on,
		//then increase the count and add to map. 
		if(wordToCount.containsKey(word))
		{
			wordCount = wordToCount.get(word)+ 1;		
		}
		wordToCount.put(word, wordCount);
		total++;
	}

	// Find out how many total words of training data we've seen
	public long getTrainingCount()
	{
		return total;
	}

	// Return the number of times we've seen a particular word.
	// Returns 0 if we've never seen this word.
	public int getWordCount(String word)
	{
		int count = 0;
		if(wordToCount.containsKey(word))
			count = wordToCount.get(word);
		return count;
	}

	// Prepare the object for prefix to word mapping.
	// This should be called AFTER all the training.
	// It MUST be called before getBest() will work.	
	public void build()
	{
		prefixToEntry.clear();
		for(Map.Entry<String, Integer> entry: wordToCount.entrySet())
		{
			String key = entry.getKey();
			int value = entry.getValue();
			double probability = value / (double)total;

			Word newWord = new Word(key, probability, "");

			//Goes over each key in the word typed
			for(int i = 1; i <= key.length(); i++)
			{
				//Each time the loop loops, the substring length increases and searches for a probable match.
				String prefix = key.substring(0, i);
				
				if(prefixToEntry.containsKey(prefix))
				{
					if(newWord.getProbability() > prefixToEntry.get(prefix).getProbability())
					{
						prefixToEntry.put(prefix, newWord);
					}
				}else{
					prefixToEntry.put(prefix, newWord);					
				}	
			}
		}
	}
		// Return the best matching Word object based on a prefix.
		// If the prefix doesn't match any known word, returns null.
		public Word getBest(String prefix)
		{
			if(prefixToEntry.containsKey(prefix))
				return prefixToEntry.get(prefix);
			else return null;
		}

		// Test main method
		public static void main(String [] args)
		{		
			// Train a model on the first bit of Moby-Dick
			WordPredictor wp = new WordPredictor();		
			System.out.println("bad1 = " + wp.getBest("the"));		
			wp.train("moby_start.txt");
			System.out.println("training words = " + wp.getTrainingCount());

			// Try and crash things on bad input
			System.out.println("bad2 = " + wp.getBest("the"));		
			wp.train("thisfiledoesnotexist.txt");
			System.out.println("training words = " + wp.getTrainingCount() + "\n");

			String [] words = {"the", "me", "zebra", "ishmael", "savage"};
			for (String s: words)			
				System.out.println("count, " + s + " = " + wp.getWordCount(s));
			System.out.println();

			wp.train("moby_end.txt");

			// Check the counts again after training on the end of the book
			for (String s: words)			
				System.out.println("count, " + s + " = " + wp.getWordCount(s));
			System.out.println();

			// Get the object ready to start looking things up
			wp.build();

			// Do some prefix lookups
			String [] test = {"a", "ab", "b", "be", "t", "th", "archang"};
			for (String prefix : test)
				System.out.println(prefix + " -> " + wp.getBest(prefix));
			System.out.println("training words = " + wp.getTrainingCount() + "\n");

			// Add two individual words to the training data
			wp.trainWord("beefeater");
			wp.trainWord("BEEFEATER!");
			wp.trainWord("Pneumonoultramicroscopicsilicovolcanoconiosis");

			// The change should have no effect for prefix lookup until we build()
			System.out.println("before, b -> " + wp.getBest("b"));
			System.out.println("before, pn -> " + wp.getBest("pn"));
			wp.build();
			System.out.println("after, b -> " + wp.getBest("b"));
			System.out.println("after, pn -> " + wp.getBest("pn"));
			System.out.println("training words = " + wp.getTrainingCount() + "\n");

			// Test out training on a big file, timing the training as well
			Stats stats1 = new Stats();
			wp.train("mobydick.txt");
			wp.build();
			for (String prefix : test)
				System.out.println(prefix + " -> " + wp.getBest(prefix));
			System.out.println("training words = " + wp.getTrainingCount());
			System.out.println(stats1);

			// Test lookup using random prefixes between 1-6 characters
			System.out.println("\nRandom load test:");
			Stats stats2 = new Stats();
			final String VALID = "abcdefghijklmnopqrstuvwxyz'";
			final long TEST_NUM = 10000000; 
			long hits = 0;
			for (long i = 0; i < TEST_NUM; i++)
			{
				String prefix = "";
				for (int j = 0; j <= (int) (Math.random() * 6); j++)
					prefix += VALID.charAt((int) (Math.random() * VALID.length()));
				Word word = wp.getBest(prefix);
				if (word != null)
					hits++;
			}
			System.out.println(stats2);
			System.out.println("Hit % = " + ((double) hits / TEST_NUM * 100.0));
		} 
	}

