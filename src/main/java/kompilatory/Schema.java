package kompilatory;

import java.sql.SQLException;
import java.util.*;

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
			int counter = lista.get(i).size() - 1 ;
			builder.append("\r\n");
			builder.append("\t\t{");
			builder.append("\r\n");

			for (Map.Entry values : lista.get(i).entrySet()) {

				if(!values.getKey().toString().equals("")) {
					builder.append("\t\t\t");
					builder.append("\"").append(values.getKey().toString()).append("\": ").append("\"").append(values.getValue().toString()).append("\"");

					if(counter != 0)
						builder.append(",");

					builder.append("\r\n");
					counter --;
				}
				else
					counter --;

			}

			builder.append("\t\t}");
			if(i != lista.size()-1)
				builder.append(",");
		}
	}

	public void addTable(StringBuilder key, List<Map<String, String>> array) {
		schema.put(key.toString(), array);
	}

	/**
	 * insert to table in schema. If column == null then it's take from table
	 * 
	 * @param table
	 * @param columns
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(String table, List<String> columns, List<String> values) throws SQLException {
		if(!schema.containsKey(table))
			throw new SQLException("table: "+table + " doesn't exist");
		List<String> validColumnes = new ArrayList<String>(schema.get(table).get(0).keySet());
		if(columns == null){
			columns = new ArrayList<String>(schema.get(table).get(0).keySet());		
		}		
		Map<String, String> record = new LinkedHashMap<String, String>();
		for(int i=0;i<validColumnes.size();i++){
			if(validColumnes.get(i).isEmpty())
				validColumnes.remove(i);
			if(columns.get(i).isEmpty())
				columns.remove(i);

			if(columns.contains(validColumnes.get(i)))
				record.put(validColumnes.get(i), values.get(columns.indexOf(validColumnes.get(i))));
			else
				record.put(validColumnes.get(i), "");
		}
		schema.get(table).add(record);
		return true;
	}

	/**
	 * create table
	 * @param tableName
	 * @param columnes
	 * @return
	 * @throws SQLException
	 */
	public boolean createTable(String tableName, String[] columnes) throws SQLException {
		if(schema.containsKey(tableName))
			throw new SQLException("table " + tableName + " alreadyExist");
		else{
			Map<String,String> record = new LinkedHashMap<String, String>();
			for(int i=0;i<columnes.length;i++) {
				record.put(columnes[i],"");
			}

			List<Map<String,String> > records = new ArrayList<Map<String,String>>();
			records.add(record);
			schema.put(tableName, records);
		}
		return true;
	}	


	public String select(String [] columns, String table) throws SQLException {
		if(!schema.containsKey(table))
			throw new SQLException("table: "+table + " doesn't exist");

		StringBuilder result = new StringBuilder();

		List<Map<String, String>> records = schema.get(table);

		if("*".equals(columns[0]))
			columns =  schema.get(table).get(0).keySet().toArray(new String [schema.get(table).get(0).keySet().size()]);

		for(String column : columns){
			if(!schema.get(table).get(0).keySet().contains(column))
				throw new SQLException("column "+ column + " doesn't exist");
			if(!column.isEmpty()){
				result.append(column);
				appendSpaces(result, column);
			}
		}
		result.append("\n");
		for(Map<String, String> record : records){
			for(String column : columns){
				if(!column.isEmpty()){
					result.append(record.get(column));
					appendSpaces(result, record.get(column));
				}
			}
			result.append("\n");
		}

		return result.toString();
	}

	private void appendSpaces(StringBuilder result, String column) {
		for(int i=0;i<30 - column.length();i++)
			result.append(" ");
	}

	public Boolean drop(String table) throws SQLException {

		if(!schema.containsKey(table))
			throw new SQLException("table: "+table + " doesn't exist");

		schema.keySet().remove(table);

		return true;

	}

	public int update(String table, List<Map<String, String>> newValues, Map<String, String> whereItems) throws SQLException {

		if(!schema.containsKey(table))
			throw new SQLException("table: "+table + " doesn't exist");


		List<Map<String, String>> records = schema.get(table);
		List<Map<String,String>> updatedRecords = new ArrayList<Map<String,String>>();

		for (Map<String,String> record : records) {
			for(String column : whereItems.keySet()) {
				if(!record.containsKey(column))
					throw new SQLException("Column " + column + " doesn't exist");

				if (record.containsKey(column) && record.containsValue(whereItems.get(column)))
					updatedRecords.add(record);
			}
		}

		for(Map<String,String> record: updatedRecords) {
			for(Map<String, String> map : newValues) {
				for(String key: map.keySet()) {
					if(!record.containsKey(key))
						throw new SQLException("Column " + key + " doesn't exist");
					if (record.containsKey(key))
						record.put(key, map.get(key));
				}
			}

		}

		return updatedRecords.size();
	}
}
