import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points

    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkNull(points);

        Point[] nps = new Point[points.length];
        copyArray(points, nps);
        Arrays.sort(nps);

        checkRepeat(nps);

        // начальные точки
        ArrayList<Point> fromPoint = new ArrayList<>();
        // конечные точки
        ArrayList<Point> toPoint = new ArrayList<>();

        // для каждой точки вычисляем наклон со всеми последующими
        // то, что получили запихиваем в массив и сортируем его.
        // далее идем по массиву и считаем одинаковые элементы (если находим 4 и более, то это наши точки)
        // проходимся по ним и отбираем мин и макс точку (хотя в общем из 2-х элементов достаточно) - это наш отрезок.
        // но надо избавиться от повторов точек (((
        for (int n = 0; n < nps.length - 1; n++) {
            Point p = nps[n];   // всегда минимальная точка
            Point[] rightPoints = new Point[nps.length - (n + 1)];
            System.arraycopy(nps, n + 1, rightPoints, 0, rightPoints.length);

            // последних точек может быть несколько
            ArrayList<Point> endPoints = findEndPoints(p, rightPoints);
            for (Point point: endPoints) {
                 if (checkEndPoint(point, p.slopeTo(point), fromPoint, toPoint)) {
                    fromPoint.add(p);
                    toPoint.add(point);
                    // segs.add(new LineSegment(p, point));
                }
            }
        }

        assert fromPoint.size() == toPoint.size();

        // segments = new LineSegment[toPoint.size()];
        for (int i = 0; i < fromPoint.size(); i++)
            segments.add(new LineSegment(fromPoint.get(i), toPoint.get(i)));
    }

    private boolean checkEndPoint(Point p, double slope, ArrayList<Point> fromPoint, ArrayList<Point> toPoint) {
        assert fromPoint.size() == toPoint.size();

        // очень долгое чтение. нельзя так (
        for (int i = 0; i < fromPoint.size(); i++) {
            Point from = fromPoint.get(i);
            Point to = toPoint.get(i);
            if ((p.compareTo(from) == 0 || p.compareTo(to) == 0) && (from.slopeTo(to) == slope))
                return false;
        }
        return true;
    }
    /**
     * Возвращает список конечных точек линий с большим, чем 3 сегмента, размером и исходящие из входной точки
     * @param p входная точка
     * @param points список точек
     * @return
     */
    private ArrayList<Point> findEndPoints(Point p, Point[] points) {
        ArrayList<Point> result = new ArrayList<>();

        Arrays.sort(points, p.slopeOrder());
        int i = 0;
        // надо найти ВСЕ конечные точки
        while (i < points.length) {
            // сдвигаем пока углы наклона различны
            // TODO или в этой точке такой угол наклона уже был - плохая идея (
            while (i < points.length - 1 &&
                    (p.slopeTo(points[i]) != p.slopeTo(points[i + 1])))
                i++;

            Point endPoint = null;
            i++;
            int k = 1;  // счетчик сегментов
            // сдвигаем пока углы совпадают
            while (i < points.length && p.slopeTo(points[i - 1]) == p.slopeTo(points[i])) {
                endPoint = points[i];
                i++;
                k++;
            }
            if (endPoint != null && k >= 3)
                result.add(endPoint);
        }

        return result;
    }

    /**
     * Проверка на null во входных данных.
     * @param points
     */
    private void checkNull(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point point: points)
            if (point == null)
                throw new IllegalArgumentException();
    }

    /**
     * Проверка повторов точек.
     * @param points - !сортированный! массив
     */
    private void checkRepeat(Point[] points) {
        for (int i = 1; i < points.length; i++)
            if (points[i - 1].compareTo(points[i]) == 0)
                throw new IllegalArgumentException();
    }

    private static <T> void copyArray(T[] source, T[] dest) {
        System.arraycopy(source, 0, dest, 0, source.length);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        // return segments.clone();
        LineSegment[] result = new LineSegment[segments.size()];
        segments.toArray(result);
        return result;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args.length > 0 ? args[0] : "inputOK.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 10);
        StdDraw.setYscale(-10, 10);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
