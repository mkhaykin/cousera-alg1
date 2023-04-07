import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    // construct an empty set of points
    private SET<Point2D> pointSet;
    public PointSET() {
        pointSet = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)  {
        if (p == null)
            throw new IllegalArgumentException();

        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D point: pointSet)
            if (rect.contains(point))
                points.add(point);
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;

        Point2D nearPoint = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Point2D point: pointSet) {
            double dist = point.distanceSquaredTo(p);
            if (dist < minDist) {
                nearPoint = point;
                minDist = dist;
            }
        }
        return nearPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}