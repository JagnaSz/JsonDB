package kompilatory;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	
	private CommandHandler commandHandler = null;

	public Parser(){
		try {
			CommandHandler.loadSchema();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readLine() {
		Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println(line);
        processCommand(line);
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
