package kompilatory;

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

	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("{\r\n");

		int i = schema.entrySet().size()-1;
		for (Map.Entry map : schema.entrySet()) {

			String mapKey = map.getKey().toString();

			builder.append("\t\"").append(map.getKey().toString()).append("\": ");
			builder.append("[");

			List<Map<String, String>> lista = schema.get(mapKey);

			parseValues(builder, lista);


			builder.append("\r\n\t]");

			if(i != 0)
				builder.append(",");
			builder.append("\r\n");

			i--;
		}
		builder.append("}");
		return builder.toString();
	}

	@SuppressWarnings("rawtypes")
	public void parseValues(StringBuilder builder, List<Map<String, String>> lista) {

		for (int i =0; i < lista.size(); i++) {
            builder.append("\r\n");
            builder.append("\t\t{");
            builder.append("\r\n");
			int counter = lista.get(i).size() - 2 ;
            for (Map.Entry values : lista.get(i).entrySet()) {

				if(!values.getKey().toString().equals("")) {
					builder.append("\t\t\t");
					builder.append("\"").append(values.getKey().toString()).append("\": ").append("\"").append(values.getValue().toString()).append("\"");

					if(counter != 0)
						builder.append(",");

					builder.append("\r\n");
					counter --;
				}

            }

            builder.append("\t\t}");
			if(i != lista.size()-1)
				builder.append(",");
        }
	}

	public void addTable(StringBuilder key, List<Map<String, String>> array) {
		schema.put(key.toString(), array);
	}	
	
}
