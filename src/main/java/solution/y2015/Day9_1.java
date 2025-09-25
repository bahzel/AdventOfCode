package solution.y2015;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9_1 extends MapSolution<List<City>> {
	private final Map<String, City> cityList = new HashMap<>();
	private final Map<String, Long> CACHE = new HashMap<>();

	public static void main(String[] args) {
		new Day9_1().solve();
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
		long minimum = Long.MAX_VALUE;
		for (var nextCity : cities) {
			var nextList = new ArrayList<>(cities);
			nextList.remove(nextCity);
			minimum = Math.min(minimum, computeMinimum(nextCity, nextList));
		}
		return minimum + "";
	}

	private long computeMinimum(City before, List<City> cities) {
		var hashKey = "" + before.hashCode() + cities.hashCode();
		if (CACHE.containsKey(hashKey)) {
			return CACHE.get(hashKey);
		}

		long minimum = Long.MAX_VALUE;

		if (cities.size() == 1) {
			minimum = before.getDistance(cities.getFirst());
		} else {
			for (var nextCity : cities) {
				var nextList = new ArrayList<>(cities);
				nextList.remove(nextCity);
				minimum = Math.min(minimum, before.getDistance(nextCity) + computeMinimum(nextCity, nextList));
			}
		}

		CACHE.put(hashKey, minimum);
		return minimum;
	}
}

@EqualsAndHashCode
class City {
	@Getter
	private final String name;
	@EqualsAndHashCode.Exclude
	private final Map<City, Long> distanceMap = new HashMap<>();

	public City(String name) {
		this.name = name;
	}

	public void addDistance(City city, long distance) {
		distanceMap.put(city, distance);
	}

	public long getDistance(City city) {
		return distanceMap.get(city);
	}
}
