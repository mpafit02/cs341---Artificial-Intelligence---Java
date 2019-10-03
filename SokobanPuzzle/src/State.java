import java.util.ArrayList;

/**
 * The state represents a state of the Sokoban Puzzle. It contains a list of
 * state's children and its parent. It stores the path that had followed in an
 * array list and the move that it made. Also, it keeps the number of steps that
 * have been made to reach the specific state. It has the puzzle represented as
 * a two dimensional array of characters. Finally it calculates the distance
 * until the goal state which it is been calculated with two different methods.
 * Those two heuristics affect a lot the performance of the A* algorithm. The
 * first heuristic (getDist1) is the number of steps already made plus the
 * Manhattan distance between an unplaced box and a target spot. The second
 * heuristic (getDist2) is the number of steps already made plus the number of
 * misplaced boxes.
 * 
 * @author Marios
 *
 */
public class State implements Comparable<State> {
	ArrayList<State> children;
	ArrayList<Character> path;
	Character[][] puzzle;
	State parent;
	char move;
	int dist;
	int steps;

	/**
	 * This is the constructor for the creation of a new state. Uncomment the
	 * this.dist = getDist2(puzzle) to try the second heuristic. The first heuristic
	 * performs better than the second one.
	 * 
	 * @param move
	 * @param puzzle
	 * @param parent
	 */
	public State(char move, Character[][] puzzle, State parent, int heuristic) {
		this.children = new ArrayList<>();
		this.parent = parent;
		this.move = move;
		this.path = new ArrayList<Character>();
		this.steps = 0;
		this.puzzle = new Character[puzzle.length][puzzle[0].length];
		if (parent != null) {
			for (int i = 0; i < parent.path.size(); i++) {
				this.path.add(parent.path.get(i));
			}
			this.path.add(move);
			this.steps = parent.steps + 1;
		}
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				this.puzzle[i][j] = puzzle[i][j];
			}
		}
		if (heuristic == 1) {
			this.dist = getDist1(puzzle);
		} else {
			this.dist = getDist2(puzzle);
		}
	}

	/**
	 * This method calculates the possible moves until the goal state from the
	 * current state. This heuristic is the number of the steps covered already by
	 * the player plus the Manhattan distance between a misplaced box and a target
	 * spot.
	 * 
	 * @param puzzle
	 * @return
	 */
	private int getDist1(Character[][] puzzle) {
		int dist = this.steps;
		ArrayList<Integer[]> box = new ArrayList<>();
		ArrayList<Integer[]> target = new ArrayList<>();
		Integer[] pos = new Integer[2];
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				if (puzzle[i][j] != null) {
					if (puzzle[i][j] == '$') {
						pos[0] = i;
						pos[1] = j;
						box.add(pos);
						pos = new Integer[2];
					} else if (puzzle[i][j] == '.') {
						pos[0] = i;
						pos[1] = j;
						target.add(pos);
						pos = new Integer[2];
					}
				}
			}
		}
		if (target.size() == box.size()) {
			for (int i = 0; i < box.size(); i++) {
				dist += Math.abs(box.get(i)[0] - target.get(i)[0]) + Math.abs(box.get(i)[1] - target.get(i)[1]);
			}
		}
		return dist;
	}

	/**
	 * 
	 * This method calculates the possible moves until the goal state from the
	 * current state. This heuristic is the number of the steps covered already by
	 * the player plus the number of misplaced boxes.
	 * 
	 * @param puzzle
	 * @return
	 */
	private int getDist2(Character[][] puzzle) {
		int dist = this.steps;
		int missplaced = 0;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				if (puzzle[i][j] != null && puzzle[i][j] == '$') {
					missplaced++;
				}
			}
		}
		dist += missplaced;
		return dist;
	}

	/**
	 * This method checks whether a puzzle is in deadlock. If we have the situation
	 * that a box is placed in a corner then we have a deadlock. Also, if two boxes
	 * are next to each other and there is a wall behind them then this case is a
	 * deadlock too.
	 * 
	 * @param puzzle
	 * @return true if it is in a deadlock
	 */
	public boolean isDeadlock(Character[][] puzzle) {
		for (int i = 1; i < puzzle.length - 1; i++) {
			for (int j = 1; j < puzzle[0].length - 1; j++) {
				if (puzzle[i][j] != null && puzzle[i][j - 1] != null && puzzle[i][j - 1] != null
						&& puzzle[i - 1][j] != null && puzzle[i + 1][j] != null) {
					if ((puzzle[i][j] == '$' && puzzle[i][j - 1] == '#' && puzzle[i - 1][j] == '#')
							|| (puzzle[i][j] == '$' && puzzle[i][j - 1] == '#' && puzzle[i + 1][j] == '#')
							|| (puzzle[i][j] == '$' && puzzle[i][j + 1] == '#' && puzzle[i - 1][j] == '#')
							|| (puzzle[i][j] == '$' && puzzle[i][j + 1] == '#' && puzzle[i + 1][j] == '#')) {
						return true;
					}
					if ((puzzle[i][j] == '$' && puzzle[i][j - 1] == '#' && puzzle[i - 1][j] == '$'
							&& puzzle[i - 1][j - 1] == '#')
							|| (puzzle[i][j] == '$' && puzzle[i][j - 1] == '$' && puzzle[i - 1][j] == '#'
									&& puzzle[i - 1][j - 1] == '#')
							|| (puzzle[i][j] == '$' && puzzle[i][j - 1] == '#' && puzzle[i + 1][j] == '$'
									&& puzzle[i + 1][j - 1] == '#')
							|| (puzzle[i][j] == '$' && puzzle[i][j - 1] == '$' && puzzle[i + 1][j] == '#'
									&& puzzle[i + 1][j - 1] == '#')) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This method creates all the children of the current state. Before adding a
	 * child in the children list it checks wether we have a deadlock or not.
	 */
	public void createChildren(int heuristic) {
		if (this.children.isEmpty()) {
			int[] player = playersPosition();
			int x = player[0];
			int y = player[1];
			// Left
			if ((puzzle[x][y - 1] == '$' || this.puzzle[x][y - 1] == '*')
					&& (puzzle[x][y - 2] != '#' && puzzle[x][y - 2] != '$' && puzzle[x][y - 2] != '*')) {
				Character[][] puzzle_l = copyArray(puzzle);
				pushBox(x, y, x, y - 1, x, y - 2, puzzle_l);
				if (!isDeadlock(puzzle_l)) {
					this.children.add(new State('L', puzzle_l, this, heuristic));
				}
			} else if (puzzle[x][y - 1] == ' ' || puzzle[x][y - 1] == '.') {
				Character[][] puzzle_l = copyArray(puzzle);
				movePlayer(x, y, x, y - 1, puzzle_l);
				if (!isDeadlock(puzzle_l)) {
					this.children.add(new State('l', puzzle_l, this, heuristic));
				}
			}
			// Right
			if ((puzzle[x][y + 1] == '$' || this.puzzle[x][y + 1] == '*')
					&& (puzzle[x][y + 2] != '#' && puzzle[x][y + 2] != '$' && puzzle[x][y + 2] != '*')) {
				Character[][] puzzle_r = copyArray(puzzle);
				pushBox(x, y, x, y + 1, x, y + 2, puzzle_r);
				if (!isDeadlock(puzzle_r)) {
					this.children.add(new State('R', puzzle_r, this, heuristic));
				}
			} else if (puzzle[x][y + 1] == ' ' || puzzle[x][y + 1] == '.') {
				Character[][] puzzle_r = copyArray(puzzle);
				movePlayer(x, y, x, y + 1, puzzle_r);
				if (!isDeadlock(puzzle_r)) {
					this.children.add(new State('r', puzzle_r, this, heuristic));
				}
			}
			// Up
			if ((puzzle[x - 1][y] == '$' || this.puzzle[x - 1][y] == '*')
					&& (puzzle[x - 2][y] != '#' && puzzle[x - 2][y] != '$' && puzzle[x - 2][y] != '*')) {
				Character[][] puzzle_u = copyArray(puzzle);
				pushBox(x, y, x - 1, y, x - 2, y, puzzle_u);
				if (!isDeadlock(puzzle_u)) {
					this.children.add(new State('U', puzzle_u, this, heuristic));
				}
			} else if (puzzle[x - 1][y] == ' ' || puzzle[x - 1][y] == '.') {
				Character[][] puzzle_u = copyArray(puzzle);
				movePlayer(x, y, x - 1, y, puzzle_u);
				if (!isDeadlock(puzzle_u)) {
					this.children.add(new State('u', puzzle_u, this, heuristic));
				}
			}
			// Down
			if ((puzzle[x + 1][y] == '$' || puzzle[x + 1][y] == '*')
					&& (puzzle[x + 2][y] != '#' && puzzle[x + 2][y] != '$' && puzzle[x + 2][y] != '*')) {
				Character[][] puzzle_d = copyArray(puzzle);
				pushBox(x, y, x + 1, y, x + 2, y, puzzle_d);
				if (!isDeadlock(puzzle_d)) {
					this.children.add(new State('D', puzzle_d, this, heuristic));
				}
			} else if (puzzle[x + 1][y] == ' ' || puzzle[x + 1][y] == '.') {
				Character[][] puzzle_d = copyArray(puzzle);
				movePlayer(x, y, x + 1, y, puzzle_d);
				if (!isDeadlock(puzzle_d)) {
					this.children.add(new State('d', puzzle_d, this, heuristic));
				}
			}
		}
	}

	/**
	 * Method to push a box and move the player too.
	 * 
	 * @param old_x
	 * @param old_y
	 * @param new_x
	 * @param new_y
	 * @param box_x
	 * @param box_y
	 * @param puzzle
	 */
	private void pushBox(int old_x, int old_y, int new_x, int new_y, int box_x, int box_y, Character[][] puzzle) {
		if (puzzle[old_x][old_y] == '+') {
			puzzle[old_x][old_y] = '.';
		} else if (puzzle[old_x][old_y] == '@') {
			puzzle[old_x][old_y] = ' ';
		}
		if (puzzle[new_x][new_y] == '*') {
			puzzle[new_x][new_y] = '+';
		} else {
			puzzle[new_x][new_y] = '@';
		}
		if (puzzle[box_x][box_y] == '.') {
			puzzle[box_x][box_y] = '*';
		} else {
			puzzle[box_x][box_y] = '$';
		}
	}

	/**
	 * Method to move the player to a new position.
	 * 
	 * @param old_x
	 * @param old_y
	 * @param new_x
	 * @param new_y
	 * @param puzzle
	 */
	private void movePlayer(int old_x, int old_y, int new_x, int new_y, Character[][] puzzle) {
		if (puzzle[old_x][old_y] == '+') {
			puzzle[old_x][old_y] = '.';
		} else if (puzzle[old_x][old_y] == '@') {
			puzzle[old_x][old_y] = ' ';
		}
		if (puzzle[new_x][new_y] == '.') {
			puzzle[new_x][new_y] = '+';
		} else {
			puzzle[new_x][new_y] = '@';
		}
	}

	/**
	 * This method creates a new array, copies the one that took as argument and
	 * then returns the instance of the new array.
	 * 
	 * @param puzzle
	 * @return
	 */
	private Character[][] copyArray(Character[][] puzzle) {
		Character[][] puzzle_new = new Character[puzzle.length][puzzle[0].length];
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				puzzle_new[i][j] = puzzle[i][j];
			}
		}
		return puzzle_new;
	}

	/**
	 * Returns the players position.
	 * 
	 * @return two element array wich is the x and y of the player in the puzzle.
	 */
	public int[] playersPosition() {
		int position[] = new int[2];
		for (int i = 0; i < this.puzzle.length; i++) {
			for (int j = 0; j < this.puzzle[0].length; j++) {
				if (this.puzzle[i][j] != null) {
					if (this.puzzle[i][j] == '@' || this.puzzle[i][j] == '+') {
						position[0] = i;
						position[1] = j;
					}
				}
			}
		}
		return position;
	}

	/**
	 * Checks if a puzzle is solved.
	 * 
	 * @return
	 */
	public boolean isSolved() {
		for (int i = 0; i < this.puzzle.length; i++) {
			for (int j = 0; j < this.puzzle[0].length; j++) {
				if (this.puzzle[i][j] != null && this.puzzle[i][j] == '$') {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns a string with the puzzle ready for printing.
	 * 
	 * @param puzzle
	 * @return
	 */
	private String printPuzzle(Character[][] puzzle) {
		String output = "";
		for (int i = 0; i < puzzle.length; i++) {
			output += "\t";
			for (int j = 0; j < puzzle[0].length; j++) {
				output += puzzle[i][j];
			}
			output += "\n";
		}
		return output;
	}

	@Override
	public String toString() {
		String output = "State {\nChildren : " + children.size() + ",\nPath: " + path.toString() + ",\nMove: " + move
				+ ",\nDistance: " + dist + ",\nSteps: " + steps + ",\nPuzzle: \n";
		output += printPuzzle(this.puzzle) + "}\n";
		return output;
	}

	@Override
	public int compareTo(State state) {
		if (this.dist > state.dist) {
			return 1;
		} else if (this.dist < state.dist) {
			return -1;
		} else {
			return 0;
		}
	}
}
