import java.util.ArrayList;

public class State implements Comparable<State> {
	ArrayList<State> children;
	ArrayList<Character> path;
	int[][] puzzle;
	State parent;
	char move;
	int dist;
	int steps;

	public State(char move, int[][] puzzle, State parent) {
		this.children = new ArrayList<>();
		this.parent = parent;
		this.move = move;
		this.path = new ArrayList<Character>();
		this.steps = 0;
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
			for (int j = 0; j < puzzle[0].length; j++) {
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
		for (int i = 0; i < box.size(); i++) {
			dist += Math.abs(box.get(i)[0] - target.get(i)[0]) + Math.abs(box.get(i)[1] - target.get(i)[1]);
		}
		return dist;
	}

	public boolean checkForDeadlock() {
		for (int i = 1; i < this.puzzle.length - 1; i++) {
			for (int j = 1; j < this.puzzle[0].length - 1; j++) {
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
			ArrayList<State> states = new ArrayList<>();
			if ((puzzle[x][y - 1] == '$' || puzzle[x][y - 1] == '*') && (puzzle[x][y - 2] != '#')) {
				// push box left
			}

			if ((puzzle[x][y + 1] == '$' || puzzle[x][y + 1] == '*') && (puzzle[x][y + 2] != '#')) {
				// push box right
			}

			if ((puzzle[x - 1][y] == '$' || puzzle[x - 1][y] == '*') && (puzzle[x - 2][y] != '#')) {
				// push box up
			}

			if ((puzzle[x + 1][y] == '$' || puzzle[x + 1][y] == '*') && (puzzle[x + 2][y] != '#')) {
				// push box down
			}
			if (puzzle[x][y - 1] != '#') {
				// move left
			}
			if (puzzle[x][y + 1] != '#') {
				// move right
			}
			if (puzzle[x - 1][y] != '#') {
				// move up
			}
			if (puzzle[x + 1][y] != '#') {
				// move down
			}
			for (int i = 0; i < states.size(); i++) {
				this.children.add(states.get(i));
			}
		}
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
