package solution.y2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import utils.soution.Solution;

public class Day11_1 extends Solution {
    private Set<String> CACHE = new HashSet<>();

    public static void main(String[] args) {
        new Day11_1().solve();
    }

    @Override
    protected String doSolve() {
        var currentRound = List.of("00010102222");

        long round = 0;
        while (true) {
            System.out.println(round);
            round++;
            var nextRound = new ArrayList<String>();
            currentRound.forEach(current -> nextRound.addAll(performStep(current)));
            for (var next : nextRound) {
                if (isGoal(next)) {
                    return round + "";
                }
            }
            var maximumScore = nextRound.stream().map(this::computeScore).mapToLong(Long::longValue).max().orElseThrow();
            currentRound = nextRound.stream().filter(nextStep -> computeScore(nextStep) >= maximumScore * 95L / 100L).toList();
        }
    }

    private List<String> performStep(String facility) {
        var nextSteps = new ArrayList<String>();
        var indexElevator = getIndexOfElevator(facility);

        var chars = facility.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != indexElevator) {
                continue;
            }

            if (indexElevator > '0') {
                var nextStep = new StringBuilder();
                nextStep.append(lower(chars[0]));
                nextStep.append(facility.substring(1, i));
                nextStep.append(lower(chars[i]));
                nextStep.append(facility.substring(i + 1));
                var nextStepCandidate = nextStep.toString();
                if (isValid(nextStepCandidate) && CACHE.add(nextStepCandidate)) {
                    nextSteps.add(nextStepCandidate);
                }
            }
            if (indexElevator < '3') {
                var nextStep = new StringBuilder();
                nextStep.append(higher(chars[0]));
                nextStep.append(facility.substring(1, i));
                nextStep.append(higher(chars[i]));
                nextStep.append(facility.substring(i + 1));
                var nextStepCandidate = nextStep.toString();
                if (isValid(nextStepCandidate) && CACHE.add(nextStepCandidate)) {
                    nextSteps.add(nextStepCandidate);
                }
            }
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] != indexElevator) {
                    continue;
                }

                if (indexElevator > '0') {
                    var nextStep = new StringBuilder();
                    nextStep.append(lower(chars[0]));
                    nextStep.append(facility.substring(1, i));
                    nextStep.append(lower(chars[i]));
                    nextStep.append(facility.substring(i + 1, j));
                    nextStep.append(lower(chars[j]));
                    nextStep.append(facility.substring(j + 1));
                    var nextStepCandidate = nextStep.toString();
                    if (isValid(nextStepCandidate) && CACHE.add(nextStepCandidate)) {
                        nextSteps.add(nextStepCandidate);
                    }
                }
                if (indexElevator < '3') {
                    var nextStep = new StringBuilder();
                    nextStep.append(higher(chars[0]));
                    nextStep.append(facility.substring(1, i));
                    nextStep.append(higher(chars[i]));
                    nextStep.append(facility.substring(i + 1, j));
                    nextStep.append(higher(chars[j]));
                    nextStep.append(facility.substring(j + 1));
                    var nextStepCandidate = nextStep.toString();
                    if (isValid(nextStepCandidate) && CACHE.add(nextStepCandidate)) {
                        nextSteps.add(nextStepCandidate);
                    }
                }
            }
        }
        return nextSteps;
    }

    private char lower(char ch) {
        return (char) (ch - 1);
    }

    private char higher(char ch) {
        return (char) (ch + 1);
    }

    private char getIndexOfElevator(String facility) {
        return facility.charAt(0);
    }

    private boolean isValid(String facility) {
        for (int i = 1; i < facility.length(); i = i + 2) {
            if (facility.charAt(i) == facility.charAt(i + 1)) {
                continue;
            }
            for (int j = 2; j < facility.length(); j = j + 2) {
                if (facility.charAt(i) == facility.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGoal(String facility) {
        return "33333333333".equals(facility);
    }

    private long computeScore(String facility) {
        var score = 0L;
        score += StringUtils.countMatches(facility, '0');
        score += StringUtils.countMatches(facility, '1') * 10L;
        score += StringUtils.countMatches(facility, '2') * 20L;
        score += StringUtils.countMatches(facility, '3') * 30L;
        switch (facility.charAt(0)) {
            case '0':
                score += 29;
                break;
            case '1':
                score += 10;
                break;
            case '2':
                score -= 10;
                break;
            case '3':
                score -= 29;
        }
        return score;
    }
}
