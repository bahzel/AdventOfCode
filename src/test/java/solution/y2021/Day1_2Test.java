package solution.y2021;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day1_2Test {
	@Test
	public void test() {
		assertThat(new Day1_1().disableLog().solve()).isEqualTo("1761");
	}
}
