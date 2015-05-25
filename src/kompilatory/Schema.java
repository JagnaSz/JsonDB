package kompilatory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {
	private Map<String, List<Map<String, String> > > schema;
	
	public Schema(){
		schema = new HashMap<String, List<Map<String,String>>>();
	}

	public Map<String, List<Map<String, String>>> getSchema() {
		return schema;
	}

	public void setSchema(Map<String, List<Map<String, String>>> schema) {
		this.schema = schema;
	}

	@Override
	public String toString() {
//		TODO:
		return "Schema [schema=" + schema + "]";
	}

	public void addTable(StringBuilder key, List<Map<String, String>> array) {
		schema.put(key.toString(), array);
	}	
	
}
