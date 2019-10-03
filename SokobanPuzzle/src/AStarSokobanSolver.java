
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * This class is an A* algorithm for solving the Sokoban puzzle. It uses a
 * priority queue in order to expand each time the state with the least
 * distance. The distance is been calculated by the State class with two
 * different methods. It uses either The Manhattan distance plus the number of
 * steps covered by the player or the number of misplaced boxes plus the steps
 * covered by the player.
 * 
 * @author Marios
 *
 */
public class AStarSokobanSolver {
	private PriorityQueue<State> frontier;
	private ArrayList<Character[][]> visited;
	private ArrayList<Character[][]> states;
	private ArrayList<Character> path;
	private int nodesCreated;

	/**
	 * Constructor for creating a new A* Sokoban solver. It creates the list for
	 * keeping the visited nodes and a priority queue.
	 * 
	 * @param puzzle
	 */
	public AStarSokobanSolver(Character[][] puzzle) {
		// Create a PriorityQueue
		frontier = new PriorityQueue<>();
		visited = new ArrayList<>();
		states = new ArrayList<>();
		frontier.add(new State(' ', puzzle, null));
		nodesCreated = 0;
	}

	/**
	 * It just calls the A* star recursive solver method and stores the result in
	 * the path list which is the path for the solution.
	 */
	public void solve() {
		State goal = AStarSolver(frontier, visited);
		if (goal != null) {
			this.path = goal.path;
			addToStates(states, goal);
			states.add(goal.puzzle);
		}
	}

	private void addToStates(ArrayList<Character[][]> states, State state) {
		if (state == null) {
			return;
		}
		addToStates(states, state.parent);
		states.add(state.puzzle);
	}

	/**
	 * This is the A* star solver which pops the state with the most priority from
	 * the priority queue, checks if it is the goal state, creates the children of
	 * that state and adds them in the frontier(priority queue) if they are not
	 * visited already.
	 * 
	 * @param frontier
	 * @param visited
	 * @return
	 */
	private State AStarSolver(PriorityQueue<State> frontier, ArrayList<Character[][]> visited) {
		if (frontier.isEmpty()) {
			return null;
		}
		nodesCreated++;
		// Get the head of the queue
		State parent = frontier.poll();
		if (parent.isSolved()) {
			return parent;
		}
		visited.add(parent.puzzle);
		parent.createChildren();
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

	/**
	 * This method compares whether two puzzles are the same.
	 * 
	 * @param puzzle1
	 * @param puzzle2
	 * @return
	 */
	private boolean isSame(Character[][] puzzle1, Character[][] puzzle2) {
		for (int i = 0; i < puzzle1.length; i++) {
			for (int j = 0; j < puzzle1[0].length; j++) {
				if (puzzle1[i][j] != puzzle2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public ArrayList<Character> getPath() {
		return path;
	}

	public ArrayList<Character[][]> getStates() {
		return states;
	}

	public int getNodesCrated() {
		return nodesCreated;
	}

}
