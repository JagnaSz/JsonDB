package kompilatory;

import java.io.FileNotFoundException;
import java.io.FileReader;

import kompilatory.parser.JSONParser;


public class TestJSON {

	public static void main(String[] args) {
		JSONScanner jsonScanner;
		try {
			jsonScanner = new JSONScanner(new FileReader("db2.json"));
			JSONParser jsonParser = new JSONParser(jsonScanner);
			String scan = "";
			do{
				scan = jsonParser.scan().toString();
				System.out.println(scan);
			} while (!scan.equals("#0"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
