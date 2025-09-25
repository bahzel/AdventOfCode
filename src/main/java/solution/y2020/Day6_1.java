package solution.y2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import utils.soution.Solution;

public class Day6_1 extends Solution {
	public static void main(String[] args) {
		new Day6_1().solve();
	}

	@Override
	protected String doSolve() {
		var count = 0;

		var forms = getForms();
		for (var form : forms) {
			var chars = new HashSet<Character>();
			for (var questions : form) {
				for (var question : questions.toCharArray()) {
					chars.add(question);
				}
			}
			count += chars.size();
		}

		return count + "";
	}

	private List<List<String>> getForms() {
		var forms = new ArrayList<List<String>>();
		var form = new ArrayList<String>();

		for (var inputs : input) {
			if (inputs.isEmpty()) {
				forms.add(form);
				form = new ArrayList<>();
				continue;
			}

			form.add(inputs);
		}

		forms.add(form);
		return forms;
	}
}
