package solution.y2016;

import java.util.LinkedList;
import java.util.ListIterator;

import utils.soution.Solution;

public class Day19_2 extends Solution {
    public static void main(String[] args) {
        new Day19_2().solve();
    }

    @Override
    protected String doSolve() {
        var elfCount = Integer.parseInt(input.getFirst());
        var queue = new LinkedList<Integer>();

        for (int i = 1; i <= elfCount; i++) {
            queue.add(i);
        }

        var iterator = new CyclingIntIterator(queue);
        while (queue.size() > 1) {
            iterator.next();
            iterator.remove();
            iterator.next();
            iterator.remove();
            iterator.next();
        }
        return iterator.next() + "";
    }
}

class CyclingIntIterator {
    private final LinkedList<Integer> list;
    private ListIterator<Integer> iterator;

    public CyclingIntIterator(LinkedList<Integer> list) {
        this.list = list;
        iterator = list.listIterator(list.size() / 2);
    }

    public int next() {
        if (!iterator.hasNext()) {
            iterator = list.listIterator();
        }
        return iterator.next();
    }

    public void remove() {
        if (list.size() > 1) {
            iterator.remove();
        }
    }
}
