// Represents a word along with its unigram probability and an optional audio filename.
// 
// Do NOT modify this file.

public class Word
{
	private String word				= ""; 		// text of this particular word
	private double prob				= 0.0;    	// unigram language model probability
	private String audioFilename 	= "";    	// audio filename (if any)
	
	// Constructor, no audio filename passed in
	public Word(String word, double prob)
	{
		this.word = word;
		this.prob = prob;
	}

	// Overloaded constructor, this version also takes an audio filename
	public Word(String word, double prob, String audioFilename)
	{
		this.word  			= word;
		this.prob  			= prob;
		this.audioFilename 	= audioFilename;
	}
	
	// Getter for the audio filename
	public String getAudioFilename()
	{
		return audioFilename;
	}
	
	// Getter for the word
	public String getWord()
	{
		return word;
	}
	
	// Getter for the probability
	public double getProbability()
	{
		return prob;
	}
	
	// Return a string representation of this object, e.g.:
	//   quick (prob 0.123000 audio '')
	public String toString()
	{
	    return String.format("%s (prob %.6f audio '%s')", word, prob, audioFilename);
	}
	
	// See if the given regular expression matches this word
	public boolean matchesPattern(String regularExpression)
	{
		return (word.matches(regularExpression));		
	}

	// Test main program for the Word class
	public static void main(String [] args)
	{
	    Word quick = new Word("quick", 1.0 / 3.0, "quick.wav");
	    
	    System.out.println("word                 = " + quick.getWord());
	    System.out.println("prob                 = " + quick.getProbability());
	    System.out.println("audio                = " + quick.getAudioFilename());
	    System.out.println("matches(\"quick\")     = " + quick.matchesPattern("quick"));
	    System.out.println("matches(\"Quick\")     = " + quick.matchesPattern("Quick"));
	    System.out.println("matches(\"[a-z]+ick\") = " + quick.matchesPattern("[a-z]+ick"));
	    
	    System.out.println(quick);
	}
	
}

