package Lab3;
/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Zachary Pope
 * zhp76
 * 15465
 * Slip days used: <0>
 * Git URL: https://github.com/ZachPope531/422C_Spring2018
 * Spring 2018
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;


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
			System.out.println("Please enter a start word and end word, separated by a space.");
			ArrayList<String> words = parse(kb);
			if(words == null){
				continue;
			}
			if(words.contains("/quit")) break;

			System.out.println("**********DFS***********");
			ArrayList<String> DFSLadder = getWordLadderDFS(words.get(0).toUpperCase(), words.get(1).toUpperCase());
			printLadder(DFSLadder);
			VisitedDFSWords.clear();

			System.out.println("\n**********BFS***********");
			ArrayList<String> BFSLadder = getWordLadderBFS(words.get(0).toUpperCase(), words.get(1).toUpperCase());
			printLadder(BFSLadder);
			VisitedBFSWords.clear();


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
		if(input.length() < 10 || !input.contains(" ")){
			System.out.println("I said TWO WORDS, SEPARATED BY A SPACE");
			return null;
		}
		String trimmedInput = input.replace(" ", "").replace("\t", "").replace("\n", "");
		ArrayList<String> parsedInput = new ArrayList<>();
		parsedInput.add(trimmedInput.substring(0,5));
		parsedInput.add(trimmedInput.substring(5));
		return parsedInput;
	}

	/**
	 * Uses depth first search to find a word ladder between two words
	 * @param start	The first word of the ladder
	 * @param end	The last word of the ladder
	 * @return		The word ladder
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		// TODO some code
		//Set<String> dict = makeDictionary();
		ArrayList<String> DFSLadderRet = new ArrayList<>();
		start = start.toUpperCase();
		end = end.toUpperCase();
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
		//Sort the array of neighbors by closeness to the end word
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

	/**
	 * Uses breadth first search to find a word ladder between two words
	 * Created a node class to emulate a linked list with previous pointers
	 * @param start	The first word of the ladder
	 * @param end	The last word of the ladder
	 * @return		The word ladder
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	start = start.toUpperCase();
    	end = end.toUpperCase();

		BFSQueue = new LinkedList<>();

		ArrayList<String> BFSLadder = new ArrayList<>();
		LinkedList<BFSNode> nodesList = new LinkedList<>();
		BFSQueue.add(start);
		VisitedBFSWords.add(start);

		BFSNode currentNode = new BFSNode();

		while(!BFSQueue.isEmpty()){
			String word = BFSQueue.poll();

			//Find the most recent parent node
			for(BFSNode nodeSearch : nodesList){
				if(nodeSearch.word.equals(word)){
					currentNode = nodeSearch;
					break;
				}
			}

			//Initialize the node list if you need to
			if(nodesList.isEmpty()){
				currentNode = new BFSNode(word, null);
				nodesList.add(currentNode);
			}

			//Once the end word is found, go through the node list
			//and add the node chain to the ladder
			if(word.equals(end)){
				while(currentNode.previousNode != null){
					BFSLadder.add(currentNode.word);
					currentNode = currentNode.previousNode;
				}
				BFSLadder.add(currentNode.word);
				Collections.reverse(BFSLadder);
				BFSQueue.clear();
				return BFSLadder;
			}

			//Add all the words that are one letter different from the current node and
			//haven't been seen yet to the queue
			for(String wordToQueue : WordNeighbors.get(WordNeighborsIndex.indexOf(word))){
				if(!VisitedBFSWords.contains(wordToQueue)){
					BFSQueue.add(wordToQueue);
					VisitedBFSWords.add(wordToQueue);
					BFSNode newNode = new BFSNode(wordToQueue, currentNode);
					nodesList.add(newNode);
				}
			}
		}

		BFSLadder.add(start);
		BFSLadder.add(end);
		return BFSLadder;
	}


	/**
	 * Used in initialize(), finds the "neighboring" words of the current word
	 * @param word	The current word
	 * @return		An array list of neighboring words
	 */
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
	
	//Take in a ladder and print every word if available, otherwise print that no ladder was found
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder.size() == 2){
			System.out.println("no word ladder can be found between " + ladder.get(0).toLowerCase() + " and " + ladder.get(1).toLowerCase() + ".");
		} else {
			System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " + ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size()-1).toLowerCase() + ".");
			for (String word : ladder) {
				System.out.println(word);
			}
		}
	}

	//Find the difference in letters between two words
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
