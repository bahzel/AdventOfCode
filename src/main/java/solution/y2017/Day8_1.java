package solution.y2017;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import utils.soution.InstructionSolution;

public class Day8_1 extends InstructionSolution<Consumer<Map<String, Long>>, Map<String, Long>> {
    public static void main(String[] args) {
        new Day8_1().solve();
    }

    @Override
    protected Map<String, Long> initializeValue() {
        return new HashMap<>();
    }

    @Override
    protected Consumer<Map<String, Long>> transformInstruction(String instruction) {
        var instructions = instruction.split(" ");
        return switch (instructions[1]) {
            case "inc" -> switch (instructions[5]) {
                case ">" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) > Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                    }
                };
                case "<" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) < Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                    }
                };
                case ">=" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) >= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                    }
                };
                case "<=" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) <= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                    }
                };
                case "==" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) == Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                    }
                };
                case "!=" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) != Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) + Long.parseLong(instructions[2]));
                    }
                };
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
            };
            case "dec" -> switch (instructions[5]) {
                case ">" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) > Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                    }
                };
                case "<" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) < Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                    }
                };
                case ">=" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) >= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                    }
                };
                case "<=" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) <= Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                    }
                };
                case "==" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) == Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                    }
                };
                case "!=" -> register -> {
                    if (register.computeIfAbsent(instructions[4], name -> 0L) != Long.parseLong(instructions[6])) {
                        register.put(instructions[0], register.computeIfAbsent(instructions[0], name -> 0L) - Long.parseLong(instructions[2]));
                    }
                };
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
            };
            default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
        };
    }

    @Override
    protected boolean performInstruction(Consumer<Map<String, Long>> mapConsumer, Map<String, Long> stringLongMap) {
        mapConsumer.accept(stringLongMap);
        return false;
    }

    @Override
    protected String getSolution(Map<String, Long> stringLongMap) {
        return stringLongMap.values().stream().mapToLong(Long::longValue).max().orElseThrow() + "";
    }
}
