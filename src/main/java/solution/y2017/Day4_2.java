package solution.y2017;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day4_2 extends InstructionSolution<List<String>, AtomicInteger> {
    public static void main(String[] args) {
        new Day4_2().solve();
    }

    @Override
    protected AtomicInteger initializeValue() {
        return new AtomicInteger();
    }

    @Override
    protected List<String> transformInstruction(String instruction) {
        return List.of(instruction.split(" "));
    }

    @Override
    protected boolean performInstruction(List<String> strings, AtomicInteger atomicInteger) {
        if (strings.stream().map(this::sortString).distinct().count() == strings.size()) {
            atomicInteger.incrementAndGet();
        }
        return false;
    }

    private String sortString(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    @Override
    protected String getSolution(AtomicInteger atomicInteger) {
        return atomicInteger.toString();
    }
}
