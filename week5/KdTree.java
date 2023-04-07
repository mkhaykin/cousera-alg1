import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private int size;
    // construct an empty set of points
    private Node root;

    private class Node {
        private Node left;
        private Node right;
        private final Point2D point;
        private final RectHV area;
        // private final int level;
        public Node(Point2D point, RectHV area) {
            if (point == null)
                throw new IllegalArgumentException();

            this.point = point;
            this.area = area;
        }
    }
    public KdTree() {
        size = 0;
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            size = 1;
            return;
        }

        int level = 0;
        Node node = root;

        while (true) {
            if (node.point == p || p.equals(node.point))
                return;

            if ((level % 2 == 0 && p.x() < node.point.x()) || (level % 2 != 0 && p.y() < node.point.y())) {
                if (node.left == null) {
                    node.left = new Node(p, nodeAreaHalf(node, level, true));
                    break;
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new Node(p, nodeAreaHalf(node, level, false));
                    break;
                }
                node = node.right;
            }
            level++;
        }
        size++;
    }

    private RectHV nodeAreaHalf(Node node, int level, boolean left) {
        double xMin = node.area.xmin();
        double yMin = node.area.ymin();
        double xMax = node.area.xmax();
        double yMax = node.area.ymax();
        if (level % 2 == 0)
            if (left)
                xMax = node.point.x();
            else
                xMin = node.point.x();
        else
            if (left)
                yMax = node.point.y();
            else
                yMin = node.point.y();
        return new RectHV(xMin, yMin, xMax, yMax);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)  {
        if (p == null)
            throw new IllegalArgumentException();

        int level = 0;
        Node node = root;
        while (node != null) {
            if (p.equals(node.point))
                return true;

            if ((level % 2 == 0 && p.x() < node.point.x()) ||
                    (level % 2 != 0 && p.y() < node.point.y()))
                node = node.left;
            else
                node = node.right;
            level++;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        drawNode(root, 0, "");
    }

    private void drawNode(Node node, int level, String mark) {
        if (node == null)
            return;

        // draw point
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());
        StdDraw.text(node.point.x(), node.point.y() + 0.025, Integer.toString(level) + mark);

        // draw rectangle
        StdDraw.setPenRadius();
        StdDraw.rectangle((node.area.xmax() + node.area.xmin()) / 2, (node.area.ymax() + node.area.ymin()) / 2,
                (node.area.xmax() - node.area.xmin() - 0.01) / 2,
                (node.area.ymax() - node.area.ymin() - 0.01) / 2);

        // draw middle line
        if (level % 2 == 0)
            StdDraw.line(node.point.x(), 0, node.point.x(), 1);
        else
            StdDraw.line(0, node.point.y(), 1, node.point.y());

        level++;

        drawNode(node.left, level, "L");
        drawNode(node.right, level, "R");
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        ArrayList<Point2D> list = new ArrayList<>();

        if (!isEmpty())
            range(rect, root, list);

        return list;
    }

    private void range(RectHV rect, Node node, ArrayList<Point2D> list) {
        if (node == null)
            return;

        Point2D point = node.point;

        if (rect.contains(point))
            list.add(point);

        if (node.left != null && node.left.area.intersects(rect))
            range(rect, node.left, list);
        if (node.right != null && node.right.area.intersects(rect))
            range(rect, node.right, list);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;

        return nearest(p, root, root.point);
    }

    private Point2D nearest(Point2D p, Node node, Point2D minPoint) {
        if (node == null)
            return minPoint;

        double minDist = minPoint.distanceSquaredTo(p);

        if (minDist <= node.area.distanceSquaredTo(p))
            return minPoint;

        if (p.distanceSquaredTo(node.point) < minDist)
            minPoint = node.point;

        if (node.right == null)
            minPoint = nearest(p, node.left, minPoint);
        else if (node.left == null) {
            minPoint = nearest(p, node.right, minPoint);
        } else {
            Node firstCheck = node.right;
            if (node.left.area.contains(p) ||
                    node.right.area.distanceSquaredTo(p) > node.left.area.distanceSquaredTo(p))
                firstCheck = node.left;

            minPoint = nearest(p, firstCheck, minPoint);
            minPoint = nearest(p, firstCheck == node.left ? node.right : node.left, minPoint);
        }

        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}