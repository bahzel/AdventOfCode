package solution.y2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8_2Test {
	@Test
	public void test() {
		assertThat(new Day8_2().solve()).isEqualTo(
				"XXX  X  X XXX  X  X  XX  XXXX  XX  XXXX  XXX X    \nX  X X  X X  X X  X X  X X    X  X X      X  X    \nX  X X  X X  X X  X X    XXX  X  X XXX    X  X    \nXXX  X  X XXX  X  X X    X    X  X X      X  X    \nX X  X  X X X  X  X X  X X    X  X X      X  X    \nX  X  XX  X  X  XX   XX  XXXX  XX  XXXX  XXX XXXX \n");
	}
}
