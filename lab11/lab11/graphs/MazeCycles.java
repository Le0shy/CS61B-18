package lab11.graphs;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    //private int vertex_count = 0;
    private final int[] parent;
    private boolean detected;

    public MazeCycles(Maze m) {
        super(m);
        //vertex_count = m.V();
        detected = false;
        distTo[0] = 0;
        edgeTo[0] = 0;
        parent = new int[maze.V()];
        parent[0] = -1;
    }

    @Override
    public void solve() {
        // Your code here!
        cd(0);
    }

    // Helper methods go here
    private void cd(int v) {
        if (detected) {
            return;
        }

        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                parent[w] = v;
                distTo[w] = distTo[v] + 1;
                cd(w);
            } else if (parent[v] != w) {
                /* detect a cycle */
                detected = true;
                parent[w] = v;
                setEdges(w, v);
                return;
            }
        }
    }

    private void setEdges(int start, int end) {
        for (; ; end = parent[end]) {
            edgeTo[end] = parent[end];
            /* display */
            announce();
            if (start == end) {
                break;
            }
        }
    }
}


