import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTreeTest {
    private static void stepOne() {
        KdTree kt = new KdTree();
        kt.insert(new Point2D(0.7, 0.2));
        System.out.println(kt.size());
        System.out.println(kt.isEmpty());
        kt.insert(new Point2D(0.5, 0.4));
        System.out.println(kt.size());
        System.out.println(kt.isEmpty());
        kt.insert(new Point2D(0.2, 0.3));
        kt.insert(new Point2D(0.4, 0.7));
        kt.insert(new Point2D(0.9, 0.6));

        System.out.println(kt.contains(new Point2D(0.9, 0.6)));
        System.out.println(kt.contains(new Point2D(0.1, 0.1)));

        System.out.println("OK");

        for (Point2D point: kt.range(new RectHV(0.0, 0.39, 0.13, 0.93)))
            System.out.println(point.toString());

        RectHV rect = new RectHV(0.15625, 0.0625, 0.4375, 0.5625);
        System.out.println("Point in rect " + rect.toString());
        for (Point2D point: kt.range(rect))
            System.out.println(point.toString());
        System.out.println("------------");

        Point2D point = new Point2D(0.75, 0.48);
        Point2D np = kt.nearest(point);
        System.out.println("nearest point: " + np.toString());
        System.out.println("------------");

        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        rect.draw();
        StdDraw.setPenColor(StdDraw.BLACK);
        kt.draw();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        point.draw();
        StdDraw.show();
    }

    private static void stepTwo() {
        KdTree kt = new KdTree();
//        A  0.75 0.15625
//        B  0.84375 0.90625
//        C  0.9375 0.40625
//        D  0.03125 0.84375
//        E  0.875 0.75
//        F  0.40625 0.21875
//        G  0.28125 0.0
//        H  0.3125 0.3125
//        I  0.46875 0.71875
//        J  0.59375 0.03125
//        K  1.0 0.9375
//        L  0.625 1.0
//        M  0.34375 0.65625
//        N  0.0 0.59375
//        O  0.96875 0.1875
//        P  0.8125 0.875
//        Q  0.09375 0.53125
//        R  0.21875 0.34375
//        S  0.1875 0.375
//        T  0.375 0.46875
        kt.insert(new Point2D(0.75, 0.15625));
        kt.insert(new Point2D(0.84375, 0.90625));
        kt.insert(new Point2D(0.9375, 0.40625));
        kt.insert(new Point2D(0.03125, 0.84375));
        kt.insert(new Point2D(0.875, 0.75));
        kt.insert(new Point2D(0.40625, 0.21875));
        kt.insert(new Point2D(0.28125, 0.));
        kt.insert(new Point2D(0.3125, 0.3125));
        kt.insert(new Point2D(0.46875, 0.71875));

        // RectHV rect = new RectHV(0.0625, 0.4375, 0.15625, 0.5625);
        RectHV rect = new RectHV(0.15625, 0.0625, 0.4375, 0.5625);

        System.out.println("Point in rect " + rect.toString());
        for (Point2D point: kt.range(rect))
            System.out.println(point.toString());
        System.out.println("------------");

        Point2D point = new Point2D(0.45, 0.77);
        Point2D np = kt.nearest(point);
        System.out.println("nearest point: " + np.toString());
        System.out.println("------------");

        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        rect.draw();
        StdDraw.setPenColor(StdDraw.BLACK);
        kt.draw();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        point.draw();
        StdDraw.show();
    }

    private static void stepThree() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();

        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }

    }
    public static void main(String[] args) {
//        stepOne();
//        stepTwo();
        stepThree();
    }
}
