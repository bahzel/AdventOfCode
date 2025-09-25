package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day17_1 extends GridSolution<Boolean> {
	public static void main(String[] args) {
		new Day17_1().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withOutput(output).performComputation();

		var instruction = new ArrayList<String>();
		var currentLine = new StringBuilder();
		for (var value : output) {
			if (value == 10) {
				instruction.add(currentLine.toString());
				currentLine = new StringBuilder();
			} else {
				currentLine.append((char) value.intValue());
			}
		}
		return instruction.subList(0, instruction.size() - 1);
	}

	@Override
	protected GridElement<Boolean> transformCell(char ch, int x, int y) {
		return switch (ch) {
			case '#', '^', 'v', '<', '>' -> new GridElement<>(true, x, y);
			case '.' -> new GridElement<>(false, x, y);
			default -> throw new IllegalArgumentException("Invalid character: " + ch);
		};
	}

	@Override
	protected String computeSolution() {
		return stream().filter(cell -> cell.getBorderingNeighbours().size() == 4)
					   .filter(cell -> cell.getValue() && cell.getUpperNeighbour().getValue()
							   && cell.getRightNeighbour().getValue() && cell.getLowerNeighbour().getValue()
							   && cell.getLeftNeighbour().getValue())
					   .mapToInt(cell -> cell.getCoordinates().getX() * cell.getCoordinates().getY())
					   .sum() + "";
	}
}
