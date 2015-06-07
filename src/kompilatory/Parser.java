package kompilatory;

import java.io.FileNotFoundException;
import java.util.Scanner;

import kompilatory.exception.SyntaxError;

public class Parser {

	private CommandHandler commandHandler = null;

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

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
		String line = "";
		while(!line.equals("q")){
			System.out.print("> ");
			line = scanner.nextLine();
			if("q".equals(line))
				continue;

			if(!line.isEmpty())
				System.out.println(processCommand(line));

		}
		System.out.println("SQL shell terminated");
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

		try{
			switch(words[0].toLowerCase()){
			case "select":
				commandHandler = new SelectHandler();
				break;
			case "insert":
				commandHandler = new InsertHandler();
			case "update":
				commandHandler = new UpdateHandler();
				break;
			case "delete" :
				//TODO
				break;
			case "create" :
				commandHandler = new CreateHandler();
				break;
			case "drop" :
				commandHandler = new DropHandler();
				break;
			default:
				throw new SyntaxError("Method doesn't exist: " + words[0]);
			}

			result = commandHandler.process(line);
			CommandHandler.saveSchema();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
