package kompilatory;


public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser();
//        parser.readLine();
        System.out.println(parser.getCommandHandler().getSchema());
    }
}
