import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] result;
    private final double mean;
    private final static double C1_96 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        double sum = 0;
        result = new double[trials];
        for (int i = 0; i < trials; i++) {
            result[i] = 1.0 * oneGame(n) / (n * n);
            sum += result[i];
        }
        mean = sum / result.length;
    }

    private int oneGame(int size) {
        Percolation p = new Percolation(size);
        while (!p.percolates()) {
            int iR = StdRandom.uniformInt(1, size + 1);
            int iC = StdRandom.uniformInt(1, size + 1);
            if (!p.isOpen(iR, iC))
                p.open(iR, iC);
        }
        return p.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double x = mean();
        double s = stddev();
        return x - C1_96 * s / Math.sqrt(result.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double x = mean();
        double s = stddev();
        return x + C1_96 * s / Math.sqrt(result.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = args.length == 2 ? Integer.parseInt(args[0]) : 200;
        int t = args.length == 2 ? Integer.parseInt(args[1]) : 100;

        PercolationStats ps = new PercolationStats(n, t);
        final int LEN = "95% confidence interval".length();
        StdOut.printf("%-" + LEN + "s = %f%n", "mean", ps.mean());
        StdOut.printf("%-" + LEN + "s = %f%n", "stddev", ps.stddev());
        StdOut.printf("%-" + LEN + "s = [%f, %f]%n", 95 + "% confidence interval",
                ps.confidenceLo(), ps.confidenceHi());
    }
}