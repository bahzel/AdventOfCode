package solution.y2019;

public class IntCodeInterpreter {
	private IntCodeInterpreter() {
	}

	public static int performComputation(int[] register) {
		for (int i = 0; i < register.length; i = i + 4) {
			switch (register[i]) {
			case 1:
				register[register[i + 3]] = register[register[i + 1]] + register[register[i + 2]];
				break;
			case 2:
				register[register[i + 3]] = register[register[i + 1]] * register[register[i + 2]];
				break;
			case 99:
				return register[0];
			default:
				throw new IllegalStateException("Unexpected value: " + register[i]);
			}
		}

		return register[0];
	}
}
