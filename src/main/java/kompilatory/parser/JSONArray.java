package kompilatory.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class JSONArray extends ArrayList<JSONValue> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5293182940970820529L;

	public JSONArray() {
		super();
	}

	public JSONArray(Collection<JSONValue> c) {
		super(c);
	}

	public static String toJSONString(Collection<JSONValue> collection) {
		StringBuilder sb = new StringBuilder();
		if (collection == null) {
			sb.append("null");
			return sb.toString();
		}

		boolean first = true;
		Iterator iter = collection.iterator();

		sb.append('[');
		while (iter.hasNext()) {
			if (first)
				first = false;
			else
				sb.append(',');

			Object value = iter.next();
			if (value == null) {
				sb.append("null");
				continue;
			}

			sb.append(JSONValue.toJSONString(value));
		}
		sb.append(']');
		return sb.toString();
	}

	public static String toJSONString(Object[] array) {
		StringBuilder sb = new StringBuilder();
		if (array == null) {
			sb.append("null");
		} else if (array.length == 0) {
			sb.append("[]");
		} else {
			sb.append("[");
			sb.append(JSONValue.toJSONString(array[0]));

			for (int i = 1; i < array.length; i++) {
				sb.append(",");
				sb.append(JSONValue.toJSONString(array[i]));
			}

			sb.append("]");
		}
		return sb.toString();
	}

	public String toJSONString() {
		return toJSONString(this);
	}

	/**
	 * Returns a string representation of this array. This is equivalent to
	 * calling {@link JSONArray#toJSONString()}.
	 */
	public String toString() {
		return toJSONString();
	}
}
