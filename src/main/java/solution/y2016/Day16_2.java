package solution.y2016;

import org.apache.commons.lang3.StringUtils;
import utils.soution.Solution;

public class Day16_2 extends Solution {
    public static void main(String[] args) {
        new Day16_2().solve();
    }

    @Override
    protected String doSolve() {
        return computeChecksum(input.getFirst(), 35651584);
    }

    private String computeChecksum(String input, int length) {
        return computeChecksum(expandLength(input, length));
    }

    private String expandLength(String input, int length) {
        while (input.length() < length) {
            input = doDragonCurve(input);
        }
        return input.substring(0, length);
    }

    private String doDragonCurve(String input) {
        return input + "0" + StringUtils.reverse(input).replace("0", "x").replace("1", "0").replace("x", "1");
    }

    private String computeChecksum(String input) {
        while (input.length() % 2 == 0) {
            input = computeNextChecksum(input);
        }
        return input;
    }

    private String computeNextChecksum(String input) {
        var solution = new StringBuilder();
        for (int i = 0; i < input.length(); i = i + 2) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                solution.append("1");
            } else {
                solution.append("0");
            }
        }
        return solution.toString();
    }
}
