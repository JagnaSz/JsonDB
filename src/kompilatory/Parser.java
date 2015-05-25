package kompilatory;

import java.util.Scanner;

public class Parser {

	public void readLine() {
		Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println(line);
        
        scanner.close();
	}

}
