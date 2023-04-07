import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;


public class Board {
    private final int[] tiles;
    private final int swapA;
    private final int swapB;
    private final int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException();

        this.size = tiles.length;
        this.tiles = new int[size * size];

        int idx = 0;
        for (int[] row: tiles)
            for (int value: row)
                this.tiles[idx++] = value;

        int idxA;
        do {
            idxA = StdRandom.uniformInt(0, this.tiles.length);
        } while (this.tiles[idxA] == 0);

        int idxB;
        do {
            idxB = StdRandom.uniformInt(0, this.tiles.length);
        } while (this.tiles[idxB] == 0 || idxB == idxA);

        this.swapA = idxA;
        this.swapB = idxB;
    }

    private int[][] copyTiles2D() {
        return copyTiles2D(tiles);
    }
    private int[][] copyTiles2D(int[] source) {
        int[][] result = new int[size][size];
        int idx = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result[i][j] = source[idx++];
        return result;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size).append("\n");
        for (int i = 1; i <= tiles.length; i++) {
            s.append(tiles[i - 1]);
            s.append(i % size == 0 ? "\n" : " ");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int wrongPlace = 0;
        for (int i = 0; i < tiles.length; i++)
            if (tiles[i] != 0 && tiles[i] != i + 1)
                wrongPlace++;
        return wrongPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != 0 && tiles[i] != i + 1) {
                int deltaR = Math.abs((tiles[i] - 1) / size - (i) / size);
                int deltaC = Math.abs((tiles[i] - 1) % size - (i) % size);
                result += deltaR + deltaC;
            }
        }
        return result;
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

        Board that = (Board) obj;
        if (that.tiles.length != this.tiles.length)
            return false;

        for (int i = 0; i < this.tiles.length; i++)
            if (this.tiles[i] != that.tiles[i])
                return false;

        return true;
    }

    private void swap(int[] tiles, int from, int to) {
        int value = tiles[from];
        tiles[from] = tiles[to];
        tiles[to] = value;
    }

    private void swap(int[][] tiles, int fromRow, int fromCol, int toRow, int toCol) {
        int value = tiles[fromRow][fromCol];
        tiles[fromRow][fromCol] = tiles[toRow][toCol];
        tiles[toRow][toCol] = value;
    }

    private int getZero(int[] source) {
        for (int i = 0; i < source.length; i++)
            if (source[i] == 0)
                return i;
        return -1;
    }

    private int getZero() {
        return getZero(tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<>();
        int[] dR = new int[] {-1, 1, 0, 0};
        int[] dC = new int[] {0, 0, -1, 1};
        // find Zero
        int idxZero = getZero();
        int zeroR = idxZero / size;
        int zeroC = idxZero % size;

        int[][] tiles2D = copyTiles2D();
        for (int i = 0; i < 4; i++) {
            int row = zeroR + dR[i];
            int col = zeroC + dC[i];
            if (row >= 0 && row < size && col >= 0 && col < size) {
                swap(tiles2D, row, col, zeroR, zeroC);
                Board board = new Board(tiles2D);
                // второй обмен чтобы не пересоздавать массивы
                swap(tiles2D, row, col, zeroR, zeroC);
                boards.add(board);
            }
        }
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        swap(tiles, swapA, swapB);
        Board board = new Board(copyTiles2D());
        swap(tiles, swapA, swapB);
        return board;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args.length > 0 ? args[0] : "puzzle04.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan: " + initial.manhattan());
        System.out.println("----------------");
        System.out.println("twin");
        System.out.println(initial.twin());
        System.out.println("----------------");
        System.out.println("neighbors");
        for (Board board: initial.neighbors())
            System.out.println(board);
        System.out.println("----------------");
    }
}