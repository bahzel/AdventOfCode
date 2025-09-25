package solution.y2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import utils.soution.Solution;

public class Day4_2 extends Solution {
	public static void main(String[] args) {
		new Day4_2().solve();
	}

	@Override
	protected String doSolve() {
		var count = 0;

		for (var passport : getPassports()) {
			if (isComplete(passport) && isValid(passport)) {
				count++;
			}
		}

		return count + "";
	}

	private boolean isComplete(Map<String, String> passport) {
		return passport.containsKey("byr") && passport.containsKey("iyr") && passport.containsKey("eyr")
				&& passport.containsKey("hgt") && passport.containsKey("hcl") && passport.containsKey("ecl")
				&& passport.containsKey("pid");
	}

	private final Pattern COLOR = Pattern.compile("^#[0123456789abcdef]{6}$");
	private final List<String> VALID_EYE_COLORS = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
	private final Pattern PID = Pattern.compile("^\\d{9}$");

	private boolean isValid(Map<String, String> passport) {
		var byr = Integer.parseInt(passport.get("byr"));
		if (byr < 1920 || byr > 2002) {
			return false;
		}

		var iyr = Integer.parseInt(passport.get("iyr"));
		if (iyr < 2010 || iyr > 2020) {
			return false;
		}

		var eyr = Integer.parseInt(passport.get("eyr"));
		System.out.println(eyr);
		if (eyr < 2020 || eyr > 2030) {
			return false;
		}

		var hgt = passport.get("hgt");
		if (hgt.endsWith("cm")) {
			var hgtValue = Integer.parseInt(hgt.replace("cm", ""));
			if (hgtValue < 150 || hgtValue > 193) {
				return false;
			}
		} else if (hgt.endsWith("in")) {
			var hgtValue = Integer.parseInt(hgt.replace("in", ""));
			if (hgtValue < 59 || hgtValue > 76) {
				return false;
			}
		} else {
			return false;
		}

		if (!COLOR.matcher(passport.get("hcl")).find()) {
			return false;
		}

		if (!VALID_EYE_COLORS.contains(passport.get("ecl"))) {
			return false;
		}

		return PID.matcher(passport.get("pid")).find();
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
