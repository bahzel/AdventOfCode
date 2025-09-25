package solution.y2017;

import utils.Tree;
import utils.soution.Solution;

public class Day9_1 extends Solution {
    public static void main(String[] args) {
        new Day9_1().solve();
    }

    @Override
    protected String doSolve() {
        String program = input.getFirst();
        var parent = new Tree<>(0);
        var state = ParserState.Group;

        for (int i = 0; i < program.length(); i++) {
            if (state == ParserState.Group && '{' == program.charAt(i)) {
                var child = new Tree<>(parent.getValue() + 1);
                parent.addChild(child);
                parent = child;
            } else if (state == ParserState.Group && '}' == program.charAt(i)) {
                parent = parent.getParent();
            } else if (state == ParserState.Group && '<' == program.charAt(i)) {
                state = ParserState.Garbage;
            } else if (state == ParserState.Garbage && '>' == program.charAt(i)) {
                state = ParserState.Group;
            } else if (state == ParserState.Garbage && '!' == program.charAt(i)) {
                i++;
            }
        }

        return computeScore(parent) + "";
    }

    private long computeScore(Tree<Integer> tree) {
        long score = tree.getValue();
        for (var child : tree.getChildren()) {
            score += computeScore(child);
        }
        return score;
    }
}

enum ParserState {
    Group, Garbage
}
