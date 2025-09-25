package solution.y2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Triple;
import utils.MapSolution;

public class Day23_1 extends MapSolution<List<Triple<BiConsumer<Map<String, Integer>, AtomicInteger>, BiConsumer<Map<String, Integer>, AtomicInteger>, AtomicBoolean>>> {
    public static void main(String[] args) {
        new Day23_1().solve();
    }

    @Override
    protected List<Triple<BiConsumer<Map<String, Integer>, AtomicInteger>, BiConsumer<Map<String, Integer>, AtomicInteger>, AtomicBoolean>> initializeMapping() {
        return new ArrayList<>();
    }

    @Override
    protected void transformInstruction(String instruction, List<Triple<BiConsumer<Map<String, Integer>, AtomicInteger>, BiConsumer<Map<String, Integer>, AtomicInteger>, AtomicBoolean>> consumers) {
        var instructions = instruction.split(" ");
        switch (instructions[0]) {
            case "cpy":
                consumers.add(Triple.of(cpy(instructions[1], instructions[2]), jnz(instructions[0], instructions[1]), new AtomicBoolean(true)));
                break;
            case "inc":
                consumers.add(Triple.of(inc(instructions[1]), dec(instructions[1]), new AtomicBoolean(true)));
                break;
            case "dec":
                consumers.add(Triple.of(dec(instructions[1]), inc(instructions[1]), new AtomicBoolean(true)));
                break;
            case "jnz":
                consumers.add(Triple.of(jnz(instructions[1], instructions[2]), cpy(instructions[1], instructions[2]), new AtomicBoolean(true)));
                break;
            case "tgl":
                consumers.add(Triple.of(tgl(instructions[1], consumers), inc(instructions[1]), new AtomicBoolean(true)));
        }
    }

    private BiConsumer<Map<String, Integer>, AtomicInteger> cpy(String param1, String param2) {
        if (NumberUtils.isParsable(param2)) {
            return (registers, index) -> {
            };
        }

        if (NumberUtils.isParsable(param1)) {
            return (registers, index) -> registers.put(param2, Integer.parseInt(param1));
        } else {
            return (registers, index) -> registers.put(param2, registers.get(param1));
        }
    }

    private BiConsumer<Map<String, Integer>, AtomicInteger> jnz(String param1, String param2) {
        if (NumberUtils.isParsable(param1)) {
            if (NumberUtils.isParsable(param2)) {
                return (registers, index) -> {
                    if (Integer.parseInt(param1) != 0) {
                        index.addAndGet(Integer.parseInt(param2) - 1);
                    }
                };
            } else {
                return (registers, index) -> {
                    if (Integer.parseInt(param1) != 0) {
                        index.addAndGet(registers.get(param2) - 1);
                    }
                };
            }
        } else {
            if (NumberUtils.isParsable(param2)) {
                return (registers, index) -> {
                    if (registers.get(param1) != 0) {
                        index.addAndGet(Integer.parseInt(param2) - 1);
                    }
                };
            } else {
                return (registers, index) -> {
                    if (registers.get(param1) != 0) {
                        index.addAndGet(registers.get(param2) - 1);
                    }
                };
            }
        }
    }

    private BiConsumer<Map<String, Integer>, AtomicInteger> inc(String param) {
        if (NumberUtils.isParsable(param)) {
            return (registers, index) -> {
            };
        }

        return (registers, index) -> registers.put(param, registers.get(param) + 1);
    }

    private BiConsumer<Map<String, Integer>, AtomicInteger> dec(String param) {
        if (NumberUtils.isParsable(param)) {
            return (registers, index) -> {
            };
        }

        return (registers, index) -> registers.put(param, registers.get(param) - 1);
    }

    private BiConsumer<Map<String, Integer>, AtomicInteger> tgl(String param, List<Triple<BiConsumer<Map<String, Integer>, AtomicInteger>, BiConsumer<Map<String, Integer>, AtomicInteger>, AtomicBoolean>> consumers) {
        if (NumberUtils.isParsable(param)) {
            return (registers, index) -> toggle(consumers.get(index.get() + Integer.parseInt(param)).getRight());
        } else {
            return (registers, index) -> {
                var consumerIndex = index.get() + registers.get(param);
                if (consumerIndex >= 0 && consumerIndex < consumers.size()) {
                    toggle(consumers.get(consumerIndex).getRight());
                }
            };
        }
    }

    private void toggle(AtomicBoolean bool) {
        bool.set(!bool.get());
    }

    @Override
    protected String computeSolution(List<Triple<BiConsumer<Map<String, Integer>, AtomicInteger>, BiConsumer<Map<String, Integer>, AtomicInteger>, AtomicBoolean>> consumers) {
        var register = new HashMap<String, Integer>();
        register.put("a", 7);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);

        for (AtomicInteger i = new AtomicInteger(); i.get() < consumers.size(); i.incrementAndGet()) {
            var consumer = consumers.get(i.get());
            if (consumer.getRight().get()) {
                consumer.getLeft().accept(register, i);
            } else {
                consumer.getMiddle().accept(register, i);
            }
        }

        return register.get("a") + "";
    }
}
