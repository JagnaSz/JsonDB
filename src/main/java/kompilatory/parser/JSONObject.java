package kompilatory.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONObject extends HashMap<String, JSONValue> implements Map<String, JSONValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3241243437359365222L;

	public JSONObject() {
		super();
	}

	/**
	 * Allows creation of a JSONObject from a Map. After that, both the
	 * generated JSONObject and the Map can be modified independently.
	 * 
	 * @param map
	 */
	public JSONObject(Map<String, JSONValue> map) {
		super(map);
	}

	public JSONObject(List<JSONPair> pairs) {
		for(JSONPair pair : pairs){
			this.put(pair.getKey(), pair.getValue());
		}
	}

	public static String toJSONString(Map map){
		StringBuilder sb = new StringBuilder();
		if(map == null){
			sb.append("null");
			return sb.toString();
		}

		boolean first = true;
		Iterator iter=map.entrySet().iterator();

		sb.append("{");
		while(iter.hasNext()){
			if(first)
				first = false;
			else
				sb.append(",");
			Map.Entry entry=(Map.Entry)iter.next();
//			sb.append("\"");
			sb.append(String.valueOf(entry.getKey()));
//			sb.append("\"");
			sb.append(":");
			sb.append(JSONValue.toJSONString(entry.getValue()));
		}
		sb.append("}");

		return sb.toString();
	}

	public String toJSONString(){
		return toJSONString(this);
	}

	public String toString(){
		return toJSONString();
	}

	public static String toString(String key,Object value){
		StringBuffer sb = new StringBuffer();
		sb.append('\"');
		if(key == null)
			return "null";
		sb.append('\"').append(':');

		sb.append(JSONValue.toJSONString(value));

		return sb.toString();
	}
}
