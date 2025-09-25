package solution.y2017;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import utils.soution.Solution;
import utils.StringTransformer;

public class Day6_1 extends Solution {
    private final Set<String> CACHE = new HashSet<>();

    public static void main(String[] args) {
        new Day6_1().solve();
    }

    @Override
    protected String doSolve() {
        var memory = Arrays.stream(StringTransformer.removeDuplicateWhitespaces(input.getFirst()).split(" ")).mapToInt(Integer::parseInt).toArray();

        var i = 0L;
        for (; CACHE.add(Arrays.toString(memory)); i++) {
            reallocate(memory);
        }

        return i + "";
    }

    private void reallocate(int[] memory) {
        var index = maxIndex(memory);
        var amount = memory[index];
        memory[index] = 0;

        for (int i = 1; i <= amount; i++) {
            memory[(index + i) % memory.length]++;
        }
    }

    private int maxIndex(int[] memory) {
        int index = 0;
        int max = memory[0];

        for (int i = 1; i < memory.length; i++) {
            if (memory[i] > max) {
                max = memory[i];
                index = i;
            }
        }

        return index;
    }
}
