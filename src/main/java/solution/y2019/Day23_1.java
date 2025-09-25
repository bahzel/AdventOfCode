package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import utils.soution.Solution;

import static org.awaitility.Awaitility.await;

public class Day23_1 extends Solution {
    public static void main(String[] args) {
        new Day23_1().solve();
    }

    @Override
    protected String doSolve() {
        var register = new ArrayList<>(
                Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
        var network = new ArrayList<IntCodeInterpreter>();
        var inputs = new HashMap<Long, InputQueue>();
        var solution = new AtomicLong();

        for (var address = 0L; address < 50; address++) {
            var input = new InputQueue();
            input.add(address);
            inputs.put(address, input);

            network.add(new IntCodeInterpreter().withRegister(new ArrayList<>(register))
                    .withInput(input)
                    .withOutput(new OutputQueue(inputs, solution)));
        }

        network.forEach(computer -> new Thread(computer::performComputation).start());
        await().until(() -> solution.get() != 0);

        network.forEach(IntCodeInterpreter::interrupt);
        return solution.toString();
    }
}

class InputQueue extends LinkedBlockingQueue<Long> {
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Long take() throws InterruptedException {
        synchronized (this) {
            if (super.isEmpty()) {
                return -1L;
            }
            return super.take();
        }
    }

    public boolean add(Long x, Long y) {
        synchronized (this) {
            add(x);
            add(y);
        }
        return true;
    }
}

class OutputQueue extends LinkedBlockingQueue<Long> {
    private final Map<Long, InputQueue> inputs;
    private final List<Long> buffer = new ArrayList<>();
    private final AtomicLong solution;

    public OutputQueue(Map<Long, InputQueue> inputs, AtomicLong solution) {
        this.inputs = inputs;
        this.solution = solution;
    }

    @Override
    public boolean add(Long aLong) {
        if (solution.get() != 0) {
            return false;
        }

        buffer.add(aLong);

        if (buffer.size() == 3) {
            if (buffer.getFirst() == 255L) {
                solution.set(buffer.get(2));
            } else if (buffer.getFirst() < 50) {
                var input = inputs.get(buffer.getFirst());
                input.add(buffer.get(1), buffer.get(2));
            }
            buffer.clear();
        }

        return true;
    }
}
