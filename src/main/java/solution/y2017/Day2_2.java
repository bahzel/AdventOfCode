package solution.y2017;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import utils.soution.InstructionSolution;
import utils.StringTransformer;

public class Day2_2 extends InstructionSolution<List<Long>, AtomicLong> {
    public static void main(String[] args) {
        new Day2_2().solve();
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
        for (int i = 0; i < aLong.size(); i++) {
            for (int j = 0; j < aLong.size(); j++) {
                if (i == j) continue;

                if (aLong.get(i) % aLong.get(j) == 0) {
                    atomicLong.addAndGet(aLong.get(i) / aLong.get(j));
                    return false;
                }
            }
        }

        throw new IllegalStateException();
    }

    @Override
    protected String getSolution(AtomicLong atomicLong) {
        return atomicLong.toString();
    }
}
