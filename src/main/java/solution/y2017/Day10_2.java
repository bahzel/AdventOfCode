package solution.y2017;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import utils.soution.Solution;

public class Day10_2 extends Solution {
    private final int SIZE = 256;

    public static void main(String[] args) {
        new Day10_2().solve();
    }

    public Day10_2() {
        super();
    }

    public Day10_2(String input) {
        super(List.of(input));
    }

    @Override
    public String doSolve() {
        var sequence = initializeSequence();
        var circularList = initializeCircularList();
        return performHashing(circularList, sequence);
    }

    private List<Integer> initializeSequence() {
        var sequence = new ArrayList<Integer>();
        for (var character : input.getFirst().toCharArray()) {
            sequence.add((int) character);
        }
        sequence.add(17);
        sequence.add(31);
        sequence.add(73);
        sequence.add(47);
        sequence.add(23);
        return sequence;
    }

    private int[] initializeCircularList() {
        var values = new int[SIZE];
        for (int i = 0; i < SIZE; i++) values[i] = i;
        return values;
    }

    private String performHashing(int[] circularList, List<Integer> lengths) {
        var index = new AtomicInteger();
        var skipSize = new AtomicInteger();
        for (int i = 0; i < 64; i++) {
            performRound(circularList, lengths, index, skipSize);
        }
        return computeSparsHash(circularList);
    }

    private String computeSparsHash(int[] circularList) {
        var solution = new StringBuilder();
        for (int i = 0; i < circularList.length; i = i + 16) {
            solution.append(computeHash(circularList, i, i + 15));
        }
        return solution.toString();
    }

    private String computeHash(int[] circularList, int startIndex, int endIndex) {
        var hash = circularList[startIndex];
        for (int i = startIndex + 1; i <= endIndex; i++) {
            hash ^= circularList[i];
        }
        return StringUtils.leftPad(Integer.toHexString(hash), 2, "0");
    }

    private void performRound(int[] circularList, List<Integer> lengths, AtomicInteger index, AtomicInteger skipSize) {
        for (var length : lengths) {
            performInstruction(circularList, length, index, skipSize);
        }
    }

    private void performInstruction(int[] circularList, int length, AtomicInteger index, AtomicInteger skipSize) {
        var startIndex = index.get();
        var endIndex = (startIndex + length - 1) % SIZE;

        for (int i = 0; i < length / 2; i++) {
            var value = circularList[endIndex];
            circularList[endIndex] = circularList[startIndex];
            circularList[startIndex] = value;
            startIndex++;
            endIndex--;

            if (startIndex >= SIZE) {
                startIndex = 0;
            }
            if (endIndex < 0) {
                endIndex = SIZE - 1;
            }
        }

        index.set((index.get() + length + skipSize.get()) % SIZE);
        skipSize.incrementAndGet();
    }
}
