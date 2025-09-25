package solution.y2017;

import utils.soution.Solution;

public class Day1_2 extends Solution {
    public static void main(String[] args) {
        new Day1_2().solve();
    }

    @Override
    protected String doSolve() {
        var sum = 0L;
        var captcha = input.getFirst();
        var halfSize = captcha.length() / 2;

        for (int i = 0; i < halfSize; i++) {
            if (captcha.charAt(i) == captcha.charAt(i + halfSize)) {
                sum += Long.parseLong(captcha.charAt(i) + "") * 2;
            }
        }

        return sum + "";
    }
}
