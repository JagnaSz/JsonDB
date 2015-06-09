package kompilatory.parser;

public class JSONPair {
	private String key;
	private JSONValue value;
	
	public JSONPair(String key, JSONValue value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JSONValue getValue() {
		return value;
	}

	public void setValue(JSONValue value) {
		this.value = value;
	}

}
