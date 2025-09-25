package solution.y2017;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Triple;
import utils.soution.InstructionSolution;

public class Day10_1 extends InstructionSolution<Integer, Triple<int[], AtomicInteger, AtomicInteger>> {
    private final int SIZE = 256;

    public static void main(String[] args) {
        new Day10_1().solve();
    }

    @Override
    protected Triple<int[], AtomicInteger, AtomicInteger> initializeValue() {
        var values = new int[SIZE];
        for (int i = 0; i < SIZE; i++) values[i] = i;
        return Triple.of(values, new AtomicInteger(0), new AtomicInteger(0));
    }

    @Override
    protected List<String> getInstructions(List<String> instructions) {
        return Arrays.asList(instructions.getFirst().split(","));
    }

    @Override
    protected Integer transformInstruction(String instruction) {
        return Integer.parseInt(instruction);
    }

    @Override
    protected boolean performInstruction(Integer integer, Triple<int[], AtomicInteger, AtomicInteger> ints) {
        var startIndex = ints.getMiddle().get();
        var endIndex = (startIndex + integer - 1) % SIZE;

        for (int i = 0; i < integer / 2; i++) {
            var value = ints.getLeft()[endIndex];
            ints.getLeft()[endIndex] = ints.getLeft()[startIndex];
            ints.getLeft()[startIndex] = value;
            startIndex++;
            endIndex--;

            if (startIndex >= SIZE) {
                startIndex = 0;
            }
            if (endIndex < 0) {
                endIndex = SIZE - 1;
            }
        }

        ints.getMiddle().set((ints.getMiddle().get() + integer + ints.getRight().get()) % SIZE);
        ints.getRight().incrementAndGet();

        return false;
    }

    @Override
    protected String getSolution(Triple<int[], AtomicInteger, AtomicInteger> ints) {
        return ints.getLeft()[0] * ints.getLeft()[1] + "";
    }
}
