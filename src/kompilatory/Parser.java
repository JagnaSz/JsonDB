package kompilatory;

import java.util.Scanner;

public class Parser {

	public void readLine() {
		Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println(line);
        
        scanner.close();
	}
	
	/**
	 * process single command
	 * @param line
	 * @return
	 */
	public String processCommand(String line){
		String result = null;
		String [] words = line.split(" ");
		CommandHandler commandHandler = null;

		switch(words[0].toLowerCase()){
			case "select":
				
				break;
			case "insert":
				commandHandler = new InsertHandler();
			case "update":

				break;
			case "delete" :
				break;
			case "create" :
				break;
			case "drop" :
				break;
		}
		try{
			result = commandHandler.process(line);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
