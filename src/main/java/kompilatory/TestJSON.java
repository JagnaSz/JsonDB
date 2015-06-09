package kompilatory;

import java.io.FileNotFoundException;
import java.io.FileReader;

import kompilatory.parser.JSONObject;
import kompilatory.parser.JSONParser;


public class TestJSON {

	public static void main(String[] args) {
		JSONScanner jsonScanner;
		try {
			jsonScanner = new JSONScanner(new FileReader("db.json"));
			JSONParser jsonParser = new JSONParser(jsonScanner);
			JSONObject jsonObject = (JSONObject) jsonParser.parse().value;
		
			
			System.out.println(jsonObject.toString());
//			System.out.println(jsonParser.debug_parse().toString());
//			
//			String scan = "";
//			Symbol sym = null;
//			do{
//				sym = jsonParser.scan();
////			toString();
//				if(sym.value != null)
//					scan = sym.value.toString();
//				else
//					scan = Integer.toString(sym.sym);
//				System.out.println(scan);
//			} while (!sym.toString().equals("#0"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
