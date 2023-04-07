import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;


public class Board94 {
    private class Pos {
        private final int row;
        private final int col;
        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;

            if (obj == null) return false;
            if (obj.getClass() != this.getClass()) return false;

            Pos that = (Pos) obj;
            return this.row == that.row && this.col == that.col;
        }
    }

    private final int[][] tiles;
    private final Pos swapA;
    private final Pos swapB;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board94(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException();

        this.tiles = copyTiles(tiles);

        Pos swap1 = randomTail();
        Pos swap2;
        do {
            swap2 = randomTail();
        } while (swap1.equals(swap2));

        this.swapA = swap1;
        this.swapB = swap2;
//        // TODO fix zero
//        final int N = tiles.length;
//        int zeroRow = 0;
//        int zeroCol = 0;
//        ex:
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++)
//                if (tiles[i][j] == 0) {
//                    zeroRow = i;
//                    zeroCol = j;
//                    break ex;
//                }
//        this.zeroRow = zeroRow;
//        this.zeroCol = zeroCol;
    }

    private int[][] copyTiles(int[][] source) {
        int[][] result = new int[source.length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new int[source[i].length];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = source[i][j];
            }
        }
//        int i = 0;
//        for (int[] row: tiles)
//            result[i++] = row.clone();
        return result;
    }
    private int[][] copyTiles() {
        return copyTiles(this.tiles);
    }

    private String join(int[] arr, String sep) {
        if (arr.length == 0)
            return "";

        StringBuilder s = new StringBuilder();
        s.append(arr[0]);
        for (int i = 1; i < arr.length; i++)
            s.append(sep).append(arr[i]);
        return s.toString();
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(tiles.length).append("\n");
        for (int[] row: tiles)
            s.append(" ").append(join(row, "  ")).append("\n");
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int wrongPlace = 0;
        final int n = dimension();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1)
                    wrongPlace++;
        return wrongPlace;
    }

    private int manhattan(int row, int col) {
        int value = tiles[row][col] - 1;
        int valueRow = value / dimension();
        int valueCol = value % dimension();
        return Math.abs(row - valueRow) + Math.abs(col - valueCol);
    }
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        final int N = dimension();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (tiles[i][j] != 0)
                    dist += manhattan(i, j);
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal obj?
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

        Board94 that = (Board94) obj;
        final int N = that.dimension();
        if (that.dimension() != this.dimension())
            return false;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (that.tiles[i][j] != this.tiles[i][j])
                    return false;
        return true;
    }

    private void swap(int[][] tiles, Pos from, Pos to) {
        int value = tiles[from.row][from.col];
        tiles[from.row][from.col] = tiles[to.row][to.col];
        tiles[to.row][to.col] = value;
    }

    private Pos getZero(int[][] source) {
        int zeroRow = 0;
        int zeroCol = 0;

        ex:
        for (int i = 0; i < source.length; i++)
            for (int j = 0; j < source[i].length; j++)
                if (source[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break ex;
                }
        return new Pos(zeroRow, zeroCol);
    }

    private Pos getZero() {
        return getZero(tiles);
    }

    // all neighboring boards
    public Iterable<Board94> neighbors() {
        ArrayList<Board94> boards = new ArrayList<>();
        final int n = dimension();
        int[] dR = new int[] {-1, 1, 0, 0};
        int[] dC = new int[] {0, 0, -1, 1};
        // find Zero
        Pos zero = getZero();

        for (int i = 0; i < 4; i++) {
            int row = zero.row + dR[i];
            int col = zero.col + dC[i];
            if (row >= 0 && row < n && col >= 0 && col < n) {
                Board94 board = new Board94(this.tiles);
                swap(board.tiles, zero, new Pos(row, col));
                boards.add(board);
            }
        }
        return boards;
    }

    private Pos randomTail() {
        int row;
        int col;
        do {
            row = StdRandom.uniformInt(0, dimension());
            col = StdRandom.uniformInt(0, dimension());
        } while (tiles[row][col] == 0);
        return new Pos(row, col);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board94 twin() {
//        int[][] tiles = copyTiles();
//        int tv = tiles[row1][col1];
//        tiles[row1][col1] = tiles[row2][col2];
//        tiles[row2][col2] = tv;
        Board94 board = new Board94(tiles);
        board.swap(board.tiles, swapA, swapB);
        return board;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args.length > 0 ? args[0] : "puzzle4x4.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board94 initial = new Board94(tiles);
        System.out.println(initial);
        System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan: " + initial.manhattan());
        System.out.println("----------------");
        System.out.println("twin");
        System.out.println(initial.twin());
        System.out.println("----------------");
        System.out.println("neighbors");
        for (Board94 board: initial.neighbors())
            System.out.println(board);
        System.out.println("----------------");
        System.out.println("equals");
        System.out.println("self: " + initial.equals(initial));
        tiles = initial.copyTiles();
        Board94 copyInitials = new Board94((tiles));
        copyInitials.twin();
        copyInitials.neighbors();
        copyInitials.manhattan();
        copyInitials.toString();
        copyInitials.neighbors();
        copyInitials.neighbors();
        copyInitials.twin();
        copyInitials.twin();
        copyInitials.twin();
        copyInitials.twin();
        System.out.println("copy: " + initial.equals(copyInitials));
        System.out.println("null: " + initial.equals(null));
        System.out.println("string: " + initial.equals("string"));
        System.out.println("----------------");
    }
}