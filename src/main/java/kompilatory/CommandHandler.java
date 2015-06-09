package kompilatory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kompilatory.exception.SyntaxError;
import kompilatory.parser.JSONArray;
import kompilatory.parser.JSONObject;
import kompilatory.parser.JSONParser;

public abstract class CommandHandler {
	public static String DATABASE = "db2.json";
	//public static String DATABASE = "C://Users//piotr//Desktop//db.json";

	protected static Schema schema;
	
	public static Schema getSchema() {
		return schema;
	}

	public static void setSchema(Schema schema) {
		CommandHandler.schema = schema;
	}

	public abstract String process(String line) throws SyntaxError, SQLException;

	public static void loadSchema() throws FileNotFoundException{
//		Scanner in = new Scanner(new File(DATABASE));
		schema = new Schema();
//		StringBuilder all = new StringBuilder();
//		while(in.hasNextLine())
//			all.append(in.nextLine());
		JSONScanner jsonScanner = new JSONScanner(new FileReader(DATABASE));
		JSONParser jsonParser = new JSONParser(jsonScanner);
		JSONObject db;
		try {
			db = (JSONObject) jsonParser.parse().value;
			parseToSchema(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(all);
		

//		in.close();
	}
	
	private static void parseToSchema(JSONObject db) {
		Map<String, List<Map<String, String>>> schema = new HashMap<String, List<Map<String, String>>>();
		for(String keyTable : db.keySet()){
			List<Map<String,String>> records = new LinkedList<Map<String,String>>();
			JSONArray array = (JSONArray) db.get(keyTable).getValue();
			for(int i=0;i<array.size();i++){
				JSONObject jsonRecord = (JSONObject) array.get(i).getValue();
				Map<String, String> record = new HashMap<String, String>();
				for(String keyRecord : jsonRecord.keySet()){
					if(jsonRecord.get(keyRecord).getValue() == null)
						record.put(keyRecord, null);
					record.put(keyRecord, jsonRecord.get(keyRecord).getValue().toString());
				}
				records.add(record);
			}
			schema.put(keyTable, records);	
		}
		CommandHandler.schema.setSchema(schema);
	}

	public static void saveSchema() throws FileNotFoundException{
		PrintWriter output = new PrintWriter(DATABASE);
		output.write(schema.toString());
		output.close();		
	}

//	private static void parseToSchema(StringBuilder all) {
//		for(long i=0;i<all.length();i++){
//			if('{'== all.charAt((int) i)){
//				int end = findEnd(all.substring((int) i+1),'{', '}');
//				parseTable(all.substring((int)i+1, (int)(end+i+1)));
//				break;
//			}
//		}
//	}

	private static void parseTable(String object) {
//		System.out.println("TABLES "  + object);
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
				List<Map<String,String> > array = parseArray(object.substring(i+1, (int)end+i));
				schema.addTable(key, array);
				i += end;
			}
		}
	}

	private static List<Map<String,String> > parseArray(String input) {
//		System.out.println("RECORDS " + input);
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
	//	System.out.println("RECORD " + input);
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
	protected static int findEnd(String text, char left, char right) {
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
