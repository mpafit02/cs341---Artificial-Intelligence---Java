import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * https://www.callicoder.com/java-priority-queue/
 * 
 * Frontier,
 * Close list, states already created
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

			State root = new State('-', puzzle, null);

			System.out.println("-root");
			System.out.println(root.toString());

			root.CreateChildren();
			for (int i = 0; i < root.children.size(); i++) {
				System.out.println("-" + i);
				System.out.println(root.children.get(i).toString());
				root.children.get(i).CreateChildren();
				for (int j = 0; j < root.children.get(i).children.size(); j++) {
					System.out.println("---" + j);
					System.out.println(root.children.get(i).children.get(j).toString());
				}
				System.out.println();
			}

			// Create a PriorityQueue
			PriorityQueue<State> frontier = new PriorityQueue<>();
//			ArrayList<Integer[]> blockedPositions = blockedPositions(puzzle);
//			System.out.println("Blocked postiions:");
//			for (int i = 0; i < blockedPositions.size(); i++) {
//				System.out.println("[" + blockedPositions.get(i)[0] + ", " + blockedPositions.get(i)[1] + "]");
//			}
		}
	}

}
