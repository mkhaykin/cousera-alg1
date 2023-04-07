import edu.princeton.cs.algs4.StdOut;
public class LineSegment {
    // constructs the line segment between points p and q
    private final Point p;
    private final Point q;
    private final double slope;
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        }

        this.p = p.compareTo(q) < 0 ? p : q;
        this.q = p.compareTo(q) > 0 ? p : q;
        slope = p.slopeTo(q);
    }

    // draws this line segment
    public void draw() {
        p.drawTo(q);
    }

    public Point getP() {
        return p;
    }

    public Point getQ() {
        return q;
    }

    public double getSlope() {
        return slope;
    }

    // string representation
    public String toString() {
        return p.toString() + " -> " + q.toString();
    }

    public static void main(String[] args) {
        Point p = new Point(0, 0);
        Point q = new Point(1, 1);
        LineSegment l1 = new LineSegment(p, q);
        StdOut.println(l1);
        LineSegment l2 = new LineSegment(q, p);
        StdOut.println(l2);
    }
}