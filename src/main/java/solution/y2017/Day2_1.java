package solution.y2017;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import utils.InstructionSolution;
import utils.StringTransformer;

public class Day2_1 extends InstructionSolution<List<Long>, AtomicLong> {
    public static void main(String[] args) {
        new Day2_1().solve();
    }

    @Override
    protected AtomicLong initializeValue() {
        return new AtomicLong();
    }

    @Override
    protected List<Long> transformInstruction(String instruction) {
        return Arrays.stream(StringTransformer.removeDuplicateWhitespaces(instruction).split(" ")).map(Long::parseLong).collect(Collectors.toList());
    }

    @Override
    protected boolean performInstruction(List<Long> aLong, AtomicLong atomicLong) {
        atomicLong.addAndGet(aLong.stream().mapToLong(Long::longValue).max().orElse(0));
        atomicLong.addAndGet(aLong.stream().mapToLong(Long::longValue).min().orElse(0) * -1);
        return false;
    }

    @Override
    protected String getSolution(AtomicLong atomicLong) {
        return atomicLong.toString();
    }
}
