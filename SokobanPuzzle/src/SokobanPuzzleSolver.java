import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a testing main class for the A* Sokoban puzzle solver.
 * 
 * @author Marios
 *
 */
public class SokobanPuzzleSolver {
	private static Scanner scan;

	/**
	 * This method loads a puzzle from a file. It takes as input a filename. It
	 * opens the file and reads the first character which is the number of rows for
	 * the Sokoban puzzle. It reads two per two the puzzle and stores the output in
	 * a two dimensional character array. The space ' ' represents an empty spot in
	 * the puzzle. The character '@' represents the position of the player. The '.'
	 * represents a target spot for a box. The '$' represents a box. The '#'
	 * represents a wall. The '*' represents a box placed on a target spot. The '+'
	 * represents the player standing on a target spot.
	 * 
	 * @param filename
	 * @return
	 */
	public static Character[][] loadPuzzle(Scanner sc) {
		Character puzzle[][] = null;
		try {
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

	/**
	 * This method prints the puzzle/
	 * 
	 * @param puzzle
	 */
	public static void printPuzzle(Character[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			System.out.print("\t");
			for (int j = 0; j < puzzle[0].length; j++) {
				if (puzzle[i][j] != null) {
					System.out.print(puzzle[i][j]);
				}
			}
			System.out.println();
		}
	}

	/**
	 * Run this method if you wan to see some examples of this solution.
	 */
	public static void autoTesting() {
		// Sokoban filenames
		String filenames[] = { ".\\puzzles\\SOK_EASY1.txt", ".\\puzzles\\SOK_EASY2.txt", ".\\puzzles\\SOK_EASY3.txt",
				".\\puzzles\\SOK_EASY4.txt", ".\\puzzles\\SOK_EASY5.txt", ".\\puzzles\\SOK_MED1.txt",
				".\\puzzles\\SOK_MED2.txt", ".\\puzzles\\SOK_MED3.txt", ".\\puzzles\\SOK_MED4.txt",
				".\\puzzles\\SOK_HARD1.txt", ".\\puzzles\\SOK_HARD2.txt", ".\\puzzles\\SOK_HARD3.txt",
				".\\puzzles\\SOK_HARD4.txt", ".\\puzzles\\SOK_HARD5.txt", ".\\puzzles\\SOK_UNSOLVABLE1.txt" };
		ArrayList<Character[][]> puzzles = new ArrayList<>();
		// Load the puzzles from the files
		for (String filename : filenames) {
			try {
				File file = new File(filename);
				Scanner sc = new Scanner(file);
				puzzles.add(loadPuzzle(sc));
			} catch (FileNotFoundException e) {
				System.out.println("Wrong input files.");
				System.exit(0);
			}
		}
		// Solve the puzzles
		for (Character[][] puzzle : puzzles) {
			System.out.println("Input Puzzle: ");
			printPuzzle(puzzle);
			System.out.println();

			// Create an A* solver object.
			AStarSokobanSolver solution = new AStarSokobanSolver(puzzle);

			long start_time = System.currentTimeMillis();
			solution.solve();
			long end_time = System.currentTimeMillis();

			if (solution.getPath() == null) {
				System.out.println("No solution found");
			} else {
				System.out.println("Steps: ");
				for (int i = 0; i <= solution.getPath().size(); i++) {
					if (i == solution.getPath().size()) {
						System.out.println(">GOAL");
					} else {
						char c = solution.getPath().get(i);
						if (c == 'r' || c == 'R') {
							System.out.println(">RIGHT");
						} else if (c == 'l' || c == 'L') {
							System.out.println(">LEFT");
						} else if (c == 'u' || c == 'U') {
							System.out.println(">UP");
						} else {
							System.out.println(">DOWN");
						}
					}
					printPuzzle(solution.getStates().get(i));
				}
			}
			System.out.println();
			System.out.print("Solution: ");
			for (int i = 0; i < solution.getPath().size(); i++) {
				System.out.print(solution.getPath().get(i) + " ");
			}
			System.out.println();
			System.out.printf("Total Time: %.3f sec\n", (end_time - start_time) * 0.001);
			System.out.println("Nodes Created: " + solution.getNodesCrated() + " nodes");
		}
	}

	public static void main(String[] args) {
		// Uncomment this if you wan to test some examples.
		// autoTesting();
		System.out.println("Hello, This is the A* Sokoban Solver");
		System.out.println("Please give me a filename of a Sokoban puzzle: ");
		scan = new Scanner(System.in);
		String filename = scan.next();
		Character[][] puzzle = null;
		try {
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			puzzle = loadPuzzle(sc);
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Wrong input files.");
			System.exit(0);
		}
		System.out.println("Input Puzzle: ");
		printPuzzle(puzzle);
		System.out.println();

		// Create an A* solver object.
		AStarSokobanSolver solution = new AStarSokobanSolver(puzzle);

		long start_time = System.currentTimeMillis();
		solution.solve();
		long end_time = System.currentTimeMillis();

		if (solution.getPath() == null) {
			System.out.println("No solution found");
		} else {
			System.out.println("Steps: ");
			for (int i = 0; i <= solution.getPath().size(); i++) {
				if (i == solution.getPath().size()) {
					System.out.println(">GOAL");
				} else {
					char c = solution.getPath().get(i);
					if (c == 'r' || c == 'R') {
						System.out.println(">RIGHT");
					} else if (c == 'l' || c == 'L') {
						System.out.println(">LEFT");
					} else if (c == 'u' || c == 'U') {
						System.out.println(">UP");
					} else {
						System.out.println(">DOWN");
					}
				}
				printPuzzle(solution.getStates().get(i));
			}
		}
		System.out.println();
		System.out.print("Solution: ");
		for (int i = 0; i < solution.getPath().size(); i++) {
			System.out.print(solution.getPath().get(i) + " ");
		}
		System.out.println();
		System.out.printf("Total Time: %.3f sec\n", (end_time - start_time) * 0.001);
		System.out.println("Nodes Created: " + solution.getNodesCrated() + " nodes");
	}

}
