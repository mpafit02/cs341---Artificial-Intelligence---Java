import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SokobanPuzzleSolver {
	public static Character[][] loadPuzzle(String filename) {
		File file = new File(filename);
		Scanner sc = null;
		Character puzzle[][] = null;
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

			puzzle = new Character[N][max];
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

	public static ArrayList<Character> AStarSolver(PriorityQueue<State> frontier, ArrayList<Character[][]> visited) {
		if (frontier.isEmpty()) {
			return null;
		}
		// Get the head of the queue
		State parent = frontier.poll();
		if (parent.isSolved()) {
			return parent.path;
		}
		visited.add(parent.puzzle);
		parent.CreateChildren();
		boolean isVisited = false;
		for (int i = 0; i < parent.children.size(); i++) {
			isVisited = false;
			State child = parent.children.get(i);
			for (Character[][] v_puzzle : visited) {
				if (isSame(v_puzzle, child.puzzle)) {
					isVisited = true;
					break;
				}
			}
			if (!isVisited) {
				frontier.add(child);
			}
		}
		return AStarSolver(frontier, visited);
	}

	public static boolean isSame(Character[][] puzzle1, Character[][] puzzle2) {
		for (int i = 0; i < puzzle1.length; i++) {
			for (int j = 0; j < puzzle1[0].length; j++) {
				if (puzzle1[i][j] != puzzle2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		long maxBytes = Runtime.getRuntime().maxMemory();
		System.out.println("Max memory: " + maxBytes / 1024 / 1024 + " MB");
		String filenames[] = { ".\\puzzles\\SOK_EASY1.txt", ".\\puzzles\\SOK_EASY2.txt", ".\\puzzles\\SOK_EASY3.txt",
				".\\puzzles\\SOK_MED1.txt", ".\\puzzles\\SOK_HARD1.txt" };
		ArrayList<Character[][]> puzzles = new ArrayList<>();
		for (String filename : filenames) {
			puzzles.add(loadPuzzle(filename));
		}
		for (Character[][] puzzle : puzzles) {
			// Create a PriorityQueue
			PriorityQueue<State> frontier = new PriorityQueue<>();
			ArrayList<Character[][]> visited = new ArrayList<>();
			frontier.add(new State(' ', puzzle, null));

			ArrayList<Character> path = AStarSolver(frontier, visited);
			for (int i = 0; i < path.size(); i++) {
				System.out.print(path.get(i));
			}
			System.out.println();
			// System.out.println(path);
		}
	}

}
