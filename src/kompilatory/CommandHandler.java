package kompilatory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class CommandHandler {
	public static String DATABASE = "db.json";

	private static Schema schema;
	public abstract String process(String line);

	public static void loadSchema() throws FileNotFoundException{
		Scanner in = new Scanner(new File(DATABASE));
		schema = new Schema();
		StringBuilder all = new StringBuilder();
		while(in.hasNextLine())
			all.append(in.nextLine());
		
//		System.out.println(all);
		parseToSchema(all);

		in.close();
	}

	private static void parseToSchema(StringBuilder all) {
		for(long i=0;i<all.length();i++){
			if('{'== all.charAt((int) i)){
				int end = findEnd(all.substring((int) i+1),'{', '}');
				parseTable(all.substring((int)i+1, (int)(end+i+1)));
				break;
			}
		}
	}

	private static void parseTable(String object) {
		System.out.println("TABLES "  + object);
		StringBuilder key = new StringBuilder();
		for(int i=0;i<object.length();i++){
			if('"' == object.charAt(i)){
				i++;
				key = new StringBuilder();
				while('"' != object.charAt(i)){
					key.append(object.charAt(i++));
				}
				while(':' != object.charAt(i)){
					i++;
				}
				while('[' != object.charAt(i)){
					i++;
				}
				int end = findEnd(object.substring(i+1), '[', ']');
				System.out.println("key " + key);
				List<Map<String,String> > array = parseArray(object.substring(i+1, (int)end+i));
				schema.addTable(key, array);
				i += end;
			}
		}
	}

	private static List<Map<String,String> > parseArray(String input) {
		System.out.println("RECORDS " + input);
		List<Map<String,String> > array= new ArrayList<Map<String,String> >();
		for(int i=0;i<input.length();i++){
			if('{'== input.charAt(i)){
				int end = findEnd(input.substring((int) i+1),'{', '}');				
				array.add( parseRecord(input.substring(i+1, end+i+1)));			
				i += end;
			}
		}
		
		return array;
	}

	private static Map<String,String> parseRecord(String input){
		System.out.println("RECORD " + input);
		Map<String,String> record = new HashMap<String, String>();
		StringBuilder key = new StringBuilder();
		StringBuilder value = new StringBuilder();
		for(int i=0;i<input.length();i++){
			if('"' == input.charAt(i)){
				key = new StringBuilder();
				value = new StringBuilder();
				i++;
				while('"' != input.charAt(i)){
					key.append(input.charAt(i));
					i++;
				}
				i++;
				while('"' != input.charAt(i))
					i++;
				i++;
				while('"' != input.charAt(i)){
					value.append(input.charAt(i));
					i++;
				}
				i++;		
			}
			record.put(key.toString(), value.toString());
		}
		return record;
	}
	private static int findEnd(String text, char left, char right) {
		int count = 1;
		for(int i=0;i<text.length();i++){
			if(left == text.charAt(i))
				count++;
			else if(right == text.charAt(i))
				count--;
			if(count == 0)
				return i;
		}
		return -1;
	}
}
