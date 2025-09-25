package solution.y2017;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import utils.soution.InstructionSolution;

public class Day8_2 extends InstructionSolution<BiConsumer<Map<String, Long>, AtomicLong>, Map<String, Long>> {
    private final AtomicLong maxValue = new AtomicLong();

    public static void main(String[] args) {
        new Day8_2().solve();
    }

    @Override
    protected Map<String, Long> initializeValue() {
        return new HashMap<>();
    }

    @Override
    protected BiConsumer<Map<String, Long>, AtomicLong> transformInstruction(String instruction) {
        var instructions = instruction.split(" ");
        return switch (instructions[1]) {
            case "inc" -> switch (instructions[5]) {
                case ">" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) > Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "<" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) < Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case ">=" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) >= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "<=" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) <= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "==" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) == Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "!=" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) != Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
            };
            case "dec" -> switch (instructions[5]) {
                case ">" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) > Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "<" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) < Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case ">=" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) >= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "<=" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) <= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "==" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) == Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                case "!=" -> (register, maxValue) -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) != Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                        maxValue.set(Math.max(maxValue.get(), register.get(instructions[0])));
                    }
                };
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
            };
            default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
        };
    }

    @Override
    protected boolean performInstruction(BiConsumer<Map<String, Long>, AtomicLong> mapConsumer, Map<String, Long> stringLongMap) {
        mapConsumer.accept(stringLongMap, maxValue);
        return false;
    }

    @Override
    protected String getSolution(Map<String, Long> stringLongMap) {
        return maxValue.toString();
    }
}
