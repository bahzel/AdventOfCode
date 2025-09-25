package solution.y2015;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Solution;

public class Day12_2 extends Solution {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected String doSolve() {
		var array = new JSONArray(input.getFirst());
		return getSum(array) + "";
	}

	private long getSum(JSONArray array) {
		long sum = 0;

		for (int i = 0; i < array.length(); i++) {
			Object currentObject = array.get(i);

			if (currentObject instanceof Integer) {
				sum += array.getInt(i);
			} else if (currentObject instanceof JSONArray) {
				sum += getSum(array.getJSONArray(i));
			} else if (currentObject instanceof JSONObject) {
				sum += getSum(array.getJSONObject(i));
			}
		}

		return sum;
	}

	private long getSum(JSONObject object) {
		if (object.toMap().containsValue("red")) {
			return 0;
		}

		long sum = 0;

		for (var key : object.keySet()) {
			Object currentObject = object.get(key);

			if (currentObject instanceof Integer) {
				sum += object.getInt(key);
			} else if (currentObject instanceof JSONArray) {
				sum += getSum(object.getJSONArray(key));
			} else if (currentObject instanceof JSONObject) {
				sum += getSum(object.getJSONObject(key));
			}
		}

		return sum;
	}
}
