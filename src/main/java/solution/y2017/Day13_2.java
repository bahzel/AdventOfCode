package solution.y2017;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.InstructionSolution;

public class Day13_2 extends InstructionSolution<Pair<Integer, Integer>, AtomicLong> {
    public static void main(String[] args) {
        new Day13_2().solve();
    }

    @Override
    protected AtomicLong initializeValue() {
        return new AtomicLong(-1);
    }

    @Override
    protected Pair<Integer, Integer> transformInstruction(String instruction) {
        var instructions = instruction.split(": ");
        return Pair.of(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]));
    }

    @Override
    public String doSolve() {
        var solutionFound = false;

        while (!solutionFound) {
            getValue().incrementAndGet();
            solutionFound = true;
            for (var instruction : getInstructions()) {
                if (!performInstruction(instruction, getValue())) {
                    solutionFound = false;
                    break;
                }
            }
        }
        return getSolution(getValue());
    }

    @Override
    protected boolean performInstruction(Pair<Integer, Integer> integerIntegerPair, AtomicLong atomicLong) {
        return (integerIntegerPair.getLeft() + atomicLong.get()) % (integerIntegerPair.getRight() * 2 - 2) != 0;
    }

    @Override
    protected String getSolution(AtomicLong atomicLong) {
        return atomicLong.toString();
    }
}
