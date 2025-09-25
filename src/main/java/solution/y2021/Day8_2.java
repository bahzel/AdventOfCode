package solution.y2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import utils.StringTransformer;
import utils.soution.MapSolution;

public class Day8_2 extends MapSolution<List<Pair<List<String>, Set<String>>>> {
    public static void main(String[] args) {
        new Day8_2().solve();
    }

    @Override
    protected List<Pair<List<String>, Set<String>>> initializeMapping() {
        return new ArrayList<>();
    }

    @Override
    protected void transformInstruction(String instruction, List<Pair<List<String>, Set<String>>> pairs) {
        var instructions = instruction.split(" \\| ");
        var number = Pair.of(Arrays.stream(instructions[0].split(" ")).map(StringTransformer::sort).toList(),
                Arrays.stream(instructions[1].split(" ")).map(StringTransformer::sort).toList());
        var numberSet = new HashSet<String>();
        numberSet.addAll(number.getLeft());
        numberSet.addAll(number.getRight());
        pairs.add(Pair.of(number.getRight(), numberSet));
    }

    @Override
    protected String computeSolution(List<Pair<List<String>, Set<String>>> pairs) {
        return pairs.stream().mapToLong(number -> convert(createDigitMap(number.getRight()), number.getLeft())).sum()
                + "";
    }

    private long convert(Map<Set<String>, String> digitMap, List<String> number) {
        return Long.parseLong(number.stream()
                .map(digit -> digitMap.get(new HashSet<>(Arrays.asList(digit.split("")))))
                .collect(Collectors.joining()));
    }

    private Map<Set<String>, String> createDigitMap(Set<String> numbers) {
        var one = getNumberWithSize(numbers, 2).getFirst();
        Set<String> two = null;
        var four = getNumberWithSize(numbers, 4).getFirst();
        var seven = getNumberWithSize(numbers, 3).getFirst();
        Set<String> nine = null;
        var sixOrNineOrZero = getNumberWithSize(numbers, 6);
        var twoOrThreeOrFive = getNumberWithSize(numbers, 5);

        String upper;
        String upperLeft;
        String upperRight;
        String middle;
        String lowerLeft = null;
        String lowerRight;
        String lower = null;

        var working = new HashSet<>(seven);
        working.removeAll(one);
        upper = working.iterator().next();

        for (var candidateForNine : sixOrNineOrZero) {
            working = new HashSet<>(candidateForNine);
            working.removeAll(four);
            if (working.size() == 2) {
                nine = candidateForNine;
                working.remove(upper);
                lower = working.iterator().next();
            }
        }

        for (var candidateForTwo : twoOrThreeOrFive) {
            working = new HashSet<>(candidateForTwo);
            working.removeAll(four);
            if (working.size() == 3) {
                two = candidateForTwo;
                working.remove(upper);
                working.remove(lower);
                lowerLeft = working.iterator().next();
            }
        }

        working = new HashSet<>(two);
        working.removeAll(seven);
        working.remove(lowerLeft);
        working.remove(lower);
        middle = working.iterator().next();

        working = new HashSet<>(two);
        working.remove(upper);
        working.remove(middle);
        working.remove(lowerLeft);
        working.remove(lower);
        upperRight = working.iterator().next();

        working = new HashSet<>(one);
        working.remove(upperRight);
        lowerRight = working.iterator().next();

        working = new HashSet<>(four);
        working.removeAll(one);
        working.remove(middle);
        upperLeft = working.iterator().next();

        var digitMap = new HashMap<Set<String>, String>();
        digitMap.put(Set.of(upper, upperLeft, upperRight, lowerLeft, lowerRight, lower), "0");
        digitMap.put(one, "1");
        digitMap.put(two, "2");
        digitMap.put(Set.of(upper, upperRight, middle, lowerRight, lower), "3");
        digitMap.put(four, "4");
        digitMap.put(Set.of(upper, upperLeft, middle, lowerRight, lower), "5");
        digitMap.put(Set.of(upper, upperLeft, middle, lowerLeft, lowerRight, lower), "6");
        digitMap.put(seven, "7");
        digitMap.put(getNumberWithSize(numbers, 7).getFirst(), "8");
        digitMap.put(nine, "9");
        return digitMap;
    }

    private List<HashSet<String>> getNumberWithSize(Set<String> numbers, int size) {
        return numbers.stream()
                .filter(digit -> digit.length() == size)
                .map(digit -> Arrays.asList(digit.split("")))
                .map(HashSet::new)
                .toList();
    }
}
