package kompilatory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {
	private Map<String, List<Map<String, String>>> schema;

	public Schema() {
		schema = new HashMap<String, List<Map<String, String>>>();
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
		StringBuilder builder = new StringBuilder();
		builder.append("[\r\n");
		for (Map.Entry map : schema.entrySet()) {
			String mapKey = map.getKey().toString();

			builder.append("\t\"").append(map.getKey().toString()).append("\": ");
			builder.append("[");

			List<Map<String, String>> lista = schema.get(mapKey);

			for (Map<String, String> aLista : lista) {
				builder.append("\r\n");
				builder.append("\t\t{");
				builder.append("\r\n");
				for (Map.Entry values : aLista.entrySet()) {
					builder.append("\t\t\t");
					builder.append("\"").append(values.getKey().toString()).append("\": ").append("\"").append(values.getValue().toString()).append("\"");
					builder.append("\r\n");
				}

				builder.append("\t\t}");
			}


			builder.append("\r\n\t],\r\n");
		}
		builder.append("]");
		return builder.toString();
	}

	public void addTable(StringBuilder key, List<Map<String, String>> array) {
		schema.put(key.toString(), array);
	}	
	
}
