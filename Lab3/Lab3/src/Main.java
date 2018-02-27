/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Git URL:
 * Spring 2018
 */


import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here

	public static HashSet<String> VisitedBFSWords;
	public static HashSet<String> VisitedDFSWords;

	public static Queue<String> BFSQueue;

	public static ArrayList<ArrayList<String>> WordNeighbors;
	public static ArrayList<String> WordNeighborsIndex;

	public static Set<String> dict;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();

		// TODO methods to read in words, output ladder
		while(true){
			VisitedDFSWords.clear();
			VisitedBFSWords.clear();
			System.out.println("Please enter a start word and end word, separated by a space.");
			ArrayList<String> words = parse(kb);
			if(words.contains("/quit")) break;


			ArrayList<String> DFSLadder = getWordLadderBFS(words.get(0).toUpperCase(), words.get(1).toUpperCase());
			if(DFSLadder.get(0).equals(words.get(0).toUpperCase()) && DFSLadder.get(1).equals(words.get(1).toUpperCase())){
				System.out.println("no word ladder can be found between " + words.get(0) + " and " + words.get(1) + ".");
			} else {
				System.out.println("a " + (DFSLadder.size()-2) + "-rung word ladder exists between " + words.get(0) + " and " + words.get(1) + ".");
				printLadder(DFSLadder);
			}


		}

	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		VisitedDFSWords = new HashSet<>();
		VisitedBFSWords = new HashSet<>();
		WordNeighbors = new ArrayList<>();
		WordNeighborsIndex = new ArrayList<>();
		dict = makeDictionary();

		for(String word : dict){
			WordNeighbors.add(findNeighbors(word));
			WordNeighborsIndex.add(word);
		}

	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String input = keyboard.nextLine();
		return new ArrayList<String>(Arrays.asList(input.split(" ")));
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		// TODO some code
		//Set<String> dict = makeDictionary();
		ArrayList<String> DFSLadderRet = new ArrayList<>();
		DFSLadderRet.add(start);

		int result = 0;
		try {
			result = getWordLadderDFS(start, end, DFSLadderRet, true);
		} catch(StackOverflowError e){
			DFSLadderRet.clear();
			DFSLadderRet.add(start);
			DFSLadderRet.add(end);
		}
		if(result == 0){
			DFSLadderRet.clear();
			DFSLadderRet.add(start);
			DFSLadderRet.add(end);
		}

		return DFSLadderRet; // replace this line later with real return
	}

	/**
	 * Helper function for the original getWordLadderDFS
	 * @param start	The current word being looked at
	 * @param end	The destination word
	 * @param DFSLadder	The ladder
	 * @param firstCall	"Is this the first iteration of the recursion?"
	 * @return 0 if no word ladder is found, 1 if a word ladder is found
	 * @throws StackOverflowError like the goddamn hack I am
	 */
	private static int getWordLadderDFS(String start, String end, ArrayList<String> DFSLadder, boolean firstCall) throws StackOverflowError{
		//Did we find the end or is the ladder already full?
		if(start.equals(end)){
			return 1;
		}

		VisitedDFSWords.add(start);

		//If it's the last call before returning to the main function, are there any
		//more words to look for?
		if(firstCall && DFSLadder.size() > 1){
			for(String isVisited : WordNeighbors.get(WordNeighborsIndex.indexOf(start))){
				if(!VisitedDFSWords.contains(isVisited)){
					return 2147483647; //I swear to god if this is returned
				}
			}
			return 0;
		}

		ArrayList<String> wordRankings = new ArrayList<>();
		//Populate array of neighbors
		for(String word : WordNeighbors.get(WordNeighborsIndex.indexOf(start))){
			if(!VisitedDFSWords.contains(word)){
				wordRankings.add(word);
			}
		}
		if(wordRankings.size() == 0){
			return 0;
		}
		//Sort the array of neighbors by difference to the end word
		for(int i = 1; i < wordRankings.size(); i++){
			for(int j = 0; j < i; j++){
				if(getDifference(wordRankings.get(i), end) < getDifference(wordRankings.get(j),end)){
					String tempWord = wordRankings.get(i);
					wordRankings.set(i, wordRankings.get(j));
					wordRankings.set(j, tempWord);
				}
			}
		}

		boolean isTheWordVeryDifferent;

		for(int i = 0; i < wordRankings.size(); i++){

			//Try to stay away from words that only differ from the prior words by one
			if(DFSLadder.size() >= 2){
				String differWord = DFSLadder.get(DFSLadder.size()-2);
				if(getDifference(wordRankings.get(i), differWord) == 2) {
					isTheWordVeryDifferent = true;
				} else {
					isTheWordVeryDifferent = false;
				}
			} else {
				isTheWordVeryDifferent = true;
			}

			//Only recurse if you haven't seen the word, the word is at least as close
			//to the end as the current word, and the test immediately prior passed
			if(!VisitedDFSWords.contains(wordRankings.get(i)) && getDifference(wordRankings.get(i),end) <= getDifference(start, end) && isTheWordVeryDifferent){
				DFSLadder.add(wordRankings.get(i));
				int result = getWordLadderDFS(wordRankings.get(i), end, DFSLadder, false);
				if(result == 0) {
					DFSLadder.remove(wordRankings.get(i));
				} else {
					return 1;
				}
			}
		}
		return 0;


	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {

		BFSQueue = new LinkedList<>();

		ArrayList<String> BFSLadder = new ArrayList<>();
		BFSQueue.add(start);
		VisitedBFSWords.add(start);

		while(!BFSQueue.isEmpty()){
			String word = BFSQueue.poll();
			if(word.equals(end)){
				BFSLadder.add(end);
				BFSQueue.clear();
				return BFSLadder;
			}

			for(String wordToQueue : WordNeighbors.get(WordNeighborsIndex.indexOf(word))){
				if(!VisitedBFSWords.contains(wordToQueue)){
					BFSQueue.add(wordToQueue);
				}
			}
		}

		BFSLadder.addAll(BFSQueue);
		
		return BFSLadder; // replace this line later with real return
	}


	public static ArrayList<String> findNeighbors(String word){
		ArrayList<String> neighbors = new ArrayList<>();

		//Go through the dictionary and find any words that aren't already visited
		//and are only different to the original word by 1 letter
		for(String dictWord : dict){
			if(getDifference(word, dictWord) == 1) {
				neighbors.add(dictWord);
			}
		}
		return neighbors;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		for(String word : ladder){
			System.out.println(word);
		}
	}
	// TODO
	// Other private static methods here

	private static int getDifference(String word1, String word2){
		int retNum = 0;
		for(int i = 0; i < word1.length(); i++){
			if(word1.charAt(i) != word2.charAt(i)){
				retNum++;
			}
		}
		return retNum;
	}


	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
