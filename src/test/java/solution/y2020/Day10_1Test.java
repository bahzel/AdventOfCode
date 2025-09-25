package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day10_1Test {
	@Test
	public void test() {
		assertThat(new Day10_1().disableLog().solve()).isEqualTo("2664");
	}
}
