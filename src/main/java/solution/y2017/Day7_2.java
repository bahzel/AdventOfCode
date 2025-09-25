package solution.y2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;
import utils.Tree;
import utils.soution.MapSolution;

public class Day7_2 extends MapSolution<Map<String, Tree<Integer>>> {
    public static void main(String[] args) {
        new Day7_2().solve();
    }

    @Override
    protected Map<String, Tree<Integer>> initializeMapping() {
        return new HashMap<>();
    }

    @Override
    protected void transformInstruction(String instruction, Map<String, Tree<Integer>> stringTreeMap) {
        var instructions = instruction.split(" -> ");
        var parentInstruction = instructions[0].replace(")", "").split((" \\("));

        var parent = stringTreeMap.computeIfAbsent(parentInstruction[0], name -> new Tree<>());
        parent.setValue(Integer.parseInt(parentInstruction[1]));

        if (instructions.length > 1) {
            var childInstructions = instructions[1].split(", ");
            for (String childInstruction : childInstructions) {
                var child = stringTreeMap.computeIfAbsent(childInstruction, name -> new Tree<>());
                parent.addChild(child);
            }
        }
    }

    @Override
    protected String computeSolution(Map<String, Tree<Integer>> stringTreeMap) {
        var parent = findParent(stringTreeMap);
        var unbalancedChild = getUnbalancedChild(parent);
        while (!isBalanced(unbalancedChild)) {
            parent = unbalancedChild;
            unbalancedChild = getUnbalancedChild(unbalancedChild);
        }
        var balancedChild = getBalancedChild(parent);
        return unbalancedChild.getValue() + computeSum(balancedChild) - computeSum(unbalancedChild) + "";
    }

    private boolean isBalanced(Tree<Integer> tree) {
        return computeValues(tree).stream().map(Pair::getLeft).distinct().count() == 1;
    }

    private Tree<Integer> getUnbalancedChild(Tree<Integer> tree) {
        var values = computeValues(tree);
        var candidate = values.getFirst();
        if (values.stream().filter(value -> Objects.equals(value.getLeft(), candidate.getLeft())).count() > 1) {
            return values.stream().filter(value -> !Objects.equals(value.getLeft(), candidate.getLeft())).findAny().orElseThrow().getRight();
        } else {
            return candidate.getRight();
        }
    }

    private Tree<Integer> getBalancedChild(Tree<Integer> tree) {
        var values = computeValues(tree);
        var candidate = values.getFirst();
        if (values.stream().filter(value -> Objects.equals(value.getLeft(), candidate.getLeft())).count() > 1) {
            return candidate.getRight();
        } else {
            return values.stream().filter(value -> !Objects.equals(value.getLeft(), candidate.getLeft())).findAny().orElseThrow().getRight();
        }
    }

    private List<Pair<Long, Tree<Integer>>> computeValues(Tree<Integer> tree) {
        return tree.getChildren().stream().map(child -> Pair.of(computeSum(child), child)).toList();
    }

    private Tree<Integer> findParent(Map<String, Tree<Integer>> stringTreeMap) {
        var parent = stringTreeMap.values().iterator().next();
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
    }

    private long computeSum(Tree<Integer> tree) {
        long sum = tree.getValue();
        for (var child : tree.getChildren()) {
            sum += computeSum(child);
        }
        return sum;
    }
}
