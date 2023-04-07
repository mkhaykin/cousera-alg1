import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        if (that == null)
            throw new NullPointerException();

        StdDraw.line(x, y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null)
            throw new NullPointerException();

        if (this.x == that.x && this.y == that.y)
            return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        else
            return 1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null)
            throw new NullPointerException();

        if (that.x == this.x && that.y == this.y)
            return Double.NEGATIVE_INFINITY;
        else if (that.x == this.x)
            return Double.POSITIVE_INFINITY;
        else if (that.y == this.y)
            return 0.0;
        else
            return 1.0 * (that.y - this.y) / (that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new MyComparator(this);
    }

    private static class MyComparator implements Comparator<Point> {
        Point point;
        public MyComparator(Point point) {
            this.point = point;
        }
        // TODO !!!
        @Override
        public int compare(Point o1, Point o2) {
            // return Double.compare(o1.slopeTo(new Point(0, 0)), o2.slopeTo(new Point(0, 0)));
            return Double.compare(point.slopeTo(o1), point.slopeTo(o2));
        }
    }
    public static void main(String[] args) {
        Point p = new Point(65, 204);
        Point q = new Point(300, 136);
        Point r = new Point(395, 175);
        System.out.println(p.slopeOrder().compare(q, r));
    }
}