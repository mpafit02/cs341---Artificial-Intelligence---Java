import java.util.ArrayList;

public class State implements Comparable<State> {
	ArrayList<State> children;
	ArrayList<Character> path;
	char[][] puzzle;
	State parent;
	char move;
	int dist;
	int steps;

	public State(char move, char[][] puzzle, State parent) {
		this.children = new ArrayList<>();
		this.parent = parent;
		this.move = move;
		this.path = new ArrayList<Character>();
		this.steps = 0;
		this.puzzle = new char[puzzle.length][puzzle[0].length];
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
		this.dist = GetDist();
	}

	private int GetDist() {
		int dist = this.steps;
		ArrayList<Integer[]> box = new ArrayList<>();
		ArrayList<Integer[]> target = new ArrayList<>();
		Integer[] pos = new Integer[2];
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < this.puzzle[0].length; j++) {
				if (this.puzzle[i][j] == '$') {
					pos[0] = i;
					pos[1] = j;
					box.add(pos);
					pos = new Integer[2];
				} else if (this.puzzle[i][j] == '.') {
					pos[0] = i;
					pos[1] = j;
					target.add(pos);
					pos = new Integer[2];
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

	public boolean isDeadlock(char[][] puzzle) {
		for (int i = 1; i < puzzle.length - 1; i++) {
			for (int j = 1; j < puzzle[0].length - 1; j++) {
				if ((puzzle[i][j] == '$' && puzzle[i][j - 1] == '#' && puzzle[i - 1][j] == '#')
						|| (puzzle[i][j] == '$' && puzzle[i][j - 1] == '#' && puzzle[i + 1][j] == '#')
						|| (puzzle[i][j] == '$' && puzzle[i][j + 1] == '#' && puzzle[i - 1][j] == '#')
						|| (puzzle[i][j] == '$' && puzzle[i][j + 1] == '#' && puzzle[i + 1][j] == '#')) {
					return true;
				}
			}
		}
		return false;
	}

	public void CreateChildren() {
		if (this.children.isEmpty()) {
			int[] player = playersPosition();
			int x = player[0];
			int y = player[1];
			// Left
			if ((puzzle[x][y - 1] == '$' || this.puzzle[x][y - 1] == '*') && (puzzle[x][y - 2] != '#')) {
				char[][] puzzle_l = copyArray(puzzle);
				pushBox(x, y, x, y - 1, x, y - 2, puzzle_l);
				if (!isDeadlock(puzzle_l)) {
					this.children.add(new State('L', puzzle_l, this));
				}
			} else if (puzzle[x][y - 1] == ' ' || puzzle[x][y - 1] == '.') {
				char[][] puzzle_l = copyArray(puzzle);
				movePlayer(x, y, x, y - 1, puzzle_l);
				if (!isDeadlock(puzzle_l)) {
					this.children.add(new State('l', puzzle_l, this));
				}
			}
			// Right
			if ((puzzle[x][y + 1] == '$' || this.puzzle[x][y + 1] == '*') && (puzzle[x][y + 2] != '#')) {
				char[][] puzzle_r = copyArray(puzzle);
				pushBox(x, y, x, y + 1, x, y + 2, puzzle_r);
				if (!isDeadlock(puzzle_r)) {
					this.children.add(new State('R', puzzle_r, this));
				}
			} else if (puzzle[x][y + 1] == ' ' || puzzle[x][y + 1] == '.') {
				char[][] puzzle_r = copyArray(puzzle);
				movePlayer(x, y, x, y + 1, puzzle_r);
				if (!isDeadlock(puzzle_r)) {
					this.children.add(new State('r', puzzle_r, this));
				}
			}
			// Up
			if ((puzzle[x - 1][y] == '$' || this.puzzle[x - 1][y] == '*') && (puzzle[x - 2][y] != '#')) {
				char[][] puzzle_u = copyArray(puzzle);
				pushBox(x, y, x - 1, y, x - 2, y, puzzle_u);
				if (!isDeadlock(puzzle_u)) {
					this.children.add(new State('U', puzzle_u, this));
				}
			} else if (puzzle[x - 1][y] == ' ' || puzzle[x - 1][y] == '.') {
				char[][] puzzle_u = copyArray(puzzle);
				movePlayer(x, y, x - 1, y, puzzle_u);
				if (!isDeadlock(puzzle_u)) {
					this.children.add(new State('u', puzzle_u, this));
				}
			}
			// Down
			if ((puzzle[x + 1][y] == '$' || this.puzzle[x + 1][y] == '*') && (puzzle[x + 2][y] != '#')) {
				char[][] puzzle_d = copyArray(puzzle);
				pushBox(x, y, x + 1, y, x + 2, y, puzzle_d);
				if (!isDeadlock(puzzle_d)) {
					this.children.add(new State('D', puzzle_d, this));
				}
			} else if (puzzle[x + 1][y] == ' ' || puzzle[x + 1][y] == '.') {
				char[][] puzzle_d = copyArray(puzzle);
				movePlayer(x, y, x + 1, y, puzzle_d);
				if (!isDeadlock(puzzle_d)) {
					this.children.add(new State('d', puzzle_d, this));
				}
			}
		}
	}

	private void pushBox(int old_x, int old_y, int new_x, int new_y, int box_x, int box_y, char[][] puzzle) {
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

	private void movePlayer(int old_x, int old_y, int new_x, int new_y, char[][] puzzle) {
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

	private char[][] copyArray(char[][] puzzle) {
		char[][] puzzle_new = new char[puzzle.length][puzzle[0].length];
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				puzzle_new[i][j] = puzzle[i][j];
			}
		}
		return puzzle_new;
	}

	public int[] playersPosition() {
		int position[] = new int[2];
		for (int i = 0; i < this.puzzle.length; i++) {
			for (int j = 0; j < this.puzzle[0].length; j++) {
				if (this.puzzle[i][j] == '@' || this.puzzle[i][j] == '+') {
					position[0] = i;
					position[1] = j;
				}
			}
		}
		return position;
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
