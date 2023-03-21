import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
    public static void main(String[] args) {
        String nameOne = args[0];
        String nameTwo = args[1];
        StdOut.println("Hello " + nameOne + " and " + nameTwo + ".");
        StdOut.println("Goodbye " + nameTwo + " and " + nameOne + ".");
    }
}
