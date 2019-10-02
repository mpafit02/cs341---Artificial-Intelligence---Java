import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * 
 * Use objects State for storing the data of a state
 * 
 * 
 * https://www.callicoder.com/java-priority-queue/
 * 
 * Use priority queue for the frontier (implement compareTo in the State object)
 * 
 * 
 * 
 */
public class SokobanPuzzleSolver {
	public static char[][] loadPuzzle(String filename) {
		File file = new File(filename);
		Scanner sc = null;
		char puzzle[][] = null;
		try {
			sc = new Scanner(file);
			String str = sc.nextLine();

			int N = Integer.parseInt(str, 10);
			int index = 0;
			int max = 0;
			String lines[] = new String[N];

			while (sc.hasNextLine()) {
				lines[index] = sc.nextLine();
				if (lines[index].length() > max) {
					max = lines[index].length();
				}
				index++;
				if (index >= N) {
					break;
				}
			}

			puzzle = new char[N][max];
			for (int i = 0; i < lines.length; i++) {
				for (int j = 0; j < lines[i].length(); j++) {
					puzzle[i][j] = lines[i].charAt(j);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return puzzle;
	}

	public static void printPuzzle(char[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				System.out.print(puzzle[i][j]);
			}
			System.out.println();
		}
	}


	public static ArrayList<Integer[]> blockedPositions(char[][] puzzle) {
		ArrayList<Integer[]> blockedPositions = new ArrayList<>();
		Integer[] pos = new Integer[2];
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j] == '#') {
					pos[0] = i;
					pos[1] = j;
					blockedPositions.add(pos);
					pos = new Integer[2];
				}
			}
		}
		return blockedPositions;
	}

	public static void main(String[] args) {
		String filenames[] = { ".\\puzzles\\sokoban_7a.txt" };
		for (String filename : filenames) {
			char puzzle[][] = loadPuzzle(filename);
			printPuzzle(puzzle);
			ArrayList<Integer[]> blockedPositions = blockedPositions(puzzle);
			System.out.println("Blocked postiions:");
			for (int i = 0; i < blockedPositions.size(); i++) {
				System.out.println("[" + blockedPositions.get(i)[0] + ", " + blockedPositions.get(i)[1] + "]");
			}
		}
	}

}
