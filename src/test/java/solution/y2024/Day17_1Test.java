package solution.y2024;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day17_1Test {
	@Test
	public void test() {
		assertThat(new Day17_1().disableLog().solve()).isEqualTo("6,7,5,2,1,3,5,1,7");
	}
}
