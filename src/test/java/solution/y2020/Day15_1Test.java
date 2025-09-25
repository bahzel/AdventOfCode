package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day15_1Test {
	@Test
	public void test() {
		assertThat(new Day15_1().disableLog().solve()).isEqualTo("257");
	}
}
