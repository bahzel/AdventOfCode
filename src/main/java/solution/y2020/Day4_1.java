package solution.y2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.soution.Solution;

public class Day4_1 extends Solution {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected String doSolve() {
		var count = 0;

		for (var passport : getPassports()) {
			if (isValid(passport)) {
				count++;
			}
		}

		return count + "";
	}

	private boolean isValid(Map<String, String> passport) {
		return passport.containsKey("byr") && passport.containsKey("iyr") && passport.containsKey("eyr")
				&& passport.containsKey("hgt") && passport.containsKey("hcl") && passport.containsKey("ecl")
				&& passport.containsKey("pid");
	}

	private List<Map<String, String>> getPassports() {
		var passports = new ArrayList<Map<String, String>>();
		var passport = new HashMap<String, String>();

		for (var data : input) {
			if (data.isEmpty()) {
				passports.add(passport);
				passport = new HashMap<>();
				continue;
			}

			var datas = data.split(" ");
			for (var item : datas) {
				var items = item.split(":");
				passport.put(items[0], items[1]);
			}
		}

		passports.add(passport);
		return passports;
	}
}
