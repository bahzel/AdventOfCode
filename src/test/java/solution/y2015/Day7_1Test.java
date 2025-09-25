package solution.y2015;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day7_1Test {
	@Test
	public void test() {
		assertThat(new Day7_1().disableLog().solve()).isEqualTo("16076");
	}
}
