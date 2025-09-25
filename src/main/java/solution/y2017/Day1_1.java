package solution.y2017;

import utils.soution.Solution;

public class Day1_1 extends Solution {
    public static void main(String[] args) {
        new Day1_1().solve();
    }

    @Override
    protected String doSolve() {
        var sum = 0L;
        var captcha = input.getFirst();

        for (int i = 0; i < captcha.length() - 1; i++) {
            if (captcha.charAt(i) == captcha.charAt(i + 1)) {
                sum += Long.parseLong(captcha.charAt(i) + "");
            }
        }
        if (captcha.charAt(captcha.length() - 1) == captcha.charAt(0)) {
            sum += Long.parseLong(captcha.charAt(0) + "");
        }

        return sum + "";
    }
}
