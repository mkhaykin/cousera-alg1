// Одно объединение оценка 98%. Проблема в isFull для ячеек, не объединенных с верхней границей,
// но заполненной через фиктивную нижнюю. решается использованием двух объединений.
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {

    private final boolean[] field;
    private int countOpen;
    private final WeightedQuickUnionUF unionUF;
    private final int n;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation2(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.n = n + 2;
        final int size = this.n * this.n;

        field = new boolean[size];
        countOpen = 0;
        unionUF = new WeightedQuickUnionUF(size);

        int idxFirst = 0;
        int idxLast = size - 1;
        field[idxFirst] = true;
        field[idxLast] = true;
        for (int i = 1; i < this.n; i++) {
            unionUF.union(0, ++idxFirst);
            unionUF.union(idxLast, --idxLast);
            field[idxFirst] = true;
            field[idxLast] = true;
        }
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n - 2 || col < 1 || col > n - 2) {
            throw new IllegalArgumentException("index (" + row + ", " + col + ") is not between 1 and " + n);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        int idx = row * n + col;
        if (!field[idx]) {
            field[idx] = true;
            countOpen++;
            int[] delta = new int[] {-1, 1, -this.n, this.n};
            for (int i = 0; i < delta.length; i++) {
                if (field[idx + delta[i]])
                    unionUF.union(idx, idx + delta[i]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return field[row * n + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && unionUF.find(0) == unionUF.find(row * n + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionUF.find(0) == unionUF.find(n * n - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 2;
        Percolation2 p = new Percolation2(n);
        int count = 0;
        while (!p.percolates()) {
            int iR = StdRandom.uniformInt(1, n + 1);
            int iC = StdRandom.uniformInt(1, n + 1);
            if (!p.isOpen(iR, iC)) {
                p.open(iR, iC);
                count++;
            }
        }
        StdOut.println(1.0 * count / n / n);
    }
}