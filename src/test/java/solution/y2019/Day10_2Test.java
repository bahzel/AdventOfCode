package solution.y2019;

import org.junit.jupiter.api.Test;
import utils.Point;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10_2Test {
	@Test
	public void test() {
		assertThat(new Day10_2().disableLog().solve()).isEqualTo("204");
	}

	@Test
	public void getDegreeTo21Dot8() {
		assertThat(new Point(11, 13).getDegreeTo(new Point(13, 8))).isEqualTo(21.801409486351815);
	}

	@Test
	public void getDegreeTo21Dot0() {
		assertThat(new Point(11, 13).getDegreeTo(new Point(16, 0))).isEqualTo(21.037511025421807);
	}
}
