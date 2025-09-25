package solution.y2015;

import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9_2 extends MapSolution<List<City>> {
	private final Map<String, City> cityList = new HashMap<>();
	private final Map<String, Long> CACHE = new HashMap<>();

	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected List<City> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<City> cities) {
		var instructions = instruction.split(" = ");
		var connection = instructions[0].split(" to ");
		var distance = Long.parseLong(instructions[1]);

		var city1 = cityList.computeIfAbsent(connection[0], name -> createCity(name, cities));
		var city2 = cityList.computeIfAbsent(connection[1], name -> createCity(name, cities));

		city1.addDistance(city2, distance);
		city2.addDistance(city1, distance);
	}

	private City createCity(String name, List<City> cities) {
		var city = new City(name);
		cities.add(city);
		return city;
	}

	@Override
	protected String computeSolution(List<City> cities) {
		long maximum = Long.MIN_VALUE;
		for (var nextCity : cities) {
			var nextList = new ArrayList<>(cities);
			nextList.remove(nextCity);
			maximum = Math.max(maximum, computeMaximum(nextCity, nextList));
		}
		return maximum + "";
	}

	private long computeMaximum(City before, List<City> cities) {
		var hashKey = "" + before.hashCode() + cities.hashCode();
		if (CACHE.containsKey(hashKey)) {
			return CACHE.get(hashKey);
		}

		long maximum = Long.MIN_VALUE;

		if (cities.size() == 1) {
			maximum = before.getDistance(cities.getFirst());
		} else {
			for (var nextCity : cities) {
				var nextList = new ArrayList<>(cities);
				nextList.remove(nextCity);
				maximum = Math.max(maximum, before.getDistance(nextCity) + computeMaximum(nextCity, nextList));
			}
		}

		CACHE.put(hashKey, maximum);
		return maximum;
	}
}