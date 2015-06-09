package kompilatory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kompilatory.exception.SyntaxError;

public class InsertHandler extends CommandHandler {

	@Override
	public String process(String line) throws SyntaxError, SQLException {
		Scanner in = new Scanner(line);
		String input;
		String table;
		String result = "nothing happend";
		List<String> columns = null;
		List<String> values = null;
		try{
			input = in.next();
			if(!input.toLowerCase().equals("insert"))
				throw new SyntaxError(input);
			input = in.next();
			if(!input.toLowerCase().equals("into"))
				throw new SyntaxError(input);
			table = in.next();
			
			StringBuilder rest = new StringBuilder();
			while(in.hasNext())
				rest.append(in.next());
			
			for(int i=0;i<rest.length();i++){
				if('(' == rest.charAt(i)){
					int end = findEnd(rest.substring(i+1), '(', ')');
					if (end == -1)
						throw new SyntaxError(rest.substring(i+1));
					columns = createList(rest.substring(i+1, i+end+1));
					i += end;
				}
				if('v' == rest.charAt(i) || 'V' == rest.charAt(i)){
					StringBuilder value = new StringBuilder();
					value.append(rest.charAt(i));
					while(rest.charAt(i) != 's' && rest.charAt(i) != 'S'){
						value.append(rest.charAt(i));
						i++;
					}
					while(rest.charAt(i) != '(')
						i++;
					int end = findEnd(rest.substring(i+1), '(', ')');
					if (end == -1)
						throw new SyntaxError(rest.substring(i+1));
					
					values = createList(rest.substring(i+1,i+end+1));
					i = rest.length();
				}			
			}		
			if(columns!=null)
				if(values.size() != columns.size())
					throw new SyntaxError("values doesn't fit to columns");
			if(values == null)
				throw new SyntaxError("values can't be empty");
			if(schema.insert(table, columns, values))
				result = "One item inserted in table " + table;
		} catch (SyntaxError e){
			throw e;
		} catch (SQLException e) {
			throw e;
		}
		finally{
			in.close();
		}
		return result;
	}

	/**
	 * create string list from string chain in format "string","string"...
	 * @param input
	 * @return
	 */
	private List<String> createList(String input) {
		List<String> result = new ArrayList<String>();
//		for(int i=0;i<input.length();i++){
//			StringBuilder word = new StringBuilder();
//			if(input.charAt(i) == '"'){
//				i++;
//				word.append(input.charAt(i));	
//				i++;
//				while(input.charAt(i) != '"'){
//					word.append(input.charAt(i));
//					i++;
//				}
//				result.add(word.toString());
//				i++;	
//			}
//		}
		String [] splited = input.split(",");
		for(int i=0;i<splited.length;i++)
			result.add(splited[i].replace("\"", ""));
		return result;
	}

}
