package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {

    private int movesToGoal;
    private LinkedList<WorldState> sequence;
    public class SearchNode {
        WorldState ws;
        int movesTo;
        SearchNode previous;

        int heuristic;

        SearchNode(WorldState worldState) {
            ws = worldState;
            movesTo = 0;
            previous = null;
            heuristic = -1;
        }

        SearchNode(WorldState worldState, int m, SearchNode pre) {
            ws = worldState;
            movesTo = m;
            previous = pre;
            heuristic = -1;
        }
    }

    public class SearchNodePriority implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.heuristic == -1) {
                o1.heuristic = o1.ws.estimatedDistanceToGoal();
            }
            if (o2.heuristic == -1) {
                o2.heuristic = o2.ws.estimatedDistanceToGoal();
            }
            return o1.movesTo + o1.heuristic - o2.movesTo - o2.heuristic;
        }
    }
    MinPQ<SearchNode> pq;

    /* Constructor which solves the puzzle, computing everything necessary for moves() and solution()
    to not have to solve the problem again. Solves the puzzle using the A* algorithm.
    Assumes a solution exists. */
    public Solver(WorldState initial) {
        initSolver(initial);
        SearchNode iter = pq.delMin();
        while (!iter.ws.isGoal()) {
            for (WorldState neighbor : iter.ws.neighbors()) {
                if (iter.previous == null || !neighbor.equals(iter.previous.ws)) {
                    pq.insert(new SearchNode(neighbor, iter.movesTo + 1, iter));
                    /*System.out.println("enque\n");*/
                }
            }
            iter = pq.delMin();
        }

        movesToGoal = iter.movesTo;
        for (int i = movesToGoal; i >= 0; i -= 1) {
            sequence.addFirst(iter.ws);
            iter = iter.previous;
        }
    }
    private void initSolver(WorldState initial) {
        sequence = new LinkedList<>();
        SearchNodePriority priority = new SearchNodePriority();
        pq = new MinPQ<SearchNode>(priority);
        SearchNode start = new SearchNode(initial);
        pq.insert(start);
    }

    /*  Returns the minimum number of moves to solve the puzzle starting
    at the initial WorldState. */
    public int moves() {
        return movesToGoal;
    }

    /*     Returns a sequence of WorldStates from the initial WorldState to the solution. */
    public Iterable<WorldState> solution() {
        return sequence;
    }
}

