import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    // private final Point[] points;
    private final ArrayList<LineSegment> segs = new ArrayList<>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point point: points)
            if (point == null)
                throw new IllegalArgumentException();

        Point[] nps = new Point[points.length];
        copyArray(points, nps);
        points = nps;

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++)
            if (points[i - 1].compareTo(points[i]) == 0)
                throw new IllegalArgumentException();

        // this.points = points;
        Point point1, point2, point3, point4;
        double slope12, slope23, slope34;
        for (int i = 0; i < points.length; i++) {
            point1 = points[i];
            for (int j = i + 1; j < points.length; j++) {
                point2 = points[j];
                slope12 = point1.slopeTo(point2);
                for (int k = j + 1; k < points.length; k++) {
                    point3 = points[k];
                    slope23 = point2.slopeTo(point3);
                    for (int n = k + 1; n < points.length; n++) {
                        point4 = points[n];
                        slope34 = point3.slopeTo(point4);
                        if (slope12 == slope23 && slope23 == slope34) {
                            Point pointStart = point1;
                            Point pointEnd = point1;
                            if (pointStart.compareTo(point2) > 0)
                                pointStart = point2;
                            if (pointStart.compareTo(point3) > 0)
                                pointStart = point3;
                            if (pointStart.compareTo(point4) > 0)
                                pointStart = point4;
                            if (pointEnd.compareTo(point2) < 0)
                                pointEnd = point2;
                            if (pointEnd.compareTo(point3) < 0)
                                pointEnd = point3;
                            if (pointEnd.compareTo(point4) < 0)
                                pointEnd = point4;
                            segs.add(new LineSegment(pointStart, pointEnd));
                        }
                    }
                }
            }
        }
    }

    private static <T> void copyArray(T[] source, T[] dest) {
        System.arraycopy(source, 0, dest, 0, source.length);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[segs.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = segs.get(i);
        return res;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args.length > 0 ? args[0] : "input6.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}