import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    private class BoardKey implements Comparable<BoardKey> {
        Board board;
        BoardKey parent;
        int step;
        int weight;
        public BoardKey(Board board, int step, BoardKey parent) {
            this.board = board;
            this.step = step;
            this.parent = parent;
            weight = this.step + board.manhattan();
        }

        private int weight() {
            return weight;
        }
        @Override
        public int compareTo(BoardKey that) {
            return Integer.compare(this.weight(), that.weight());
        }
    }

    private ArrayList<Board> solution;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        solution = new ArrayList<>();
        if (initial.isGoal()) {
            solution.add(initial);
            return;
        }

        MinPQ<BoardKey> minPQ = new MinPQ<>();
        MinPQ<BoardKey> minPQTwin = new MinPQ<>();

        BoardKey bk;
        BoardKey bkTwin;
        minPQ.insert(new BoardKey(initial, 0, null));
        minPQTwin.insert(new BoardKey(initial.twin(), 0, null));
        ex:
        while (!minPQ.isEmpty() || !minPQTwin.isEmpty()) {
            bk = minPQ.delMin();
            for (Board board: bk.board.neighbors()) {
                if (board.isGoal()) {
                    // разматываем решение
                    // System.out.println(board);
                    solution.add(0, board);
                    while (bk.parent != null) {
                        solution.add(0, bk.board);
                        bk = bk.parent;
                    }
                    solution.add(0, initial);
                    break ex;
                }
                if (!checkInParent(bk.parent, board))
                    minPQ.insert(new BoardKey(board, bk.step + 1, bk));
            }

            bkTwin = minPQTwin.delMin();
            for (Board board: bkTwin.board.neighbors()) {
                if (board.isGoal()) {
                    break ex;
                }
                if (!checkInParent(bkTwin.parent, board)) {
                    minPQTwin.insert(new BoardKey(board, bkTwin.step + 1, bkTwin));
                }
            }
        }
    }

    private boolean checkInParent(BoardKey parent, Board board) {
//        while (parent != null) {
//            if (parent.board.equals(board))
//                return true;
//            parent = parent.parent;
//        }
//        return false;
        if (parent == null)
            return false;
        return parent.board.equals(board);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution.size() > 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solution;
        }
        else
            return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args.length > 0 ? args[0] : "puzzle2x2-unsolvable.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}