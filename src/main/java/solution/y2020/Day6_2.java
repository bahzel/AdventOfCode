package solution.y2020;

import java.util.ArrayList;
import java.util.List;

import utils.soution.Solution;

public class Day6_2 extends Solution {
	public static void main(String[] args) {
		new Day6_2().solve();
	}

	@Override
	protected String doSolve() {
		var count = 0;

		var forms = getForms();
		for (var form : forms) {
			for (var question = 'a'; question <= 'z'; question++) {
				if (containsQuestion(form, question)) {
					count++;
				}
			}
		}

		return count + "";
	}

	private boolean containsQuestion(List<String> questions, char id) {
		for (var question : questions) {
			if (!question.contains(id + "")) {
				return false;
			}
		}
		return true;
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
