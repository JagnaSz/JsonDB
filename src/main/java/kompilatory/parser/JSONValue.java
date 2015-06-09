package kompilatory.parser;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class JSONValue {
	
	private Object obj;

	public Object getValue() {
		return obj;
	}

	public void setValue(Object value) {
		this.obj = value;
	}

	public JSONValue(JSONObject o) {
		this.obj = o;
	}

	public JSONValue(JSONArray a) {
		this.obj = a;
	}
//	public static Object parse(Reader in){
//		try{
//			JSONParser parser=new JSONParser();
//			return parser.parse(in);
//		}
//		catch(Exception e){
//			return null;
//		}
//	}
//	
//	public static Object parse(String s){
//		StringReader in=new StringReader(s);
//		return parse(in);
//	}
	
//	public static Object parseWithException(Reader in) throws IOException, ParseException{
//		JSONParser parser=new JSONParser();
//		return parser.parse(in);
//	}
//	
//	public static Object parseWithException(String s) throws ParseException{
//		JSONParser parser=new JSONParser();
//		return parser.parse(s);
//	}

	public String toJSONString(){
		String out = "";
		if(obj == null){
			out = "null";
		}
		
		if(obj instanceof String){		
            out = "\"" + (String) obj + "\"";
		}
		
		if(obj instanceof Double){
			if(((Double)obj).isInfinite() || ((Double)obj).isNaN())
				out = "null";
			else
				out = ((Double)obj).toString();
		}
		
		if(obj instanceof Boolean){
			out = ((Boolean)obj).toString();
		}
		
		if(obj instanceof Map){
			out = JSONObject.toJSONString((Map)obj);
		}
		
		if(obj instanceof Collection){
			out = JSONArray.toJSONString((Collection)obj);
		}		
		return out;
	}
	

	public JSONValue() {
		obj = null;
	}

	public JSONValue(Boolean valueOf) {
		obj = valueOf;
	}

	public JSONValue(String w) {
		obj = w;
	}

	public JSONValue(Integer valueOf) {
		obj = valueOf;
	}

	public JSONValue(Double valueOf) {
		obj = valueOf;
	}

	public static String toJSONString(Object value) {
		String out = "";
		if(((JSONValue)value).getValue() == null){
			out = "null";
		}
		
		if(((JSONValue)value).getValue()  instanceof String){		
            out = (String) ((JSONValue)value).getValue();
		}
		
		if(((JSONValue)value).getValue()  instanceof Integer){		
            out = ((Integer) ((JSONValue)value).getValue()).toString();
		}
		
		if(((JSONValue)value).getValue()  instanceof Double){
			if(((Double)((JSONValue)value).getValue() ).isInfinite() || ((Double)((JSONValue)value).getValue() ).isNaN())
				out = "null";
			else
				out = ((Double)((JSONValue)value).getValue() ).toString();
		}
		
		if(((JSONValue)value).getValue()  instanceof Boolean){
			out = ((Boolean)((JSONValue)value).getValue() ).toString();
		}
		
		if(((JSONValue)value).getValue()  instanceof Map){
			out = JSONObject.toJSONString((Map)((JSONValue)value).getValue() );
		}
		
		if(((JSONValue)value).getValue()  instanceof Collection){
			out = JSONArray.toJSONString((Collection)((JSONValue)value).getValue() );
		}		
		return out;
	}

}
