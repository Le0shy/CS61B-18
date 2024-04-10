package hw4.puzzle;
import java.util.ArrayList;

public class Board implements WorldState {
    private int[][] board;
    private int size;
    private int zeroX;
    private int zeroY;

    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                board[i][j] = tiles[i][j];
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    public int size() {
        return size;
    }

    public Iterable<WorldState> neighbors() {
        ArrayList<WorldState> nbs = new ArrayList<>();
        Board neighbor;

        if (validMoveZero(zeroX - 1, zeroY)) {
            neighbor = new Board(board);
            transfrom(neighbor, zeroX - 1, zeroY);
            nbs.add(neighbor);
        }
        if (validMoveZero(zeroX + 1, zeroY)) {
            neighbor = new Board(board);
            transfrom(neighbor, zeroX + 1, zeroY);
            nbs.add(neighbor);
        }
        if (validMoveZero(zeroX, zeroY - 1)) {
            neighbor = new Board(board);
            transfrom(neighbor, zeroX, zeroY - 1);
            nbs.add(neighbor);
        }
        if (validMoveZero(zeroX, zeroY + 1)) {
            neighbor = new Board(board);
            transfrom(neighbor, zeroX, zeroY + 1);
            nbs.add(neighbor);
        }

        return nbs;
    }

    public int hamming() {
        int distance = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (board[i][j] != 0 && board[i][j] != i * size + j + 1) {
                    distance += 1;
                }
            }
        }
        return distance;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (board[i][j] != 0) {
                    distance += Math.abs(toGoalX(board[i][j]) - i)
                            + Math.abs(toGoalY(board[i][j]) - j);
                }
            }
        }
        return distance;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        Board y1 = (Board) y;
        if (y1.size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (this.board[i][j] != y1.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return -1;
    }

    private int toGoalX(int v) {
        return (v - 1) / size();
    }

    private int toGoalY(int v) {
        return (v - 1) % size();
    }

    private boolean validMoveZero(int newX, int newY) {
        return (newX >= 0 && newX < size && newY >= 0 && newY < size);
    }

    private void transfrom(Board b, int newX, int newY) {
        int temp = b.board[newX][newY];
        b.board[newX][newY] = b.board[zeroX][zeroY];
        b.board[zeroX][zeroY] = temp;
        b.zeroX = newX;
        b.zeroY = newY;
    }
    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
