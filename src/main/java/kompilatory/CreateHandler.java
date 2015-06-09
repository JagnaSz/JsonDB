package kompilatory;

import java.sql.SQLException;
import java.util.Scanner;

import kompilatory.exception.SyntaxError;

public class CreateHandler extends CommandHandler {

	@Override
	public String process(String line) throws SyntaxError, SQLException {
		Scanner in = new Scanner(line);
		String result = "nothing happend";
		try{
			String create = in.next();
			if(!create.toLowerCase().equals("create"))
				throw new SyntaxError(create);
			String table = in.next();
			if(!table.toLowerCase().equals("table"))
				throw new SyntaxError(table);
			
			String tableName = in.next();
			// TODO walidacja nazwy tabeli
			
			StringBuilder rest = new StringBuilder();
			while(in.hasNext())
				rest.append(in.next());
			
			for(int i=0;i<rest.length();i++){
				if('(' == rest.charAt(i)){
					int end = findEnd(rest.substring(i+1), '(', ')');
					String [] columnes = rest.substring(i+1, i+end+1).split(",");
					if(schema.createTable(tableName, columnes))
						result = "Table " + tableName + " created";

					i += end;
				}
			}
		}
		catch(SyntaxError e){
			throw e;
		}
		finally{
			in.close();
		}
		
		return result;
	}

}
