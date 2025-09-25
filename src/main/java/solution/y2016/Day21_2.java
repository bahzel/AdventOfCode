package solution.y2016;

import java.util.function.Consumer;

import utils.InstructionSolution;

public class Day21_2 extends InstructionSolution<Consumer<StringBuilder>, StringBuilder> {
    public static void main(String[] args) {
        new Day21_2().solve();
    }

    public Day21_2() {
        super(true);
    }

    @Override
    protected StringBuilder initializeValue() {
        return new StringBuilder("fbgdceah");
    }

    @Override
    protected Consumer<StringBuilder> transformInstruction(String instruction) {
        var instructions = instruction.split(" ");
        if ("swap".equals(instructions[0]) && "position".equals(instructions[1])) {
            return builder -> swap(Integer.parseInt(instructions[2]), Integer.parseInt(instructions[5]), builder);
        } else if ("swap".equals(instructions[0]) && "letter".equals(instructions[1])) {
            return builder -> swap(builder.indexOf(instructions[2]), builder.indexOf(instructions[5]), builder);
        } else if ("reverse".equals(instructions[0])) {
            var bottom = Integer.parseInt(instructions[2]);
            var ceiling = Integer.parseInt(instructions[4]);
            return builder -> {
                for (int i = 0; i <= (ceiling - bottom) / 2; i++) {
                    swap(bottom + i, ceiling - i, builder);
                }
            };
        } else if ("move".equals(instructions[0])) {
            return builder -> {
                var position = Integer.parseInt(instructions[5]);
                var move = builder.charAt(position);
                builder.deleteCharAt(position).insert(Integer.parseInt(instructions[2]), move);
            };
        } else if ("rotate".equals(instructions[0]) && "left".equals(instructions[1])) {
            return builder -> rotateRight(Integer.parseInt(instructions[2]), builder);
        } else if ("rotate".equals(instructions[0]) && "right".equals(instructions[1])) {
            return builder -> rotateLeft(Integer.parseInt(instructions[2]), builder);
        } else if ("rotate".equals(instructions[0])) {
            return builder -> {
                var index = builder.indexOf(instructions[6]);
                switch (index) {
                    case 0, 1:
                        rotateLeft(1, builder);
                        break;
                    case 2:
                        rotateRight(2, builder);
                        break;
                    case 3:
                        rotateLeft(2, builder);
                        break;
                    case 4:
                        rotateRight(1, builder);
                        break;
                    case 5:
                        rotateLeft(3, builder);
                        break;
                    case 7:
                        rotateRight(4, builder);
                }
            };
        } else {
            throw new IllegalArgumentException("Unknown instruction: " + instruction);
        }
    }

    private static void swap(int index1, int index2, StringBuilder stringBuilder) {
        if (index1 == index2) {
            return;
        }

        int min = Math.min(index1, index2);
        int max = Math.max(index1, index2);
        char minValue = stringBuilder.charAt(min);
        char maxValue = stringBuilder.charAt(max);
        stringBuilder.deleteCharAt(max).deleteCharAt(min).insert(min, maxValue).insert(max, minValue);
    }

    private static void rotateLeft(int amount, StringBuilder stringBuilder) {
        for (int i = 0; i < amount; i++) {
            var value = stringBuilder.charAt(0);
            stringBuilder.deleteCharAt(0).append(value);
        }
    }

    private static void rotateRight(int amount, StringBuilder stringBuilder) {
        for (int i = 0; i < amount; i++) {
            var value = stringBuilder.charAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).insert(0, value);
        }
    }

    @Override
    protected boolean performInstruction(Consumer<StringBuilder> stringBuilderConsumer, StringBuilder stringBuilder) {
        stringBuilderConsumer.accept(stringBuilder);
        return false;
    }

    @Override
    protected String getSolution(StringBuilder stringBuilder) {
        return stringBuilder.toString();
    }
}
