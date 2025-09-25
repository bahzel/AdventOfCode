package utils.soution;

import java.util.List;

import lombok.Getter;

public abstract class InstructionSolution<Instruction, Value> extends Solution {
    private final boolean reverse;
    @Getter
    private Value value;
    @Getter
    private List<Instruction> instructions;

    public InstructionSolution() {
        super();
        reverse = false;
    }

    public InstructionSolution(List<String> input) {
        super(input);
        reverse = false;
    }

    public InstructionSolution(boolean reverse) {
        super();
        this.reverse = reverse;
    }

    @Override
    protected void initialize() {
        value = initializeValue();
        instructions = getInstructions(input).stream().map(this::transformInstruction).toList();
        if (reverse) {
            instructions = instructions.reversed();
        }
    }

    @Override
    public String doSolve() {
        for (Instruction instruction : instructions) {
            if (performInstruction(instruction, value)) {
                break;
            }
        }
        return getSolution(value);
    }

    protected List<String> getInstructions(List<String> instructions) {
        return instructions;
    }

    protected abstract Value initializeValue();

    protected abstract Instruction transformInstruction(String instruction);

    /**
     * @return true if the algorithm shall finish after this instruction
     */
    protected abstract boolean performInstruction(Instruction instruction, Value value);

    protected abstract String getSolution(Value value);
}
