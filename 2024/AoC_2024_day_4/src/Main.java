
public class Main {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Puzzle input must be passed to this program.");
            System.exit(1);
        }

        WordsearchSolver solver = new WordsearchSolver(args[0]);
    }
}